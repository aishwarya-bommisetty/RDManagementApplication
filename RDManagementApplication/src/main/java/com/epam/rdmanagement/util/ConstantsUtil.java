package com.epam.rdmanagement.util;


public class ConstantsUtil {

  /**
   * Pattern Constants.
   */

  /**
   * The Constant EMAIL_PATTERN Regex String.
   */
  public static final String EMAIL_PATTERN = "[a-zA-Z]{2,15}[_][a-zA-Z]{2,15}@epam[.]com";

  /**
   * The Constant NAME_PATTERN Regex String.
   */
  public static final String NAME_PATTERN = "[A-Za-z]{2,15}";

  /**
   * The Constant PASSWORD_PATTERN Regex String.
   */
  public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
      + "(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}";

  /**
   * Security Constants.
   */

  /**
   * The Constant SECRET_KEY String.
   */
  public static final String SECRET_KEY = "SecretKeyToGenJWTs";

  /**
   * The Constant EXPIRATION_TIME in which a token gets expired i.e 10 days long.
   */
  public static final long EXPIRATION_TIME = 864_000_000;

  /**
   * The Constant TOKEN_PREFIX in the header to contain token String.
   */
  public static final String TOKEN_PREFIX = "Bearer ";

  /**
   * The Constant HEADER_STRING defining on which header the info will be present.
   */
  public static final String HEADER_STRING = "Authorization";

  /**
   * The Constant AUTHORITIES_KEY String.
   */
  public static final String AUTHORITIES_KEY = "auth";

  /**
   * The Constant REGISTER_ENDPOINT String.
   */
  public static final String REGISTER_ENDPOINT = "/register";
  
  /**
   * ENTITY BASED CONSTANTS.
   */

  /**
   * The Constant ROLE_TABLE_NAME String.
   */
  public static final String ROLE_TABLE_NAME = "ROLE";

  /**
   * The Constant ROLE_ID_COLUMN_NAME String.
   */
  public static final String ROLE_ID_COLUMN_NAME = "ROLE_ID";

  /**
   * The Constant ROLE_NAME_COLUMN_NAME String.
   */
  public static final String ROLE_NAME_COLUMN_NAME = "ROLE_NAME";

  /**
   * The Constant USER_TABLE_NAME String.
   */
  public static final String USER_TABLE_NAME = "USER";

  /**
   * The Constant USER_ID_COLUMN_NAME String.
   */
  public static final String USER_ID_COLUMN_NAME = "USER_ID";

  /**
   * The Constant EMAIL_COLUMN_NAME String.
   */
  public static final String EMAIL_COLUMN_NAME = "EMAIL";
  
  public static final String USER_FIRST_NAME_COLUMN_NAME = "FIRST_NAME";
  
  public static final String USER_LAST_NAME_COLUMN_NAME = "LAST_NAME";
  
  public static final String USER_PASSWORD_COLUMN_NAME = "PASSWORD";
  
  /**
   * DATASET PATH FOR DBUNIT.
   */
  public static final String DBUNIT_DATASET_PATH = "/dataset.xml";
  
  public static final String FEEDBACKNOTFOUNDEXCEPTIONMESSAGE = "Unable to retrieve feedback";
  public static final String FEEDBACKCONTENT = "Feedback for mentee";
  public static final String MENTEENOTFOUNDEXCEPTIONMESSAGE = "mentee with the given email not found";
  public static final String MENTORNOTAVAILABLEEXCEPTIONMESSAGE = "Mentor Information for this Student is not available";


  /**
   * Don't let anyone instantiate this class.
   */
  private ConstantsUtil() {
  }
}
