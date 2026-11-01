package com.learnova.model;

public class QuestionSummary {
    private final int id;
    private final String title;
    private final String category;
    private final String difficulty;
    private final int xpReward;
    private final String creator;

    public QuestionSummary(int id, String title, String category, String difficulty, int xpReward, String creator) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.xpReward = xpReward;
        this.creator = creator;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public int getXpReward() { return xpReward; }
    public String getCreator() { return creator; }
}
