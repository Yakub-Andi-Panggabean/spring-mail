package com.special.gitf.mail.domain;

public class Email {

  protected String content;
  protected String destination;
  protected String subject;
  protected String from;

  public Email() {
    super();
  }

  public Email(String content, String destination, String subject, String from) {
    super();
    this.content = content;
    this.destination = destination;
    this.subject = subject;
    this.from = from;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  @Override
  public String toString() {
    return "Email [content=" + content + ", destination=" + destination + ", subject=" + subject
        + ", from=" + from + "]";
  }


}
