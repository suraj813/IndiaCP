/**
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

// <reference path="api.d.ts" />

/* tslint:disable:no-unused-variable member-ordering */

module app.services {
    "use strict";

    export interface IUserService {
        transactionhistoryIssuerCpProgramIdGet(issuer: string, cpProgramId: string, extraHttpRequestParams?: any):ng.IHttpPromise<Array<ICPProgram>>;
    }

    export interface ICPProgram {
        /**
         * Unique identifier representing a specific CP Program raised by an Issuer
         */
        "programId"?: string;

        /**
         * Name of the CP Program
         */
        "name"?: string;

        /**
         * Type of the CP Program
         */
        "type"?: string;

        /**
         * Purpose of the CP Program
         */
        "purpose"?: string;

        /**
         * Unique identifier of the Issuer. This will also uniquely map to the DL Node of the Issuer
         */
        "issuerId"?: string;

        /**
         * Display name of the Issuer
         */
        "issuerName"?: string;

        /**
         * Date when the CP Program was initiated. India regulations mandate that a program should be fully allocated within 2 weeks of commencement.
         */
        "issueCommencementDate"?: Date;

        /**
         * Total borrowing amount under this CP Program
         */
        "programSize"?: number;

        /**
         * Currency of the issue of CP notes
         */
        "programCurrency"?: string;

        /**
         * No of days to maturity from the value date
         */
        "maturityDays"?: number;

        /**
         * Unique identifier of the IPA. This will also uniquely map to the DL Node of the IPA
         */
        "ipaId"?: string;

        /**
         * Display name of the IPA
         */
        "ipaName"?: string;

        /**
         * Unique identifier of the Depository (NSDL). This will also uniquely map to the DL Node of the Depository
         */
        "depositoryId"?: string;

        /**
         * Display name of the Depository
         */
        "depositoryName"?: string;

        /**
         * Unique identifier of the documents sent to the depository to generate ISIN
         */
        "isinGenerationRequestDocId"?: string;

        /**
         * Unique identifier of the documents sent to the IPA to certify the CP Program
         */
        "ipaVerificationRequestDocId"?: string;

        /**
         * Unique identifier of the IPA certificate issued by the IPA on verification of the CP Program and supporting Issuer documents
         */
        "ipaCertificateDocId"?: string;

        /**
         * Unique identifier of the Corporate Action Form generated by the Issuer to allot CP
         */
        "corporateActionFormDocId"?: string;

        /**
         * Unique identifier of the Allotment Letter generated by IPA for CP transfer to Investor DP account
         */
        "allotmentLetterDocId"?: string;

    }

    class UserService implements IUserService {
        protected basePath = "http://finwizui.azurewebsites.net/api";
        public defaultHeaders : any = {};

        static $inject: string[] = ["$http", "$httpParamSerializer", "basePath"];

        constructor(protected $http: ng.IHttpService, protected $httpParamSerializer?: (d: any) => any, basePath?: string) {
            if (basePath !== undefined) {
                this.basePath = basePath;
            }
        }

        private extendObj<T1,T2>(objA: T1, objB: T2) {
            for(let key in objB){
                if(objB.hasOwnProperty(key)){
                     objA[key.toString()] = objB[key.toString()]; 
                }
            }
            return <T1&T2>objA;
        }

        /**
         * Complete audit log of all changes/versions of given CP Program
         * A given CP Program once initiated undergoes various changes as it progresses through the trade lifecycle of generating ISIN, generating Deal Confirmations with each identified Investor, getting IPA Verification till the final settlement of all Deals and followed by redemption of the CP at the Maturity Date. This API will return this complete log history.
         * @param issuer issuer id that uniquely maps to the DL node
         * @param cpProgramId CP Program ID that uniquely identifies the CP Program issued by the Issuer
         */
        public transactionhistoryIssuerCpProgramIdGet (issuer: string, cpProgramId: string, extraHttpRequestParams?: any ) : ng.IHttpPromise<Array<ICPProgram>> {
            const localVarPath = this.basePath + "/transactionhistory/{issuer}/{cpProgramId}"
                .replace("{" + "issuer" + "}", String(issuer))
                .replace("{" + "cpProgramId" + "}", String(cpProgramId));

            let queryParameters: any = {};
            let headerParams: any = this.extendObj({}, this.defaultHeaders);
            // verify required parameter "issuer" is not null or undefined
            if (issuer === null || issuer === undefined) {
                throw new Error("Required parameter issuer was null or undefined when calling transactionhistoryIssuerCpProgramIdGet.");
            }
            // verify required parameter "cpProgramId" is not null or undefined
            if (cpProgramId === null || cpProgramId === undefined) {
                throw new Error("Required parameter cpProgramId was null or undefined when calling transactionhistoryIssuerCpProgramIdGet.");
            }
            let httpRequestParams: any = {
                method: "GET",
                url: localVarPath,
                json: true,
                params: queryParameters,
                headers: headerParams
            };

            if (extraHttpRequestParams) {
                httpRequestParams = this.extendObj(httpRequestParams, extraHttpRequestParams);
            }

            return this.$http(httpRequestParams);
        }
    }
}