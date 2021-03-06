/*
 * IndiaCP API
 * This API will drive the UI
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.barclays.indiacp.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets CreditRatingError
 */
public enum CreditRatingError {
  
  CREATION_ERROR("CR_CREATION_ERROR"),
  
  FETCH_ERROR("CR_FETCH_ERROR"),
  
  AMENDMENT_ERROR("CR_AMENDMENT_ERROR"),
  
  CANCELLATION_ERROR("CR_CANCELLATION_ERROR"),
  
  DOES_NOT_EXIST_ERROR("CR_DOES_NOT_EXIST_ERROR"),
  
  DOC_GENERATION_ERROR("CR_DOC_GENERATION_ERROR"),
  
  DOC_UPLOAD_ERROR("CR_DOC_UPLOAD_ERROR"),
  
  HISTORY_SEARCH_ERROR("CR_HISTORY_SEARCH_ERROR");

  private String value;

  CreditRatingError(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static CreditRatingError fromValue(String text) {
    for (CreditRatingError b : CreditRatingError.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

