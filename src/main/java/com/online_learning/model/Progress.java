package com.online_learning.model;

public class Progress {

    private int progressId;
    private int progressMarks;
    private String completeStatus;
    private int contentId;
    private String completedAt;
    private int userId;

    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public int getProgressMarks() {
        return progressMarks;
    }

    public void setProgressMarks(int progressMarks) {
        this.progressMarks = progressMarks;
    }

    public String getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}