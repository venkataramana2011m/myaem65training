package com.myaem65training.core.models;

import java.util.ArrayList;
import java.util.List;

public class BookDetailResult {
    private String title;
    private String description;
    private String contributor;
    private String author;
    private String contributorNote;
    private String price;
    private String ageGroup;
    private String publisher;
    private List<Isbn> isbns = new ArrayList<Isbn>();
    private List<RanksHistory> ranksHistory = new ArrayList<RanksHistory>();
    private List<Review> reviews = new ArrayList<Review>();
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getContributor() {
        return contributor;
    }
    public void setContributor(String contributor) {
        this.contributor = contributor;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContributorNote() {
        return contributorNote;
    }
    public void setContributorNote(String contributorNote) {
        this.contributorNote = contributorNote;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getAgeGroup() {
        return ageGroup;
    }
    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public List<Isbn> getIsbns() {
        return isbns;
    }
    public void setIsbns(List<Isbn> isbns) {
        this.isbns = isbns;
    }
    public List<RanksHistory> getRanksHistory() {
        return ranksHistory;
    }
    public void setRanksHistory(List<RanksHistory> ranksHistory) {
        this.ranksHistory = ranksHistory;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
