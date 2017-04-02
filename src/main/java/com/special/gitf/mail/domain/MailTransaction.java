package com.special.gitf.mail.domain;

import java.util.Date;

public class MailTransaction {

  private String id;
  private String userId;
  private String actionId;
  private boolean status;
  private Date transactionDateTime;

  public MailTransaction() {
    super();
  }

  public MailTransaction(String id, String userId, String actionId, boolean status,
      Date transactionDateTime) {
    super();
    this.id = id;
    this.userId = userId;
    this.actionId = actionId;
    this.status = status;
    this.transactionDateTime = transactionDateTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getActionId() {
    return actionId;
  }

  public void setActionId(String actionId) {
    this.actionId = actionId;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public Date getTransactionDateTime() {
    return transactionDateTime;
  }

  public void setTransactionDateTime(Date transactionDateTime) {
    this.transactionDateTime = transactionDateTime;
  }

  @Override
  public String toString() {
    return "MailTransaction [id=" + id + ", userId=" + userId + ", actionId=" + actionId
        + ", status=" + status + ", transactionDateTime=" + transactionDateTime + "]";
  }



}
