package com.example.quote;

public class Quote_Db {
    private String number;
    private String id;
    private String subject;
    private String quote;
    private String writer;
    private String image;
    private String c_number;
    private int font;
    private int time;


    public String getNumber() {
        return number;
    }

    public Quote_Db setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getId() {
        return id;
    }

    public Quote_Db setId(String id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Quote_Db setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getQuote() {
        return quote;
    }

    public Quote_Db setQuote(String quote) {
        this.quote = quote;
        return this;
    }

    public String getWriter() {
        return writer;
    }

    public Quote_Db setWriter(String writer) {
        this.writer = writer;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Quote_Db setImage(String image) {
        this.image = image;
        return this;
    }

    public int getFont() {
        return font;
    }

    public Quote_Db setFont(int font) {
        this.font = font;
        return this;

    }

    public int getTime() {
        return time;
    }

    public Quote_Db setTime(int time) {
        this.time = time;
        return this;

    }

    public String getC_number() {
        return c_number;
    }

    public Quote_Db setC_number(String c_number) {
        this.c_number = c_number;
        return this;
    }
}
