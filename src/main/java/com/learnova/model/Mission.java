package com.learnova.model;

public class Mission {
    private final int id;
    private final String title;
    private final String description;
    private final String category;
    private final String difficulty;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final int xpReward;
    private final boolean attempted;
    private final boolean previouslyCorrect;

    public Mission(int id, String title, String description, String category, String difficulty,
                   String optionA, String optionB, String optionC, String optionD, int xpReward,
                   boolean attempted, boolean previouslyCorrect) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.xpReward = xpReward;
        this.attempted = attempted;
        this.previouslyCorrect = previouslyCorrect;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public int getXpReward() { return xpReward; }
    public boolean isAttempted() { return attempted; }
    public boolean isPreviouslyCorrect() { return previouslyCorrect; }
}
