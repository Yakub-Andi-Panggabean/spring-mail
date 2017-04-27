package com.special.gitf.mail.service;

import java.util.List;

import com.special.gitf.mail.domain.MailTransaction;

public interface MailTransactionService {

  List<MailTransaction> findUnprocessedMailTransactionRequest() throws Exception;

  void findRegisrationConfirmationMailRequest() throws Exception;

  void findForgotPasswordConfirmationMailRequest() throws Exception;

  void findBookingInvoiceMailRequest() throws Exception;


}
