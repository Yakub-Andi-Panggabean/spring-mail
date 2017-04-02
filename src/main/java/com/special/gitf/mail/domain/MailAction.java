package com.special.gitf.mail.domain;

import java.util.Date;

public class MailAction {

  private String id;
  private String actionName;
  private String template;
  private Date createdDate;
  private String createdBy;
  private Date updatedDate;
  private String updatedBy;

  public MailAction() {
    super();
  }

  public MailAction(String id, String actionName, String template, Date createdDate,
      String createdBy, Date updatedDate, String updatedBy) {
    super();
    this.id = id;
    this.actionName = actionName;
    this.template = template;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
    this.updatedDate = updatedDate;
    this.updatedBy = updatedBy;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getActionName() {
    return actionName;
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  @Override
  public String toString() {
    return "MailAction [id=" + id + ", actionName=" + actionName + ", template=" + template
        + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updatedDate="
        + updatedDate + ", updatedBy=" + updatedBy + "]";
  }



}
