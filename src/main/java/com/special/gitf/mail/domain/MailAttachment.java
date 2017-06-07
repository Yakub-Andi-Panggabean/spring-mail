package com.special.gitf.mail.domain;

import java.io.File;
import java.util.List;

public class MailAttachment extends Email {

  private List<String> attachmentNames;
  private List<File> attachments;

  public MailAttachment() {
    super();
  }

  public MailAttachment(String content, String destination, String subject, String from,
      List<File> attachments, List<String> attachmentNames) {
    this.attachments = attachments;
    this.attachmentNames = attachmentNames;
    this.content = content;
    this.destination = destination;
    this.subject = subject;
    this.from = from;

  }

  public List<String> getAttachmentNames() {
    return attachmentNames;
  }

  public void setAttachmentNames(List<String> attachmentNames) {
    this.attachmentNames = attachmentNames;
  }

  public List<File> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<File> attachments) {
    this.attachments = attachments;
  }

  @Override
  public String toString() {
    return "MailAttachment [attachmentNames=" + attachmentNames + ", attachment=" + attachments
        + "]";
  }



}
