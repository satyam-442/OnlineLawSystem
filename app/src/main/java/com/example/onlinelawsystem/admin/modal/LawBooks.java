package com.example.onlinelawsystem.admin.modal;

public class LawBooks {

    String BooksPdf, BookTitle, BookNameDefault, BookDescription, BookTopics, Date, Time, BookId;

    public LawBooks() {
    }

    public LawBooks(String booksPdf, String bookTitle, String bookNameDefault, String bookDescription, String bookTopics, String date, String time, String bookId) {
        BooksPdf = booksPdf;
        BookTitle = bookTitle;
        BookNameDefault = bookNameDefault;
        BookDescription = bookDescription;
        BookTopics = bookTopics;
        Date = date;
        Time = time;
        BookId = bookId;
    }

    public String getBooksPdf() {
        return BooksPdf;
    }

    public void setBooksPdf(String booksPdf) {
        BooksPdf = booksPdf;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getBookNameDefault() {
        return BookNameDefault;
    }

    public void setBookNameDefault(String bookNameDefault) {
        BookNameDefault = bookNameDefault;
    }

    public String getBookDescription() {
        return BookDescription;
    }

    public void setBookDescription(String bookDescription) {
        BookDescription = bookDescription;
    }

    public String getBookTopics() {
        return BookTopics;
    }

    public void setBookTopics(String bookTopics) {
        BookTopics = bookTopics;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }
}