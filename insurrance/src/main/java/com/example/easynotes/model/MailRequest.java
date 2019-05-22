package com.example.easynotes.model;

/**
 * Created by ThuongPham on 22/05/2019.
 */
public class MailRequest {
    private String from;
    private String to;
    private String subject;
    private String cc;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}
