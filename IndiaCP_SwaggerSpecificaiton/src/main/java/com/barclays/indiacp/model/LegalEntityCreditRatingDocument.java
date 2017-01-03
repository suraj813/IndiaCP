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
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.barclays.indiacp.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * LegalEntityCreditRatingDocument
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-01-03T07:18:23.010Z")
public class LegalEntityCreditRatingDocument   {
  @JsonProperty("legal_entity_id")
  private String legalEntityId = null;

  @JsonProperty("credit_rating_agency_name")
  private String creditRatingAgencyName = null;

  @JsonProperty("credit_rating_amount")
  private Integer creditRatingAmount = null;

  @JsonProperty("credit_rating")
  private String creditRating = null;

  @JsonProperty("credit_rating_issuance_date")
  private Date creditRatingIssuanceDate = null;

  @JsonProperty("credit_rating_effective_date")
  private Date creditRatingEffectiveDate = null;

  @JsonProperty("credit_rating_expiry_date")
  private Date creditRatingExpiryDate = null;

  @JsonProperty("modified_by")
  private String modifiedBy = null;

  @JsonProperty("last_modified")
  private Date lastModified = null;

  public LegalEntityCreditRatingDocument legalEntityId(String legalEntityId) {
    this.legalEntityId = legalEntityId;
    return this;
  }

   /**
   * Unique identifier of the Legal Entity that this document is Issued for
   * @return legalEntityId
  **/
  @ApiModelProperty(value = "Unique identifier of the Legal Entity that this document is Issued for")
  public String getLegalEntityId() {
    return legalEntityId;
  }

  public void setLegalEntityId(String legalEntityId) {
    this.legalEntityId = legalEntityId;
  }

  public LegalEntityCreditRatingDocument creditRatingAgencyName(String creditRatingAgencyName) {
    this.creditRatingAgencyName = creditRatingAgencyName;
    return this;
  }

   /**
   * Unique identifier for the Credit Rating Agency
   * @return creditRatingAgencyName
  **/
  @ApiModelProperty(value = "Unique identifier for the Credit Rating Agency")
  public String getCreditRatingAgencyName() {
    return creditRatingAgencyName;
  }

  public void setCreditRatingAgencyName(String creditRatingAgencyName) {
    this.creditRatingAgencyName = creditRatingAgencyName;
  }

  public LegalEntityCreditRatingDocument creditRatingAmount(Integer creditRatingAmount) {
    this.creditRatingAmount = creditRatingAmount;
    return this;
  }

   /**
   * Unique identifier of the CP Program that this document is associated with
   * @return creditRatingAmount
  **/
  @ApiModelProperty(value = "Unique identifier of the CP Program that this document is associated with")
  public Integer getCreditRatingAmount() {
    return creditRatingAmount;
  }

  public void setCreditRatingAmount(Integer creditRatingAmount) {
    this.creditRatingAmount = creditRatingAmount;
  }

  public LegalEntityCreditRatingDocument creditRating(String creditRating) {
    this.creditRating = creditRating;
    return this;
  }

   /**
   * Rating assigned by the CRA
   * @return creditRating
  **/
  @ApiModelProperty(value = "Rating assigned by the CRA")
  public String getCreditRating() {
    return creditRating;
  }

  public void setCreditRating(String creditRating) {
    this.creditRating = creditRating;
  }

  public LegalEntityCreditRatingDocument creditRatingIssuanceDate(Date creditRatingIssuanceDate) {
    this.creditRatingIssuanceDate = creditRatingIssuanceDate;
    return this;
  }

   /**
   * Rating issuance date
   * @return creditRatingIssuanceDate
  **/
  @ApiModelProperty(value = "Rating issuance date")
  public Date getCreditRatingIssuanceDate() {
    return creditRatingIssuanceDate;
  }

  public void setCreditRatingIssuanceDate(Date creditRatingIssuanceDate) {
    this.creditRatingIssuanceDate = creditRatingIssuanceDate;
  }

