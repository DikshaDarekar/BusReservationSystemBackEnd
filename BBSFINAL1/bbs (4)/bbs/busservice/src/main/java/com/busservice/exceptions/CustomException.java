package com.busservice.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Divy Jain
 * Custom exception class contains two properties exception of type String if want to add single
 * message and exceptions of type Map if want to add multiple messages in key-value form
 */
public class CustomException extends Exception {
  private Map<String,String> exceptions;
  private String exception;
  public CustomException() {
    this.exceptions=new HashMap<>();
  }

  public CustomException(String exception) {
    this.exception=exception;
  }

  public void addException(String property,String exception) { this.exceptions.put(property.trim(),exception.trim()); }

  public String getException(String property)
  {
    String exception=this.exceptions.get(property.trim());
    if(exception==null) exception="";
    return exception;
  }

  public Map<String,String> getExceptions()
  {
    Map<String,String> exceptions=new HashMap<>(this.exceptions);
    return exceptions;
  }

  public void addException(String exception) {
    this.exception=exception;
  }

  public String getException() {
    return this.exception;
  }
}

