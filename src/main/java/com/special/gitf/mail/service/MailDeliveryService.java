package com.special.gitf.mail.service;

import com.special.gitf.mail.domain.MailTransaction;

public interface MailDeliveryService {

  void sendPasswordReminderMail(MailTransaction transaction);

  void sendRegistrationConfirmationMail(MailTransaction transaction);

  void sendBookingInvoiceMail(MailTransaction mailTransaction);

}
