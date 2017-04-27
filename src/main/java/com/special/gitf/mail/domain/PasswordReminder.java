package com.special.gitf.mail.domain;

import java.util.Date;

public class PasswordReminder {

  private int id;
  private String userId;
  private boolean status;
  private Date createdDate;



  public PasswordReminder() {
    super();
    // TODO Auto-generated constructor stub
  }

  public PasswordReminder(int id, String userId, boolean status, Date createdDate) {
    super();
    this.id = id;
    this.userId = userId;
    this.status = status;
    this.createdDate = createdDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return "PasswordReminder [id=" + id + ", userId=" + userId + ", status=" + status
        + ", createdDate=" + createdDate + "]";
  }



}
