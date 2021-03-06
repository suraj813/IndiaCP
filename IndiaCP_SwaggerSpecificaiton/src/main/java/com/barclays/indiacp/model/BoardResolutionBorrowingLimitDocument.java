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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * BoardResolutionBorrowingLimitDocument
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-01-14T02:47:08.972Z")
public class BoardResolutionBorrowingLimitDocument   {
  @JsonProperty("legalEntityId")
  private String legalEntityId = null;

  @JsonProperty("boardResolutionBorrowingLimit")
  private Long boardResolutionBorrowingLimit = null;

  @JsonProperty("currentOutstandingCreditBorrowing")
  private Long currentOutstandingCreditBorrowing = null;

  @JsonProperty("currency")
  private String currency = null;

  @JsonProperty("boardResolutionIssuanceDate")
  private Date boardResolutionIssuanceDate = null;

  @JsonProperty("boardResolutionExpiryDate")
  private Date boardResolutionExpiryDate = null;

  @JsonProperty("docHash")
  private String docHash = null;

  @JsonProperty("version")
  private Integer version = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("lastModifiedDate")
  private Date lastModifiedDate = null;

  /**
   * There can be only one active CR Document at any given time
   */
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    OBSOLETE("OBSOLETE");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("status")
  private StatusEnum status = null;

  public BoardResolutionBorrowingLimitDocument legalEntityId(String legalEntityId) {
    this.legalEntityId = legalEntityId;
    return this;
  }

   /**
   * Unique identifier of the Legal Entity that this document is issued by
   * @return legalEntityId
  **/
  @JsonProperty("legalEntityId")
  @ApiModelProperty(value = "Unique identifier of the Legal Entity that this document is issued by")
  public String getLegalEntityId() {
    return legalEntityId;
  }

  public void setLegalEntityId(String legalEntityId) {
    this.legalEntityId = legalEntityId;
  }

  public BoardResolutionBorrowingLimitDocument boardResolutionBorrowingLimit(Long boardResolutionBorrowingLimit) {
    this.boardResolutionBorrowingLimit = boardResolutionBorrowingLimit;
    return this;
  }

   /**
   * Borrowing Limit approved by board
   * @return boardResolutionBorrowingLimit
  **/
  @JsonProperty("boardResolutionBorrowingLimit")
  @ApiModelProperty(value = "Borrowing Limit approved by board")
  public Long getBoardResolutionBorrowingLimit() {
    return boardResolutionBorrowingLimit;
  }

  public void setBoardResolutionBorrowingLimit(Long boardResolutionBorrowingLimit) {
    this.boardResolutionBorrowingLimit = boardResolutionBorrowingLimit;
  }

  public BoardResolutionBorrowingLimitDocument currentOutstandingCreditBorrowing(Long currentOutstandingCreditBorrowing) {
    this.currentOutstandingCreditBorrowing = currentOutstandingCreditBorrowing;
    return this;
  }

   /**
   * Outstanding Credit Borrowing. This is an auto computed value. It is computed by the Smart Contract based on the outstanding open CP Program Sizes.
   * @return currentOutstandingCreditBorrowing
  **/
  @JsonProperty("currentOutstandingCreditBorrowing")
  @ApiModelProperty(value = "Outstanding Credit Borrowing. This is an auto computed value. It is computed by the Smart Contract based on the outstanding open CP Program Sizes.")
  public Long getCurrentOutstandingCreditBorrowing() {
    return currentOutstandingCreditBorrowing;
  }

  public void setCurrentOutstandingCreditBorrowing(Long currentOutstandingCreditBorrowing) {
    this.currentOutstandingCreditBorrowing = currentOutstandingCreditBorrowing;
  }

  public BoardResolutionBorrowingLimitDocument currency(String currency) {
    this.currency = currency;
    return this;
  }

   /**
   * Currency. Defaulted to INR for India Commercial Paper
   * @return currency
  **/
  @JsonProperty("currency")
  @ApiModelProperty(value = "Currency. Defaulted to INR for India Commercial Paper")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BoardResolutionBorrowingLimitDocument boardResolutionIssuanceDate(Date boardResolutionIssuanceDate) {
    this.boardResolutionIssuanceDate = boardResolutionIssuanceDate;
    return this;
  }

   /**
   * Date on which the BR was issued
   * @return boardResolutionIssuanceDate
  **/
  @JsonProperty("boardResolutionIssuanceDate")
  @ApiModelProperty(value = "Date on which the BR was issued")
  public Date getBoardResolutionIssuanceDate() {
    return boardResolutionIssuanceDate;
  }

  public void setBoardResolutionIssuanceDate(Date boardResolutionIssuanceDate) {
    this.boardResolutionIssuanceDate = boardResolutionIssuanceDate;
  }

