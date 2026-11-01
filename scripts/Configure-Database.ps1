$ErrorActionPreference = 'Stop'
$directory = Join-Path $env:USERPROFILE '.learnova'
$destination = Join-Path $directory 'database.properties'
if (Test-Path $destination) {
    throw "Configuration already exists at $destination. Edit it locally; it will not be overwritten."
}
$url = Read-Host 'JDBC URL (Enter for local MySQL on port 3306)'
if ([string]::IsNullOrWhiteSpace($url)) {
    $url = 'jdbc:mysql://localhost:3306/learnova_db?sslMode=DISABLED&allowPublicKeyRetrieval=true&serverTimezone=UTC'
}
$userName = Read-Host 'MySQL username'
if ([string]::IsNullOrWhiteSpace($userName)) { throw 'MySQL username is required.' }
$secure = Read-Host 'MySQL password' -AsSecureString
$pointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure)
try {
    $secret = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($pointer)
    function Convert-PropertyValue([string]$value) {
        return $value.Replace('\', '\\').Replace("`r", '\r').Replace("`n", '\n').Replace("`t", '\t').Replace(' ', '\ ')
    }
    New-Item -ItemType Directory -Force -Path $directory | Out-Null
    $lines = @("db.url=$(Convert-PropertyValue $url)", "db.username=$(Convert-PropertyValue $userName)", "db.password=$(Convert-PropertyValue $secret)")
    [IO.File]::WriteAllLines($destination, $lines, (New-Object Text.UTF8Encoding($false)))
    $identity = [Security.Principal.WindowsIdentity]::GetCurrent().Name
    & icacls.exe $destination /inheritance:r /grant:r "${identity}:(F)" | Out-Null
    if ($LASTEXITCODE -ne 0) { throw 'Could not restrict configuration file permissions. Check its Windows Security settings.' }
    Write-Host "Saved external configuration to $destination. Restart Tomcat after configuration changes."
} finally {
    [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($pointer)
    $secret = $null
    $secure.Dispose()
}
