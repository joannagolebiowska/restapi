package com.merapar.xmlanalyzer.xmlanalyzer.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Details {

    private Date firstPost;
    private Date lastPost;
    private int totalPosts;
    private int totalAcceptedPosts;
    private int avgScore;

    public Date getFirstPost() {
        return firstPost;
    }

    public void setFirstPost(Date firstPost) {
        this.firstPost = firstPost;
    }

    public Date getLastPost() {
        return lastPost;
    }

    public void setLastPost(Date lastPost) {
        this.lastPost = lastPost;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }

    public int getTotalAcceptedPosts() {
        return totalAcceptedPosts;
    }

    public void setTotalAcceptedPosts(int totalAcceptedPosts) {
        this.totalAcceptedPosts = totalAcceptedPosts;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }
}
