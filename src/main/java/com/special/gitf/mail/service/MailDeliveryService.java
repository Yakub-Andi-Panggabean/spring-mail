package com.special.gitf.mail.service;

import com.special.gitf.mail.domain.MailTransaction;

public interface MailDeliveryService {

  void sendPasswordReminderMail(MailTransaction transaction) throws Exception;

  void sendRegistrationConfirmationMail(MailTransaction transaction) throws Exception;

  void sendBookingInvoiceMail(MailTransaction mailTransaction) throws Exception;

}
