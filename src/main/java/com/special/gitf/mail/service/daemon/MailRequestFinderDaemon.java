package com.special.gitf.mail.service.daemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.special.gitf.mail.service.MailTransactionService;
import com.special.gitf.mail.util.CommonUtil;


public class MailRequestFinderDaemon implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(MailRequestFinderDaemon.class);

  private final MailTransactionService service;
  private final String action;

  public MailRequestFinderDaemon(MailTransactionService service, String action) {
    super();
    this.service = service;
    this.action = action;
  }


  @Override
  public void run() {

    while (true) {

      try {
        switch (action) {
          case CommonUtil.ACTIVATE_USER_ACTION:
            service.findRegisrationConfirmationMailRequest();
            break;
          case CommonUtil.SEND_INVOICE_ACTION:
            service.findBookingInvoiceMailRequest();
            break;
          case CommonUtil.FORGOT_PASSWORD_ACTION:
            service.findForgotPasswordConfirmationMailRequest();
            break;
          default:
            log.warn("unrecognized mail action");
        }

        Thread.sleep(5000);

      } catch (final Exception e) {
        e.printStackTrace();
        log.error("an error occured with message : {}", e.getMessage());
      }

    }



  }

}