  public BoardResolutionBorrowingLimitDocument boardResolutionExpiryDate(Date boardResolutionExpiryDate) {
    this.boardResolutionExpiryDate = boardResolutionExpiryDate;
    return this;
  }

   /**
   * Date on which the BR will become obsolete
   * @return boardResolutionExpiryDate
  **/
  @JsonProperty("boardResolutionExpiryDate")
  @ApiModelProperty(value = "Date on which the BR will become obsolete")
  public Date getBoardResolutionExpiryDate() {
    return boardResolutionExpiryDate;
  }

  public void setBoardResolutionExpiryDate(Date boardResolutionExpiryDate) {
    this.boardResolutionExpiryDate = boardResolutionExpiryDate;
  }

  public BoardResolutionBorrowingLimitDocument docHash(String docHash) {
    this.docHash = docHash;
    return this;
  }

   /**
   * SHA256 Hash of the Credit Rating Document
   * @return docHash
  **/
  @JsonProperty("docHash")
  @ApiModelProperty(value = "SHA256 Hash of the Credit Rating Document")
  public String getDocHash() {
    return docHash;
  }

  public void setDocHash(String docHash) {
    this.docHash = docHash;
  }

  public BoardResolutionBorrowingLimitDocument version(Integer version) {
    this.version = version;
    return this;
  }

   /**
   * Current version of the CR Document. This is auto incremented by the DL.
   * @return version
  **/
  @JsonProperty("version")
  @ApiModelProperty(value = "Current version of the CR Document. This is auto incremented by the DL.")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public BoardResolutionBorrowingLimitDocument modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

   /**
   * Unique identifier of the Logged-in User that performed the action. This is required for Audit History
   * @return modifiedBy
  **/
  @JsonProperty("modifiedBy")
  @ApiModelProperty(value = "Unique identifier of the Logged-in User that performed the action. This is required for Audit History")
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public BoardResolutionBorrowingLimitDocument lastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
    return this;
  }

   /**
   * Last Modified Date for this BR upload. This is required for Audit History
   * @return lastModifiedDate
  **/
  @JsonProperty("lastModifiedDate")
  @ApiModelProperty(value = "Last Modified Date for this BR upload. This is required for Audit History")
  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public BoardResolutionBorrowingLimitDocument status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * There can be only one active CR Document at any given time
   * @return status
  **/
  @JsonProperty("status")
  @ApiModelProperty(value = "There can be only one active CR Document at any given time")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoardResolutionBorrowingLimitDocument boardResolutionBorrowingLimitDocument = (BoardResolutionBorrowingLimitDocument) o;
    return Objects.equals(this.legalEntityId, boardResolutionBorrowingLimitDocument.legalEntityId) &&
        Objects.equals(this.boardResolutionBorrowingLimit, boardResolutionBorrowingLimitDocument.boardResolutionBorrowingLimit) &&
        Objects.equals(this.currentOutstandingCreditBorrowing, boardResolutionBorrowingLimitDocument.currentOutstandingCreditBorrowing) &&
        Objects.equals(this.currency, boardResolutionBorrowingLimitDocument.currency) &&
        Objects.equals(this.boardResolutionIssuanceDate, boardResolutionBorrowingLimitDocument.boardResolutionIssuanceDate) &&
        Objects.equals(this.boardResolutionExpiryDate, boardResolutionBorrowingLimitDocument.boardResolutionExpiryDate) &&
        Objects.equals(this.docHash, boardResolutionBorrowingLimitDocument.docHash) &&
        Objects.equals(this.version, boardResolutionBorrowingLimitDocument.version) &&
        Objects.equals(this.modifiedBy, boardResolutionBorrowingLimitDocument.modifiedBy) &&
        Objects.equals(this.lastModifiedDate, boardResolutionBorrowingLimitDocument.lastModifiedDate) &&
        Objects.equals(this.status, boardResolutionBorrowingLimitDocument.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(legalEntityId, boardResolutionBorrowingLimit, currentOutstandingCreditBorrowing, currency, boardResolutionIssuanceDate, boardResolutionExpiryDate, docHash, version, modifiedBy, lastModifiedDate, status);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoardResolutionBorrowingLimitDocument {\n");
    
    sb.append("    legalEntityId: ").append(toIndentedString(legalEntityId)).append("\n");
    sb.append("    boardResolutionBorrowingLimit: ").append(toIndentedString(boardResolutionBorrowingLimit)).append("\n");
    sb.append("    currentOutstandingCreditBorrowing: ").append(toIndentedString(currentOutstandingCreditBorrowing)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    boardResolutionIssuanceDate: ").append(toIndentedString(boardResolutionIssuanceDate)).append("\n");
    sb.append("    boardResolutionExpiryDate: ").append(toIndentedString(boardResolutionExpiryDate)).append("\n");
    sb.append("    docHash: ").append(toIndentedString(docHash)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    lastModifiedDate: ").append(toIndentedString(lastModifiedDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

