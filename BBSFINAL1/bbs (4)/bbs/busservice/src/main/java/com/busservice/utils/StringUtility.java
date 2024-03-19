package com.busservice.utils;


import lombok.experimental.UtilityClass;


/**
 * @author Divy Jain
 * Utility class for string related fuctionalities
 */
@UtilityClass
public class StringUtility {

  /**
   * To check string is null empty or blank
   * @param str
   * @return boolean
   */
  public boolean isNullEmptyOrBlank(String str) {
    if(str==null) return true;
    if(str.isEmpty()) return true;
    if(str.isBlank()) return true;
    return false;
  }

  /**
   * To get lower case character count
   * @param str
   * @return int
   */
  public int getLowercaseCharacterCount(String str) {
    int count=0;
    for(int i=0;i<str.length();i++) {
      char ch=str.charAt(i);
      if(ch>='a' && ch<='z') count++;
    }
    return count;
  }

  /**
   * To get uppercase character count
   * @param str
   * @return int
   */
  public int getUppercaseCharacterCount(String str) {
    int count=0;
    for(int i=0;i<str.length();i++) {
      char ch=str.charAt(i);
      if(ch>='A' && ch<='Z') count++;
    }
    return count;
  }

  /**
   * To get digit count
   * @param str
   * @return int
   */
  public int getDigitCount(String str) {
    int count=0;
    for(int i=0;i<str.length();i++) {
      char ch=str.charAt(i);
      if(ch>='0' && ch<='9') count++;
    }
    return count;
  }

  /**
   * To get special character count
   * @param str
   * @return int
   */
  public int getSpecialCharacterCount(String str) {
    int count=0;
    for(int i=0;i<str.length();i++) {
      char ch=str.charAt(i);
      if(ch>='a' && ch<='z') continue;
      if(ch>='A' && ch<='Z') continue;
      if(ch>='0' && ch<='9') continue;
      count++;
    }
    return count;
  }

}
