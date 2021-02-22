package com.example.quote;

public class Quote {
    private String subject;
    private String quote;
    private String writer;
    private String number;
    private int image;
    private String image2;
    private int time;
    private int time2;
    private int font;
    private String id;

    public Quote(String quote, String writer){
        this.quote = quote;
        this.writer = writer;
    }

    public Quote(String quote, String writer, String number, String subject, int image){
        this.quote = quote;
        this.writer = writer;
        this.number = number;
        this.subject = subject;
        this.image = image;
    }
    public Quote(String quote, String writer, String number, String subject, int image, int font){
        this.quote = quote;
        this.writer = writer;
        this.number = number;
        this.subject = subject;
        this.image = image;
        this.font = font;
    }
    public Quote(String quote, String writer, String number, String subject, int image, int font, String id){
        this.quote = quote;
        this.writer = writer;
        this.number = number;
        this.subject = subject;
        this.image = image;
        this.font = font;
        this.id = id;
    }


    public Quote(){}



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuote() {
        return quote;
    }

    public Quote setQuote(String quote) {
        this.quote = quote;
        return this;
    }

    public String getWriter() {
        return writer;
    }

    public Quote setWriter(String writer) {
        this.writer = writer;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Quote setNumber(String number) {
        this.number = number;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Quote setTime(int time) {
        this.time = time;
        return this;
    }

    public int getImage() {
        return image;
    }

    public Quote setImage(int image) {
        this.image = image;
        return this;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getId() {
        return id;
    }

    public Quote setId(String id) {
        this.id = id;
        return this;
    }

    public int getTime2() {
        return time2;
    }

    public Quote setTime2(int time2) {
        this.time2 = time2;
        return this;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
