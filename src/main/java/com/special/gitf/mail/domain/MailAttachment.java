package com.special.gitf.mail.domain;

import java.io.File;

public class MailAttachment extends Email {

  private String attachmentName;
  private File attachment;

  public MailAttachment() {
    super();
  }

  public MailAttachment(String content, String destination, String subject, String from,
      File attachment, String attachmentName) {
    this.attachment = attachment;
    this.attachmentName = attachmentName;
    this.content = content;
    this.destination = destination;
    this.subject = subject;
    this.from = from;

  }

  public File getAttachment() {
    return attachment;
  }

  public void setAttachment(File attachment) {
    this.attachment = attachment;
  }



  public String getAttachmentName() {
    return attachmentName;
  }

  public void setAttachmentName(String attachmentName) {
    this.attachmentName = attachmentName;
  }

  @Override
  public String toString() {
    return "MailAttachment [attachment=" + attachment + ", content=" + content + ", destination="
        + destination + ", subject=" + subject + ", from=" + from + "]";
  }



}
