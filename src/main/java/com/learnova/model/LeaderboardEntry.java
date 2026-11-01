package com.learnova.model;

public class LeaderboardEntry {
    private final String username;
    private final int totalXp;
    private final int playerLevel;
    private final int completed;

    public LeaderboardEntry(String username, int totalXp, int playerLevel, int completed) {
        this.username = username;
        this.totalXp = totalXp;
        this.playerLevel = playerLevel;
        this.completed = completed;
    }

    public String getUsername() { return username; }
    public int getTotalXp() { return totalXp; }
    public int getPlayerLevel() { return playerLevel; }
    public int getCompleted() { return completed; }
}
