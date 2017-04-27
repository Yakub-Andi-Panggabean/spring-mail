package com.special.gitf.mail.util;

public class CommonUtil {

  public static final String FORGOT_PASSWORD_ACTION = "0";
  public static final String SEND_INVOICE_ACTION = "1";
  public static final String ACTIVATE_USER_ACTION = "2";

  public static final String WAITING_CONFIRMATION = "2";



  public static final String EMAIL_SEQUENCE_ID = "MAIL_TRANSACTION_ID";

  public static final String INSERT_MAIL_TRANSACTION_KEY = "insertEmailTransaction";
  public static final String INSERT_MAIL_CONFIG_KEY = "insertEmailAction";
  public static final String FIND_INACTIVE_USER_KEY = "findInactiveUser";
  public static final String FIND_UNCONFIRMED_TRANSACTION_KEY = "findUnconfirmedTransaction";
  public static final String FIND_PASSWORD_REMINDER_REQUEST_KEY = "findPasswordReminderRequest";
  public static final String FIND_SEQUENCE_KEY = "findSequence";
  public static final String FIND_UNPROCESSED_MAIL_TRANSACTION_KEY = "findMailTransaction";
  public static final String FIND_MAIL_TRANSACTION_BY_USER_ID_AND_CONFIG_ID_KEY =
      "findMailTransactionByUserIdAndConfigId";
  public static final String FIND_MAIL_ACTION_BY_ACTION_CODE_KEY = "findEmailActionByActionCode";
  public static final String UPDATE_USER_STATUS_KEY = "updateUserWebStatus";


  public static final String REGISTRATION_CONFIRMATION_BEAN = "registrationConfirmationFinder";

}
