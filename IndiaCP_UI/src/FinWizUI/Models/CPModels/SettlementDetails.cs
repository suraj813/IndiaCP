/*
 * IndiaCP API
 *
 * This API will drive the UI
 *
 * OpenAPI spec version: 1.0.0
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

using System;
using System.Linq;
using System.IO;
using System.Text;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Runtime.Serialization;
using Newtonsoft.Json;

namespace IO.Swagger.Models
{
    /// <summary>
    /// 
    /// </summary>
    [DataContract]
    public partial class SettlementDetails :  IEquatable<SettlementDetails>
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="SettlementDetails" /> class.
        /// </summary>
        /// <param name="PaymentAccountDetails">PaymentAccountDetails.</param>
        /// <param name="DepositoryAccountDetails">DepositoryAccountDetails.</param>
        public SettlementDetails(PaymentAccountDetails PaymentAccountDetails = null, DepositoryAccountDetails DepositoryAccountDetails = null)
        {
            this.PaymentAccountDetails = PaymentAccountDetails;
            this.DepositoryAccountDetails = DepositoryAccountDetails;
            
        }

        /// <summary>
        /// Gets or Sets PaymentAccountDetails
        /// </summary>
        [DataMember(Name="paymentAccountDetails")]
        public PaymentAccountDetails PaymentAccountDetails { get; set; }

        /// <summary>
        /// Gets or Sets DepositoryAccountDetails
        /// </summary>
        [DataMember(Name="depositoryAccountDetails")]
        public DepositoryAccountDetails DepositoryAccountDetails { get; set; }


        /// <summary>
        /// Returns the string presentation of the object
        /// </summary>
        /// <returns>String presentation of the object</returns>
        public override string ToString()
        {
            var sb = new StringBuilder();
            sb.Append("class SettlementDetails {\n");
            sb.Append("  PaymentAccountDetails: ").Append(PaymentAccountDetails).Append("\n");
            sb.Append("  DepositoryAccountDetails: ").Append(DepositoryAccountDetails).Append("\n");
            sb.Append("}\n");
            return sb.ToString();
        }

        /// <summary>
        /// Returns the JSON string presentation of the object
        /// </summary>
        /// <returns>JSON string presentation of the object</returns>
        public string ToJson()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }

        /// <summary>
        /// Returns true if objects are equal
        /// </summary>
        /// <param name="obj">Object to be compared</param>
        /// <returns>Boolean</returns>
        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != GetType()) return false;
            return Equals((SettlementDetails)obj);
        }

        /// <summary>
        /// Returns true if SettlementDetails instances are equal
        /// </summary>
        /// <param name="other">Instance of SettlementDetails to be compared</param>
        /// <returns>Boolean</returns>
        public bool Equals(SettlementDetails other)
        {

            if (ReferenceEquals(null, other)) return false;
            if (ReferenceEquals(this, other)) return true;

            return 
                (
                    this.PaymentAccountDetails == other.PaymentAccountDetails ||
                    this.PaymentAccountDetails != null &&
                    this.PaymentAccountDetails.Equals(other.PaymentAccountDetails)
                ) && 
                (
                    this.DepositoryAccountDetails == other.DepositoryAccountDetails ||
                    this.DepositoryAccountDetails != null &&
                    this.DepositoryAccountDetails.Equals(other.DepositoryAccountDetails)
                );
        }

        /// <summary>
        /// Gets the hash code
        /// </summary>
        /// <returns>Hash code</returns>
        public override int GetHashCode()
        {
            // credit: http://stackoverflow.com/a/263416/677735
            unchecked // Overflow is fine, just wrap
            {
                int hash = 41;
                // Suitable nullity checks etc, of course :)
                    if (this.PaymentAccountDetails != null)
                    hash = hash * 59 + this.PaymentAccountDetails.GetHashCode();
                    if (this.DepositoryAccountDetails != null)
                    hash = hash * 59 + this.DepositoryAccountDetails.GetHashCode();
                return hash;
            }
        }

        #region Operators

        public static bool operator ==(SettlementDetails left, SettlementDetails right)
        {
            return Equals(left, right);
        }

        public static bool operator !=(SettlementDetails left, SettlementDetails right)
        {
            return !Equals(left, right);
        }

        #endregion Operators

    }
}
