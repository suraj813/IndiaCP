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

/**
 * PaymentAccountDetails
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-01-03T07:18:23.010Z")
public class PaymentAccountDetails   {
  @JsonProperty("creditor_name")
  private String creditorName = null;

  @JsonProperty("bank_account_no")
  private String bankAccountNo = null;

  @JsonProperty("bank_account_type")
  private String bankAccountType = null;

  @JsonProperty("bank_name")
  private String bankName = null;

  @JsonProperty("rtgs_ifsc_code")
  private String rtgsIfscCode = null;

  public PaymentAccountDetails creditorName(String creditorName) {
    this.creditorName = creditorName;
    return this;
  }

   /**
   * Name in which the payment has to be made. For e.g. Barclays Investments & Loans (India) Ltd CP
   * @return creditorName
  **/
  @ApiModelProperty(value = "Name in which the payment has to be made. For e.g. Barclays Investments & Loans (India) Ltd CP")
  public String getCreditorName() {
    return creditorName;
  }

  public void setCreditorName(String creditorName) {
    this.creditorName = creditorName;
  }

  public PaymentAccountDetails bankAccountNo(String bankAccountNo) {
    this.bankAccountNo = bankAccountNo;
    return this;
  }

   /**
   * Bank account no. at the IPA Bank
   * @return bankAccountNo
  **/
  @ApiModelProperty(value = "Bank account no. at the IPA Bank")
  public String getBankAccountNo() {
    return bankAccountNo;
  }

  public void setBankAccountNo(String bankAccountNo) {
    this.bankAccountNo = bankAccountNo;
  }

  public PaymentAccountDetails bankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
    return this;
  }

   /**
   * Bank account type. For e.g. current account
   * @return bankAccountType
  **/
  @ApiModelProperty(value = "Bank account type. For e.g. current account")
  public String getBankAccountType() {
    return bankAccountType;
  }

  public void setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public PaymentAccountDetails bankName(String bankName) {
    this.bankName = bankName;
    return this;
  }

   /**
   * Name of the IPA Bank
   * @return bankName
  **/
  @ApiModelProperty(value = "Name of the IPA Bank")
  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public PaymentAccountDetails rtgsIfscCode(String rtgsIfscCode) {
    this.rtgsIfscCode = rtgsIfscCode;
    return this;
  }

   /**
   * RTGS IFSC code of the IPA Bank to receive payments
   * @return rtgsIfscCode
  **/
  @ApiModelProperty(value = "RTGS IFSC code of the IPA Bank to receive payments")
  public String getRtgsIfscCode() {
    return rtgsIfscCode;
  }

  public void setRtgsIfscCode(String rtgsIfscCode) {
    this.rtgsIfscCode = rtgsIfscCode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentAccountDetails paymentAccountDetails = (PaymentAccountDetails) o;
    return Objects.equals(this.creditorName, paymentAccountDetails.creditorName) &&
        Objects.equals(this.bankAccountNo, paymentAccountDetails.bankAccountNo) &&
        Objects.equals(this.bankAccountType, paymentAccountDetails.bankAccountType) &&
        Objects.equals(this.bankName, paymentAccountDetails.bankName) &&
        Objects.equals(this.rtgsIfscCode, paymentAccountDetails.rtgsIfscCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creditorName, bankAccountNo, bankAccountType, bankName, rtgsIfscCode);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentAccountDetails {\n");
    
    sb.append("    creditorName: ").append(toIndentedString(creditorName)).append("\n");
    sb.append("    bankAccountNo: ").append(toIndentedString(bankAccountNo)).append("\n");
    sb.append("    bankAccountType: ").append(toIndentedString(bankAccountType)).append("\n");
    sb.append("    bankName: ").append(toIndentedString(bankName)).append("\n");
    sb.append("    rtgsIfscCode: ").append(toIndentedString(rtgsIfscCode)).append("\n");
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