  public LegalEntityCreditRatingDocument creditRatingEffectiveDate(Date creditRatingEffectiveDate) {
    this.creditRatingEffectiveDate = creditRatingEffectiveDate;
    return this;
  }

   /**
   * Rating effective date, which can be different from the issuance date
   * @return creditRatingEffectiveDate
  **/
  @ApiModelProperty(value = "Rating effective date, which can be different from the issuance date")
  public Date getCreditRatingEffectiveDate() {
    return creditRatingEffectiveDate;
  }

  public void setCreditRatingEffectiveDate(Date creditRatingEffectiveDate) {
    this.creditRatingEffectiveDate = creditRatingEffectiveDate;
  }

  public LegalEntityCreditRatingDocument creditRatingExpiryDate(Date creditRatingExpiryDate) {
    this.creditRatingExpiryDate = creditRatingExpiryDate;
    return this;
  }

   /**
   * Rating expiry date
   * @return creditRatingExpiryDate
  **/
  @ApiModelProperty(value = "Rating expiry date")
  public Date getCreditRatingExpiryDate() {
    return creditRatingExpiryDate;
  }

  public void setCreditRatingExpiryDate(Date creditRatingExpiryDate) {
    this.creditRatingExpiryDate = creditRatingExpiryDate;
  }

  public LegalEntityCreditRatingDocument modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

   /**
   * Unique identifier of the Logged-in User that performed the action. This is required for Audit History
   * @return modifiedBy
  **/
  @ApiModelProperty(value = "Unique identifier of the Logged-in User that performed the action. This is required for Audit History")
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public LegalEntityCreditRatingDocument lastModified(Date lastModified) {
    this.lastModified = lastModified;
    return this;
  }

   /**
   * Last Modified Date for this CPIssue. This is required for Audit History
   * @return lastModified
  **/
  @ApiModelProperty(value = "Last Modified Date for this CPIssue. This is required for Audit History")
  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LegalEntityCreditRatingDocument legalEntityCreditRatingDocument = (LegalEntityCreditRatingDocument) o;
    return Objects.equals(this.legalEntityId, legalEntityCreditRatingDocument.legalEntityId) &&
        Objects.equals(this.creditRatingAgencyName, legalEntityCreditRatingDocument.creditRatingAgencyName) &&
        Objects.equals(this.creditRatingAmount, legalEntityCreditRatingDocument.creditRatingAmount) &&
        Objects.equals(this.creditRating, legalEntityCreditRatingDocument.creditRating) &&
        Objects.equals(this.creditRatingIssuanceDate, legalEntityCreditRatingDocument.creditRatingIssuanceDate) &&
        Objects.equals(this.creditRatingEffectiveDate, legalEntityCreditRatingDocument.creditRatingEffectiveDate) &&
        Objects.equals(this.creditRatingExpiryDate, legalEntityCreditRatingDocument.creditRatingExpiryDate) &&
        Objects.equals(this.modifiedBy, legalEntityCreditRatingDocument.modifiedBy) &&
        Objects.equals(this.lastModified, legalEntityCreditRatingDocument.lastModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(legalEntityId, creditRatingAgencyName, creditRatingAmount, creditRating, creditRatingIssuanceDate, creditRatingEffectiveDate, creditRatingExpiryDate, modifiedBy, lastModified);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LegalEntityCreditRatingDocument {\n");
    
    sb.append("    legalEntityId: ").append(toIndentedString(legalEntityId)).append("\n");
    sb.append("    creditRatingAgencyName: ").append(toIndentedString(creditRatingAgencyName)).append("\n");
    sb.append("    creditRatingAmount: ").append(toIndentedString(creditRatingAmount)).append("\n");
    sb.append("    creditRating: ").append(toIndentedString(creditRating)).append("\n");
    sb.append("    creditRatingIssuanceDate: ").append(toIndentedString(creditRatingIssuanceDate)).append("\n");
    sb.append("    creditRatingEffectiveDate: ").append(toIndentedString(creditRatingEffectiveDate)).append("\n");
    sb.append("    creditRatingExpiryDate: ").append(toIndentedString(creditRatingExpiryDate)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
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

