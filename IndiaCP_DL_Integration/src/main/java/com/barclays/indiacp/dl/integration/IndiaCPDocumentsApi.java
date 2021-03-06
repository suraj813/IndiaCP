package com.barclays.indiacp.dl.integration;

import com.barclays.indiacp.dl.utils.DLAttachmentUtils;
//import com.barclays.indiacp.model.Roles;
//import com.barclays.indiacp.model.VerificationResult;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.barclays.indiacp.dl.utils.ErrorUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
//import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;



/**
 * Created by ritukedia on 24/12/16.
 */
@Path("indiacpdocuments")
public class IndiaCPDocumentsApi {

    @POST
    @Path("generateISINDocuments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateISINDocuments(String inputJSON) {

        String pdfFileBase64 = "";

         try {
             Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
             Roles roleObj = gson.fromJson(inputJSON.toString(), Roles.class);
             Roles.CP cpDetails = roleObj.cp;
             Roles.Investor invDetails = roleObj.investor;
             Roles.IPA ipaDetails = roleObj.ipa;
             Roles.NSDL nsdlDetails = roleObj.nsdl;


             Font mainFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                     Font.BOLD);
             Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                     Font.BOLD);
             Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                     Font.BOLD);
             Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12);
             Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

             Integer units = 0;
             String tempMatValue = cpDetails.maturityValue.replaceAll("[^0-9]", "");
             String tempFaceValue = cpDetails.faceValue.replaceAll("[^0-9]", "");

             if (tempFaceValue.length() != 0 || tempMatValue.length() != 0) {

                 units = Integer.parseInt(tempMatValue) / Integer.parseInt(tempFaceValue);
             }
             DateFormat dtf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
             //allotmentDate = new Date();
             String allotmentDateStr = cpDetails.allotDate;
             String maturityDateStr = cpDetails.matDate;



             final File tempFile = File.createTempFile("ISIN", ".pdf");

             tempFile.deleteOnExit();

             String path = tempFile.getPath();

             // Instantiation of document object
             Document document = new Document(PageSize.A4, 50, 50, 50, 50);

             // Creation of PdfWriter object
             PdfWriter writer = PdfWriter.getInstance(document,
                     new FileOutputStream(path));

             document.open();

             // Creation of paragraph object
             Paragraph heading = new Paragraph("Letter of Intent for Commercial Paper", mainFont);
             heading.setAlignment(Element.ALIGN_CENTER);
             document.add(heading);

             document.add(new Paragraph("\n\n"));


             DateFormat dtf1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
             String today = dtf1.format(new Date());

             document.add(new Paragraph("Date: " + today, normal));

             document.add(new Paragraph("\n"));


             Paragraph title11 = new Paragraph("The Managing Director", smallBold);
             document.add(title11);
             // Listing 7. Creation of list object

             Paragraph nsdlAddress = new Paragraph(String.format(nsdlDetails.nsdlAddress.replaceAll(", ", "\n")), smallFont);
             nsdlAddress.setIndentationLeft(0);

             document.add(nsdlAddress);

             document.add(new Paragraph("\n"));

             Paragraph docSubject = new Paragraph("Sub: Admission of Commercial Paper", smallBold);
             document.add(docSubject);
             document.add(new Paragraph("\n"));
             Paragraph docAddressedPerson = new Paragraph("Dear Sir,", normal);
             document.add(docAddressedPerson);

             document.add(new Paragraph("\n"));

             String body = "We are pleased to inform you that our company has decided to offer the following Commercial Paper " +
                     "as eligible securities under the Depositories Act, 1996. We confirm that these instruments will be governed" +
                     " by the terms and conditions indicated in the tripartite agreement entered between " + cpDetails.issuerName
                     + ", " + cpDetails.secondParty + ", and " + nsdlDetails.depositoryName + ". The details of the instrument are as follows:";
             document.add(new Paragraph(body, normal));

             document.add(new Paragraph("\n"));

             PdfPTable t = new PdfPTable(6);


             PdfPCell c1 = new PdfPCell(new Phrase("Sr. No.", smallFont));
             t.addCell(c1);
             PdfPCell c2 = new PdfPCell(new Phrase("Description of the Security", smallFont));
             t.addCell(c2);
             PdfPCell c3 = new PdfPCell(new Phrase("Allotment Date", smallFont));
             t.addCell(c3);
             PdfPCell c4 = new PdfPCell(new Phrase("Maturity Date", smallFont));
             t.addCell(c4);
             PdfPCell c5 = new PdfPCell(new Phrase("Issue Value", smallFont));
             t.addCell(c5);
             PdfPCell c6 = new PdfPCell(new Phrase("Redemption Value", smallFont));
             t.addCell(c6);
             t.addCell(new Phrase("1", smallFont));
             t.addCell(new Phrase(cpDetails.desc, smallFont));
             t.addCell(new Phrase(allotmentDateStr, smallFont));
             t.addCell(new Phrase(maturityDateStr, smallFont));
             t.addCell(new Phrase(cpDetails.currency + " " +  cpDetails.issueValue, smallFont));
             t.addCell(new Phrase(cpDetails.currency + " " +  cpDetails.redemValue, smallFont));
             t.setTotalWidth(PageSize.A4.getWidth() - 100);
             t.setLockedWidth(true);
             document.add(t);

             String body2 = "We understand that " + nsdlDetails.depositoryName + " will not levy any charges to the investors holding securities in the " +
                     "Demat form. We agree to pay a sum of " + cpDetails.currency + " " +  cpDetails.tax + " + ST per calendar year towards admission / electronic credit" +
                     " of these securities in the " + nsdlDetails.depositoryName + " system.";

             document.add(new Paragraph(body2, normal));

             document.add(new Paragraph("\n"));

             String body3 = "We understand that for the purpose of allotment and redemption an account has to be opened by IPA " +
                     "with a depository participant of " + nsdlDetails.depositoryName + ". The same is being " +
                     "communicated to " + nsdlDetails.depositoryName + " (in the MCF) and also to the holder of the " +
                     "Commercial Paper.";
             document.add(new Paragraph(body3, normal));

             document.add(new Paragraph("\n\n"));

             document.add(new Paragraph("Yours faithfully", smallBold));

             document.add(new Paragraph("For " + cpDetails.issuerName, smallBold));

             document.add(new Paragraph("(Authorized Signatories)", smallBold));
             document.add(new Paragraph("\n\n"));


             document.newPage();
             document.add(new Paragraph("MASTER FILE CREATION FORM FOR COMMERCIAL PAPER", normal));
             document.add(new Paragraph("\n"));
             document.add(new Paragraph("Full name of the Company : " + cpDetails.issuerName.toUpperCase(), smallBold));
             document.add(new Paragraph("Corporate Identity Number (CIN): " + cpDetails.CIN, normal));
             document.add(new Paragraph("Whether company has already signed agreement with " +
                     "" + nsdlDetails.depositoryName + " for any other instrument ? " +
                     "(YES/NO) : " + cpDetails.NSDLHistory, normal));

             document.add(new Paragraph("\n\nSection A", smallBold));

             document.add(new Paragraph("Address of the Regd. office including telephone, fax nos. and email " +
                     "addresses : \n" + cpDetails.issuerAddress, normal));
             document.add(new Paragraph("Type of entity :" + cpDetails.entityType, smallBold));


             document.newPage();
             document.add(new Paragraph("Contact Persons of the Company\n\n", normal));
             PdfPTable issuerTable = new PdfPTable(2);

             issuerTable.addCell(new Phrase("Compliance Officer", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.complianceOfficer, smallBold));
             issuerTable.addCell(new Phrase("Designation/Dept", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.complianceOfficerDept, smallFont));
             issuerTable.addCell(new Phrase("Address:", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.complianceOfficerAddress, smallFont));
             issuerTable.addCell(new Phrase("Telephone", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.complianceOfficerContactNo, smallFont));
             issuerTable.addCell(new Phrase("Email Address", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.complianceOfficerEmail, smallFont));
             issuerTable.addCell(new Phrase(" ", smallFont));
             issuerTable.addCell(new Phrase(" ", smallFont));
             issuerTable.addCell(new Phrase("Investor Relations Officer", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.investorRelationsOfficer, smallFont));
             issuerTable.addCell(new Phrase("Designation/Dept", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.investorRelationsOfficerDept, smallFont));
             issuerTable.addCell(new Phrase("Address:", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.investorRelationsOfficerAddress, smallFont));
             issuerTable.addCell(new Phrase("Telephone", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.investorRelationsOfficerContactNo, smallFont));
             issuerTable.addCell(new Phrase("Email Address", smallFont));
             issuerTable.addCell(new Phrase(cpDetails.investorRelationsOfficerEmail, smallFont));
             document.add(issuerTable);
             document.add(new Paragraph("Address of the registry operations where the physical securities for " +
                     "dematerialisaion are to be delivered by the Depository Participants\n\n", normal));
             PdfPTable issuerDTable = new PdfPTable(2);

             issuerDTable.addCell(new Phrase("Name of organisation", smallFont));
             issuerDTable.addCell(new Phrase(cpDetails.issuerName, smallBold));
             issuerDTable.addCell(new Phrase("Name of contact person", smallFont));
             issuerDTable.addCell(new Phrase(cpDetails.operationsOfficer, smallBold));
             issuerDTable.addCell(new Phrase("Designation/Dept", smallFont));
             issuerDTable.addCell(new Phrase(cpDetails.operationsOfficerDept, smallFont));
             issuerDTable.addCell(new Phrase("Address:", smallFont));
             issuerDTable.addCell(new Phrase(cpDetails.operationsOfficerAddress, smallFont));
             issuerDTable.addCell(new Phrase("Telephone", smallFont));
             issuerDTable.addCell(new Phrase(cpDetails.operationsOfficerContactNo, smallFont));
             issuerDTable.addCell(new Phrase("Email Address", smallFont));
             issuerDTable.addCell(new Phrase(cpDetails.operationsOfficerEmail, smallFont));

             document.add(issuerDTable);


             document.newPage();
             document.add(new Paragraph("Section B", smallBold));
             document.add(new Paragraph("1) Type of Security\n\n", normal));

             PdfPTable secTable = new PdfPTable(4);
             secTable.addCell(new Phrase("Sr. No.", smallBold));
             secTable.addCell(new Phrase("Details", smallBold));
             secTable.addCell(new Phrase("Yes/No", smallBold));
             secTable.addCell(new Phrase("Remark (if Any)", smallBold));
             secTable.addCell(new Phrase("1.", smallFont));
             secTable.addCell(new Phrase("Guarantee", smallFont));
             secTable.addCell(new Phrase("Yes", smallFont));
             secTable.addCell(new Phrase("", smallFont));
             secTable.addCell(new Phrase("2.", smallFont));
             secTable.addCell(new Phrase("Type of Interest", smallFont));
             secTable.addCell(new Phrase("Zero Rate", smallFont));
             secTable.addCell(new Phrase("Discounted", smallFont));
             document.add(secTable);

             document.add(new Paragraph("Security Details\n\n", smallBold));
             PdfPTable secDTable = new PdfPTable(2);
             secDTable.addCell(new Phrase("Security Name", smallFont));
             secDTable.addCell(new Phrase(cpDetails.desc, smallBold));
             secDTable.addCell(new Phrase("Secured/Unsecured", smallBold));
             secDTable.addCell(new Phrase("Unsecured", smallBold));
             secDTable.addCell(new Phrase("Issue Date", smallFont));
             secDTable.addCell(new Phrase(cpDetails.tradeDate, smallFont));
             secDTable.addCell(new Phrase("Redemption Date", smallFont));
             secDTable.addCell(new Phrase(cpDetails.matDate, smallFont));
             secDTable.addCell(new Phrase("Face Value per Security (Rs.)", smallFont));
             secDTable.addCell(new Phrase(cpDetails.currency + " " + cpDetails.faceValue, smallFont));
             secDTable.addCell(new Phrase("Tenure of the security (in days)", smallFont));
             secDTable.addCell(new Phrase(dateDiff(cpDetails.valueDate.toString(), cpDetails.matDate.toString()), normal));
             secDTable.addCell(new Phrase("Total Issue size", smallFont));
             secDTable.addCell(new Phrase(cpDetails.currency + " " + cpDetails.totalAmount, smallFont));
             document.add(secDTable);

             document.add(new Paragraph("Credit Rating for the Issue : " + cpDetails.creditRating, normal));
             document.add(new Paragraph("Credit Rating Agency : " + cpDetails.ratingIssuedBy, normal));
             document.add(new Paragraph("Date of Credit Rating : " + cpDetails.dateOfRating, normal));
             document.add(new Paragraph("Back-stop facility  : " + "Yes/No", normal));
             document.add(new Paragraph("Name of Back-stop facility provider : ", normal));


             document.newPage();
             document.add(new Paragraph("IPA Details\n\n", smallBold));
             PdfPTable ipaTable = new PdfPTable(2);
             ipaTable.addCell(new Phrase("Name of the IPA", smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaName, smallBold));
             ipaTable.addCell(new Phrase("Name of Contact Person", smallBold));
             ipaTable.addCell(new Phrase(ipaDetails.ipaContactPerson, smallBold));
             ipaTable.addCell(new Phrase("Designation/Dept", smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaContactDept, smallFont));
             ipaTable.addCell(new Phrase("Address:", smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaContactAddress, smallFont));
             ipaTable.addCell(new Phrase("Telephone", smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaContactTelephone, smallFont));
             ipaTable.addCell(new Phrase("Email Address", smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaContactEmail, normal));
             ipaTable.addCell(new Phrase("Total Issue size", smallFont));
             ipaTable.addCell(new Phrase(cpDetails.totalAmount , smallFont));
             ipaTable.addCell(new Phrase("IPA Demat Account Details " , smallFont));
             ipaTable.addCell(new Phrase(" " , smallFont));
             ipaTable.addCell(new Phrase("DP ID" , smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaAllotmentDPID , smallFont));
             ipaTable.addCell(new Phrase("DP Name" , smallFont));
             ipaTable.addCell(new Phrase(ipaDetails.ipaDPName , smallFont));
             ipaTable.addCell(new Phrase("CP Allotment Account No." , smallFont));
             ipaTable.addCell(new Phrase( ipaDetails.ipaAllotmentClientID , smallFont));
             ipaTable.addCell(new Phrase("CP Redemption Account No." , smallFont));
             ipaTable.addCell(new Phrase( ipaDetails.ipaRedemptionClientID , smallFont));
             document.add(ipaTable);
             document.add(new Paragraph("Note: Though the ISIN can be activated without the IPA account Details," +
                     " the coporate action cannot be initiated if these details are not provided. Hence the issuer has to ensure" +
                     "that these details reach " + nsdlDetails.depositoryName + " prior to the corporate action for all such cases where it is not provided in the MCF.", smallBold));

             document.newPage();
             document.add(new Paragraph("Stock Exchange Details\n\n", smallBold));
             PdfPTable stTable = new PdfPTable(2);
             stTable.addCell(new Phrase("Name of the Stock Exchange", smallFont));
             stTable.addCell(new Phrase(cpDetails.stockExchange, smallBold));
             stTable.addCell(new Phrase("Listed", smallBold));
             stTable.addCell(new Phrase("Yes", smallBold));
             stTable.addCell(new Phrase("Permitted to Trade", smallBold));
             stTable.addCell(new Phrase("Yes", smallBold));
             stTable.addCell(new Phrase("Stock Exchange security code (if any)", smallBold));
             stTable.addCell(new Phrase("", smallBold));
             document.add(stTable);

             document.add(new Paragraph("We hereby certify that this issue of Commercial Paper is being made in " +
                     "accordance with the Non-Banking Companies (Accpetance of Deposits through Commercial Papers) " +
                     "Direction 1989, as amended till date, issued by RBI. We hereby also certify that the same is in conformity" +
                     "with the guidelines issued by FIMMDA approved by RBI for issuance of Commercial Paper effective from 30th June, 2001." +
                     "The information provided above is correct to the best of our knowledge and belief.", normal));

             document.add(new Paragraph("\n\nDate : " + today, normal));
             document.add(new Paragraph("Stamp of the Issuer : \n\n" , normal));
             document.add(new Paragraph("Name : " + cpDetails.investorRelationsOfficer , normal));
             document.add(new Paragraph("Designation : " + cpDetails.investorRelationsOfficerDept, normal));
             document.add(new Paragraph("Signature : \n\n" , normal));
             document.add(new Paragraph("Name : " + cpDetails.operationsOfficer , normal));
             document.add(new Paragraph("Designation : " + cpDetails.operationsOfficerDept, normal));
             document.add(new Paragraph("Signature : \n\n" , normal));


             document.newPage();


             Paragraph issuerPara = new Paragraph(cpDetails.issuerName + "\n"
                     + String.format(cpDetails.issuerAddress.replaceAll(", ", "\n")) + "\nT: " + cpDetails.officialContactNum
                     + "\nF: " + cpDetails.officialFaxNum + "\n" + cpDetails.officialEmail + "\n" +
                     cpDetails.officialWebsite + "\nDate: " + today + "\nCIN: " + cpDetails.CIN, smallFont);
             issuerPara.setIndentationLeft(300);
             document.add(issuerPara);
             document.add(new Paragraph("To,\n" + nsdlDetails.nsdlContactPerson + "\n" + String.format(nsdlDetails.nsdlAddress.replaceAll(", ", "\n") + "\n"), normal));
             document.add(new Paragraph("We wish to issue Commercial Papers (CP) of our company in the demat mode." +
                     " The details of the CP are as follows: \n\n", normal));


             PdfPTable isinTable = new PdfPTable(2);


             isinTable.addCell(new Phrase("ISIN", smallFont));
             isinTable.addCell(new Phrase(cpDetails.isinCode, smallBold));
             isinTable.addCell(new Phrase("Date of Allotment ", smallFont));
             isinTable.addCell(new Phrase(allotmentDateStr, smallFont));
             isinTable.addCell(new Phrase("Face Value", smallFont));
             isinTable.addCell(new Phrase(cpDetails.currency + " " + cpDetails.faceValue, smallFont));
             isinTable.addCell(new Phrase("No. of Units to be credited", smallFont));
             isinTable.addCell(new Phrase(units.toString(), smallFont));
             isinTable.addCell(new Phrase("Name of the IPA", smallFont));
             isinTable.addCell(new Phrase(ipaDetails.ipaName, smallFont));
             isinTable.addCell(new Phrase("IPA CP Allotment Account - DP ID", smallFont));
             isinTable.addCell(new Phrase(ipaDetails.ipaAllotmentDPID, smallFont));
             isinTable.addCell(new Phrase("IPA CP Allotment Account - Client ID", smallFont));
             isinTable.addCell(new Phrase(ipaDetails.ipaAllotmentClientID, smallFont));
             isinTable.addCell(new Phrase("IPA CP Redemption Account - DP ID", smallFont));
             isinTable.addCell(new Phrase(ipaDetails.ipaRedemptionDPID, smallFont));
             isinTable.addCell(new Phrase("IPA CP Redemption Account - Client ID", smallFont));
             isinTable.addCell(new Phrase(ipaDetails.ipaRedemptionClientID, smallFont));

             document.add(isinTable);
             document.add(new Paragraph("We request you to credit the above mentioned securities to the CP Allotment" +
                     " account of the IPA.\n\n", normal));
             document.add(new Paragraph("Yours faithfully,", normal));
             document.add(new Paragraph("Name of the Authorized Signatory", normal));

             document.close();

             pdfFileBase64 = encodeFileToBase64Binary(path);

         }  catch (Exception ex) {
             ex.printStackTrace();
             return ErrorUtils.errorHttpResponse(ex, "ISIN Document Generation Error");
         }

        return Response.status(Response.Status.OK).entity(pdfFileBase64).build();
    }

    @POST
    @Path("generateIPACertificateDocument")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateIPACertificateDocument(String inputJSON) {

        String pdfFileBase64 = "";
        try

        {


            final File tempFile = File.createTempFile("IPACertificate", ".pdf");
            tempFile.deleteOnExit();

            String path = tempFile.getPath();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Roles roleObj = gson.fromJson(inputJSON.toString(), Roles.class);
            Roles.CP cpDetails = roleObj.cp;
            Roles.Investor invDetails = roleObj.investor;
            Roles.IPA ipaDetails = roleObj.ipa;
            Roles.NSDL nsdlDetails = roleObj.nsdl;


            Font mainFont = new Font(Font.FontFamily.TIMES_ROMAN, 18.0F, 1);
            new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1);
            Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 1);
            Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F);
            Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10.0F);
            SimpleDateFormat dtf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            //String allotmentDateStr = dtf.format(cpDetails.allotDate);
            String allotmentDateStr = cpDetails.allotDate;
            DateFormat dtf1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            String todayStr = dtf1.format(new Date());

            String tempMatValue = cpDetails.maturityValue.replaceAll("[^0-9]", "");
            String tempFaceValue = cpDetails.faceValue.replaceAll("[^0-9]", "");

            Integer units = 0;

            if (tempFaceValue.length() != 0 || tempMatValue.length() != 0) {

                units = Integer.parseInt(tempMatValue) / Integer.parseInt(tempFaceValue);
            }


            Document document = new Document(PageSize.A4, 50.0F, 50.0F, 50.0F, 50.0F);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Paragraph heading = new Paragraph("Annexure - II\nIPA Certificate - Allotment of CP's", mainFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            document.add(new Paragraph("\n\n"));

            Paragraph ipaName = new Paragraph(ipaDetails.ipaName, smallBold);
            ipaName.setAlignment(Element.ALIGN_RIGHT);
            document.add(ipaName);


            Paragraph ipaAddress = new Paragraph(String.format(ipaDetails.ipaAddress.replaceAll(", ", "\n")), smallFont);
            ipaAddress.setIndentationLeft(400);

            document.add(ipaAddress);


            document.add(new Paragraph(todayStr, normal));

            document.add(new Paragraph("\n"));


            document.add(new Paragraph("To,\n" + nsdlDetails.nsdlContactPerson + "\n" + String.format(nsdlDetails.nsdlAddress.replaceAll(", ", "\n") + "\n\n"), smallFont));

            document.add(new Paragraph("Dear Sir,\n\nWe hereby certify " + cpDetails.issuerName.toUpperCase() +
                    ", the issuers have appointed us as the Issuing and Paying Agent(IPA) for the CP under reference.\n\n", normal));

            document.add(new Paragraph("We confirm that :\n" +
                    "1. The Board resolution of the Issuer authorising the CP has been verified by the Original." +
                    "\n2. The original of duly stamped Commercial Paper has been retained at our end" +
                    "\n3. The issuer has compiled with the RBI guidelines with reference to the issue of CP.\n\n", normal));


            document.add(new Paragraph("The Credits of the CP should be effected to the following account.\n\n" +
                    "1. DP Name" + " : " + ipaDetails.ipaDPName +
                    "\n2. DP ID" + " : " + ipaDetails.ipaAllotmentDPID +
                    "\n3. Client ID" + " : " + ipaDetails.ipaAllotmentClientID +
                    "\n4. Client Name" + " : " + ipaDetails.ipaDPName + " CP Allotment A/c" +
                    "\n5. Quantity" + " : " + units.toString() +
                    "\n6. Date of Credit" + " : " + cpDetails.valueDate +
                    "\n7. ISIN No." + " : " + cpDetails.isinCode + "\n\n", smallBold));


            document.add(new Paragraph("Thanking you,\nSincerely Yours,\n", normal));
            document.add(new Paragraph(ipaDetails.ipaName, smallBold));
            document.add(new Paragraph("Name of the Authorized Signatory", normal));
            document.close();
            pdfFileBase64 = encodeFileToBase64Binary(path);

        }  catch (Exception ex) {
            ex.printStackTrace();
            return ErrorUtils.errorHttpResponse(ex, "IPA Certificate Document Generation Error");
        }

        return Response.status(Response.Status.OK).entity(pdfFileBase64).build();
    }

    @POST
    @Path("generateDealConfirmationDocument")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateDealConfirmationDocument(String inputJSON) {

        String pdfFileBase64 = "";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Roles roleObj = gson.fromJson(inputJSON.toString(), Roles.class);
            Roles.CP cpDetails = roleObj.cp;

            Roles.Investor invDetails = roleObj.investor;
            Roles.IPA ipaDetails = roleObj.ipa;
            Roles.NSDL nsdlDetails = roleObj.nsdl;

            final File tempFile = File.createTempFile("IPA", ".pdf");

            tempFile.deleteOnExit();

            String path = tempFile.getPath();

            Font mainFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                    Font.BOLD);
            Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                    Font.BOLD);
            Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                    Font.BOLD);
            Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12);

            Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);


            DateFormat dtf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            DateFormat dtf1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            String todayStr = dtf1.format(new Date());
            //String today = dtf1.format(new Date());

               /* String dueDateStr = dtf.format(cpDetails.matDate);
                String validityStr = dtf.format(cpDetails.ratingValidityDate);
                String dateOfRatingStr = dtf.format(cpDetails.dateOfRating);
                String effectiveDateForRatingStr = dtf.format(cpDetails.effectiveDateOfRating);
                String valueDateStr = dtf.format(cpDetails.valueDate);
                String dateOfContractStr = dtf.format(cpDetails.dateOfContract);
                String tradeDateStr = dtf.format(cpDetails.tradeDate);*/

            String dueDateStr = cpDetails.matDate;
            String validityStr = cpDetails.ratingValidityDate;
            String dateOfRatingStr = cpDetails.dateOfRating;
            String effectiveDateForRatingStr = cpDetails.effectiveDateOfRating;
            String valueDateStr = cpDetails.valueDate;
            String dateOfContractStr = cpDetails.dateOfContract;
            String tradeDateStr = cpDetails.tradeDate;
            // Instantiation of document object
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            // Creation of PdfWriter object
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(path));

            document.open();

            // Creation of paragraph object
            Paragraph heading = new Paragraph("DEAL CONFIRMATION NOTE / CONTRACT NOTE", mainFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Date of contract : " + dateOfContractStr, normal));
            document.add(new Paragraph("CP (Maturity Value) : " + cpDetails.currency + " " + cpDetails.maturityValue, normal));
            document.add(new Paragraph("Due Date : " + dueDateStr, normal));
            document.add(new Paragraph("Price : " + cpDetails.currency + " "  + cpDetails.price, normal));
            document.add(new Paragraph("Disc. rate : " + cpDetails.discRate, normal));
            document.add(new Paragraph("Issue Reference : " + cpDetails.issueRef, normal));
            document.add(new Paragraph("ISIN Code : " + cpDetails.isinCode, normal));
            document.add(new Paragraph("Credit Rating : " + cpDetails.creditRating, normal));
            document.add(new Paragraph("Issued by : " + cpDetails.ratingIssuedBy, normal));
            document.add(new Paragraph("Date of rating : " + dateOfRatingStr, normal));
            document.add(new Paragraph("Validity : " + validityStr, normal));
            document.add(new Paragraph("Effective date for rating : " + effectiveDateForRatingStr, normal));
            document.add(new Paragraph("For amount : " + cpDetails.currency + " "  + cpDetails.issueValue, normal));
            document.add(new Paragraph("Conditions (if any) : " + cpDetails.conditions, normal));
            document.add(new Paragraph("Credit Support (if any) : " + cpDetails.creditSupport, normal));
            document.add(new Paragraph("Description of instrument : " + cpDetails.desc, normal));
            document.add(new Paragraph("Amount : " + cpDetails.currency + " "  + cpDetails.totalAmount, normal));
            document.add(new Paragraph("Issued by : " + cpDetails.issuerName, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("In favor of : " + invDetails.investorName, smallBold));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Issuing and Paying Agent : " + ipaDetails.ipaName, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Issuer's liability under the CP will continue beyond the due date, in case the CP " +
                    "is not paid on due date, even if the CP in D-MAT form is extinguished on due date", normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Seller of CP : " + cpDetails.issuerName, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Purchaser of CP : " + invDetails.investorName, smallBold));
            document.add(new Paragraph("\n\n"));
            document.newPage();

            document.add(new Paragraph("SETTLEMENT INSTRUCTIONS", subFont));
            document.add(new Paragraph("Value Date : " + valueDateStr, normal));
            document.add(new Paragraph("Please credit to : " + cpDetails.creditTo + " " + cpDetails.currentAccNo + " " + cpDetails.IFSCCode, normal));
            document.add(new Paragraph("Please deliver to : " + invDetails.investorName, smallBold));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("DP Name : " + invDetails.dpName, normal));
            document.add(new Paragraph("Client ID : " + invDetails.clientId, normal));
            document.add(new Paragraph("DP ID : " + invDetails.dpId, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Market conventions : " + cpDetails.marketConventions, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("The deal is done by : ", smallBold));
            document.add(new Paragraph("(ON BEHALF OF SELLER) : " + cpDetails.issuerContactPerson, normal));
            document.add(new Paragraph("(ON BEHALF OF PURCHASER) : " + invDetails.contactPerson, normal));
            document.add(new Paragraph("(ON TRADE DATE) : " + tradeDateStr, normal));
            document.add(new Paragraph("No recourse is available to the purchaser of CP against previous holders of the CP. : ", normal));
            document.add(new Paragraph("This contract note is executed by \n", normal));
            document.add(new Paragraph("ON BEHALF OF : ", smallBold));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph(cpDetails.issuerName, smallBold));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("ON BEHALF OF \n", smallBold));
            document.add(new Paragraph(invDetails.investorName, smallBold));

            document.close();
            pdfFileBase64 = encodeFileToBase64Binary(path);

        }  catch (Exception ex) {
            ex.printStackTrace();
            return ErrorUtils.errorHttpResponse(ex, "Deal Confirmation Document Generation Error");
        }

        return Response.status(Response.Status.OK).entity(pdfFileBase64).build();


    }

    @POST
    @Path("generateIPAVerificationDocuments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateIPAVerificationDocuments(String inputJSON) {

        String pdfFileBase64 = "";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Roles roleObj = gson.fromJson(inputJSON.toString(), Roles.class);
            Roles.CP cpDetails = roleObj.cp;

            Roles.Investor invDetails = roleObj.investor;
            Roles.IPA ipaDetails = roleObj.ipa;
            Roles.NSDL nsdlDetails = roleObj.nsdl;

            final File tempFile = File.createTempFile("IPA", ".pdf");

            tempFile.deleteOnExit();

            String path = tempFile.getPath();

            Font mainFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                    Font.BOLD);
            Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                    Font.BOLD);
            Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                    Font.BOLD);
            Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12);

            Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);


            DateFormat dtf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            DateFormat dtf1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            String todayStr = dtf1.format(new Date());
            //String today = dtf1.format(new Date());

               /* String dueDateStr = dtf.format(cpDetails.matDate);
                String validityStr = dtf.format(cpDetails.ratingValidityDate);
                String dateOfRatingStr = dtf.format(cpDetails.dateOfRating);
                String effectiveDateForRatingStr = dtf.format(cpDetails.effectiveDateOfRating);
                String valueDateStr = dtf.format(cpDetails.valueDate);
                String dateOfContractStr = dtf.format(cpDetails.dateOfContract);
                String tradeDateStr = dtf.format(cpDetails.tradeDate);*/

            String dueDateStr = cpDetails.matDate;
            String validityStr = cpDetails.ratingValidityDate;
            String dateOfRatingStr = cpDetails.dateOfRating;
            String effectiveDateForRatingStr = cpDetails.effectiveDateOfRating;
            String valueDateStr = cpDetails.valueDate;
            String dateOfContractStr = cpDetails.dateOfContract;
            String tradeDateStr = cpDetails.tradeDate;
            // Instantiation of document object
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            // Creation of PdfWriter object
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(path));

            document.open();

            // Creation of paragraph object
            document.add(new Paragraph(todayStr, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(ipaDetails.ipaName, smallBold));
            Paragraph ipaAddress2 = new Paragraph(String.format(ipaDetails.ipaAddress.replaceAll(", ", "\n")), smallFont);
            document.add(ipaAddress2);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Sub: Issuance of Commercial Paper in Dematerialised form.", smallBold));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("We refer to the Issuing and Paying Agency Agreement executed with you in" +
                    " connection with the issue of Commercial Paper in dematerialised form as per the guidelines issued" +
                    " by Reserve Bank of India\nIn connection with the above, we would like to confirm that:\n"));
            document.add(new Paragraph("a) We are eligible to issue commercial Paper as per the norms fixed by " +
                    "Reserve Bank of India.\n"));
            document.add(new Paragraph("b) The amount of " + cpDetails.currency + " "  + cpDetails.maturityValue + " proposed to be raised by us by " +
                    "issue of Commercial Paper including the amounts already raised is within the amounts mentioned" +
                    "by " + cpDetails.ratingIssuedBy + " vide their letter dated " + dateOfRatingStr + " and amounts" +
                    " approved by the Board.\n"));
            document.add(new Paragraph("c) In terms of (i) Net worth (ii) working capital facilities sanctioned by" +
                    " banks/financial institutions\n\n"));
            document.add(new Paragraph("For " + cpDetails.issuerName + "\n\n", smallBold));
            document.add(new Paragraph("Authorised Signatories\n\n", smallBold));


            document.newPage();
            Paragraph title = new Paragraph("ANNEXURE II", subFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph(todayStr, normal));
            document.add(new Paragraph("\nTo\nThe Chief General Manager\nFinancial Markets Department\n" +
                    "Reserve Bank of India (RBI)\nCentral Ofiice\nMumbai – 400 001\n\n", normal));
            document.add(new Paragraph("Through \n", normal));
            document.add(new Paragraph(ipaDetails.ipaName, smallBold));
            Paragraph ipaAddress1 = new Paragraph(String.format(ipaDetails.ipaAddress.replaceAll(", ", "\n")), smallFont);
            document.add(ipaAddress1);
            document.add(new Paragraph("\nDear Sir, \n", normal));
            Paragraph Subject = new Paragraph("Sub : Issue of Commercial Paper", smallBold);
            Subject.setIndentationLeft(20);
            document.add(Subject);
            document.add(new Paragraph("In terms of the Guidelines for issuance of Commercial paper issued " +
                    "by the Reserve Bank of India (RBI) dated August 19,2003 we have issued Commercial Paper as per" +
                    " details furnished hereunder : \n", normal));
            Paragraph list = new Paragraph("i)  Name of issuer :  " + cpDetails.issuerName + "\nii) Registered Office " +
                    "and Address :  " + cpDetails.issuerAddress + "\niii) Business Activity : " + cpDetails.businessActivity +
                    "\niv) Name/s of Stock Exchange/s with whom shares of the issuer are listed (if applicable)\n : "
                    + cpDetails.stockExchange + "\nv) Tangible net worth as per latest audited balance sheet : "
                    + cpDetails.netWorth + "\nvi) Total Working Capital Limit : " + cpDetails.workingCapitalLimit +
                    "\nvii) Outstanding  Bank Borrowings : " + cpDetails.outstandingBankBorrowing + "\nviii) Details of" +
                    " Commercial Paper :", normal);
            list.setIndentationLeft(20);
            document.add(list);
            Paragraph subList = new Paragraph("(a) Issued (Face Value) : " + cpDetails.currency + " "  + cpDetails.faceValue
                    + "\nDate of Issue : " + cpDetails.valueDate + "\nDate of Maturity : " + cpDetails.matDate +
                    "\nAmount : " + cpDetails.currency + " "  + cpDetails.maturityValue + "\nRate : " + cpDetails.discRate
                    + "\nb) Amount of CP outstanding (Face value) Including the present issue (Including" +
                    " Short term Debts) : " + cpDetails.currency + " "  + cpDetails.amountOfCPOutstanding,normal);
            subList.setIndentationLeft(35);
            document.add(subList);
            Paragraph lastPoint = new Paragraph("ix) Rating(s) obtained from the Credit Rating Information Services of India " +
                    "Ltd. (CRISIL) Or any other agency approved by the Reserve Bank of India (RBI) : "
                    + cpDetails.creditRating, normal);
            lastPoint.setIndentationLeft(20);
            document.add(new Paragraph("\nFor and on behalf of ", normal));
            document.add(new Paragraph("\n\n", normal));
            document.add(new Paragraph(cpDetails.issuerName, normal));


            document.newPage();
            Paragraph title1 = new Paragraph("Annexure- VI", subFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            Paragraph subTitle = new Paragraph("ISSUE OF COMMERCIAL PAPER (C P )", subFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            Paragraph subTitle1 = new Paragraph("LETTER OF OFFER", subFont);
            subTitle1.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle1);

            document.add(new Paragraph("PART I", smallBold));
            document.add(new Paragraph("PROPOSED DATE OF ISSUE : " + cpDetails.valueDate, normal));
            // document.add(new Paragraph("TENOR (in days) : "
            //         + dateDiff(cpDetails.valueDate.toString(), cpDetails.maturityDate.toString()), normal));
            document.add(new Paragraph("DUE DATE : " + cpDetails.matDate, normal));
            document.add(new Paragraph("ISSUE REFERENCE : " + cpDetails.issueRef, normal));
            document.add(new Paragraph("ISIN CODE : " + cpDetails.isinCode, normal));
            document.add(new Paragraph("ISSUE SIZE (Maturity Value) : " + cpDetails.currency + " "  + cpDetails.maturityValue, normal));
            document.add(new Paragraph("CREDIT RATING : " + cpDetails.creditRating, normal));
            document.add(new Paragraph("ISSUED BY : " + cpDetails.ratingIssuedBy, normal));
            document.add(new Paragraph("DATE OF RATING : " + cpDetails.effectiveDateOfRating, normal));
            document.add(new Paragraph("VALIDITY : " + cpDetails.ratingValidityDate, normal));
            document.add(new Paragraph("FOR AMOUNT : " + cpDetails.currency + " "  + cpDetails.issueValue, normal));
            document.add(new Paragraph("CONDITIONS (if any) : " + cpDetails.conditions, normal));
            document.add(new Paragraph("CREDIT SUPPORT  (if any) : " + cpDetails.creditSupport, normal));
            document.add(new Paragraph("DESCRIPTION OF INSTRUMENT : " + cpDetails.desc, normal));
            document.add(new Paragraph("AMOUNT : " + cpDetails.currency + " "  + cpDetails.totalAmount, normal));
            document.add(new Paragraph("ISSUED BY : " + cpDetails.issuerName, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("IN FAVOUR OF : " + invDetails.investorName, smallBold));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("ISSUER OF CP / HOLDERS OF CP : " + cpDetails.issuerName, normal));
            document.add(new Paragraph("Issuing and Paying Agent : " + ipaDetails.ipaName, normal));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("MARKET CONVENTIONS : " + cpDetails.marketConventions, normal));
            document.add(new Paragraph("SUPPORTING BOARD RESOLUTION : " + cpDetails.boardResolutionDate, normal));
            document.add(new Paragraph("TOTAL CP OUTSTANDING (as on date) (Including Short term Debts)" +
                    " : " + cpDetails.currency + " "  + cpDetails.amountOfCPOutstanding, normal));
            document.newPage();
            document.add(new Paragraph(todayStr));
            Paragraph subTitle2 = new Paragraph("TO WHOMSOEVER IT MAY CONCERN\n\n", subFont);
            subTitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle2);
            document.add(new Paragraph("This is to inform you that Company is eligible to raise funds through " +
                    "Commercial Papers as per " + cpDetails.marketConventions + " & RBI guidelines.\n\nWe have working capital requirement sanctioned by Barclays Bank Plc. We hereby\n" +
                    "confirm that the above working capital with Barclays Bank PLC is a standard asset.\n\nThanking you,", normal));
            document.add(new Paragraph("For and on behalf of\n\n" + cpDetails.issuerName + "\n" + "Authorised Signatories\n\n", normal));
            document.newPage();
            document.add(new Paragraph(todayStr));
            document.add(new Paragraph("\n\nTo\n",normal));
            Paragraph ipaName = new Paragraph(ipaDetails.ipaName, smallBold);
            document.add(ipaName);
            Paragraph ipaAddress = new Paragraph(String.format(ipaDetails.ipaAddress.replaceAll(", ", "\n")), smallFont);
            document.add(ipaAddress);
            document.add(new Paragraph("\nWith reference to our proposed Issue of Commercial Paper dated "
                    + cpDetails.tradeDate + " to the extent of " + cpDetails.currency + " " + cpDetails.issueValue + ", " +
                    "we declare that the amount proposed to be raised is within the ceiling mentioned by the credit " +
                    "rating agency or as approved by the Board whichever is lower.\n\n", normal));
            document.add(new Paragraph("We also confirm that the above issuance along with the earlier short term debts" +
                    " that are issued, subscribed and outstanding as on date is " + cpDetails.currency + " " + cpDetails.amountOfCPOutstanding
                    + "does not exceed the Commercial Paper/Short term debt program size mentioned in the " + cpDetails.ratingIssuedBy +
                    " rating letter dated " + cpDetails.dateOfRating + ".\n\nWith reference to our letter dated " + todayStr +
                    ",  we confirm that our eligibility to issue Commercial Paper as per the below norms fixed by RBI " +
                    "& " + cpDetails.marketConventions + " guidelines continues:\n\n",normal));
            Paragraph points = new Paragraph("1.	Net worth\n2.	Working capital facilities sanctioned by banks" +
                    "/financial institutions\n3.	Classification of our liabilities with the financing banks and " +
                    "institutions as Standard Asset\n\n", normal);
            points.setIndentationLeft(20);
            document.add(points);
            document.add(new Paragraph("Thanking you,\nFor and on behalf of\n\n" + cpDetails.issuerName + "\n" + "Authorised Signatories\n\n", normal));
            document.newPage();



            document.add(new Paragraph(todayStr));
            document.add(new Paragraph("\n\nTo\n",normal));
            Paragraph nextIpaName = new Paragraph(ipaDetails.ipaName, smallBold);
            document.add(nextIpaName);
            Paragraph nextIpaAddress = new Paragraph(String.format(ipaDetails.ipaAddress.replaceAll(", ", "\n")), smallFont);
            document.add(nextIpaAddress);
            document.add(new Paragraph("\nWith reference to our proposed Issue of Commercial Paper dated "
                    + cpDetails.tradeDate + " to the extent of " + cpDetails.currency + " " + cpDetails.issueValue + "(Issue):\n\n", normal));
            document.add(new Paragraph("1.	We confirm that currently" + ipaDetails.ipaName + " is the only Issuing and " +
                    "Payment Agent appointed for the company for commercial paper issuances.\n\n2.	Also  " +
                    cpDetails.ratingIssuedBy + " rating letter dated " + cpDetails.dateOfRating + " is the only rating " +
                    "certificate letter obtained from among all the rating agencies authorized to assign rating for " +
                    "Commercial Paper Issuances.\n\n",normal));
            document.add(new Paragraph("Thanking you,\nFor and on behalf of\n\n" + cpDetails.issuerName + "\n" + "Authorised Signatories\n\n", normal));
            document.newPage();
            document.add(new Paragraph(todayStr));
            document.add(new Paragraph("\n\nTo\n",normal));
            Paragraph IpaName1 = new Paragraph(ipaDetails.ipaName, smallBold);
            document.add(IpaName1);
            Paragraph IpaAddress1 = new Paragraph(String.format(ipaDetails.ipaAddress.replaceAll(", ", "\n")), smallFont);
            document.add(IpaAddress1);
            document.add(new Paragraph("\nWith reference to our proposed Issue of Commercial Paper dated "
                    + cpDetails.tradeDate + " to the extent of " + cpDetails.currency + " " + cpDetails.issueValue + "(Issue)," +
                    " we declare that the amount proposed to be raised is within the ceiling amount of " + cpDetails.currency
                    + " " + cpDetails.issueValue + " specified by the Rating Agency " + cpDetails.ratingIssuedBy + " Ltd in their letter dated " +
                    "" + cpDetails.dateOfRating + " and the amount specified by the Board of Directors, in their " +
                    "resolution authorizing the issue of Commercial Paper, dated " + cpDetails.boardResolutionDate + ".\n\n", normal));
            document.add(new Paragraph("We also confirm that the above issuance along with the earlier short term debts " +
                    "that are issued, subscribed and outstanding as on date is " +  cpDetails.currency + " " + cpDetails.amountOfCPOutstanding
                    + " and does not exceed the " +
                    "Commercial Paper/Short term debt program size mentioned in the Rating Letter.\n\nWith reference to our letter dated " +
                    todayStr + ", we confirm that we are eligible to issue Commercial Paper and are in full compliance" +
                    " with the requirements stipulated under the circulars, guidelines and norms as issued from time to" +
                    " time and laid down by Reserve Bank of India (“RBI”) and the operating procedures and guidelines " +
                    "issued from time to time by " + cpDetails.marketConventions + ", including but not limited to the following : \n\n", normal));
            document.add(new Paragraph("1.\tNet worth: The tangible net worth of the Company as per the latest" +
                    " audited balance sheet is " + cpDetails.currency + " " +  cpDetails.netWorth
                    + " which is greater than the minimum requirement stipulated for by the RBI, in the extant guidelines " +
                    "issued by it for the issuance of Commercial Paper (“CP Guidelines”);\n2.\tThe Company has been " +
                    "sanctioned a working capital facility limits by banks and/or financial institutions;\n" +
                    "3.\tThe Borrowal Account of the Company has been classified as a “Standard Asset” by Barclays Bank " +
                    "PLC  which is the ‘financing bank or institution’ for the Company;\n" +
                    "4.\tThe Commercial Paper which is sought to be issued by the Company shall be issued a stand-alone " +
                    "product and not combined with any of the other products by the Company.\n" +
                    "5.\tThe Commercial Paper proposed to be issued shall mature within the validity period of the credit" +
                    " rating; \n6.\tFunds being raised through the issuance of Commercial Paper are within the umbrella " +
                    "limit prescribed under the extant RBI norms on resource raising by financial institutions, where applicable. \n" +
                    "7.\tThe issuance of the Commercial Paper shall be made only to eligible investors as construed " +
                    "under the CP Guidelines and shall be issued in strict compliance with the terms and conditions of " +
                    "the CP Guidelines.\n" +
                    "8.\tThe Commercial Paper which is sought to be issued by the Company will be issued at a discount " +
                    "and is being issued by way of private placement only.\n" +
                    "\n" +
                    "9.\tThe Commercial Paper which is sought to be issued by the Company shall be issued in denominations " +
                    "of " + cpDetails.currency + " " + cpDetails.faceValue + ", and the minimum amount invested by each " +
                    "investor in the issue of the Commercial Paper shall be " + cpDetails.currency + " " + cpDetails.faceValue
                    + " each.\n" +
                    "10.\tThe Company does not seek to have the issue of the Commercial Paper underwritten or co-accepted " +
                    "nor does the Company intend for the Commercial Papers to have a Put Option or Call Option attached to them.\n" +
                    "\n" +
                    "11.\tThe Commercial Paper which is sought to be issued by the Company has been given a rating of "
                    + cpDetails.creditRating + " by " + cpDetails.ratingIssuedBy + " via the Rating Letter, which " +
                    "rating is above the minimum rating prescribed by the RBI in the CP Guidelines.\n", normal));

            document.add(new Paragraph("\nThanking you,\nFor and on behalf of\n\n" + cpDetails.issuerName
                    + "\n" + "Authorised Signatories\n\n", normal));
            document.close();

            pdfFileBase64 = encodeFileToBase64Binary(path);

        }  catch (Exception ex) {
            ex.printStackTrace();
            return ErrorUtils.errorHttpResponse(ex, "IPA Verification Document Generation Error");
        }

        return Response.status(Response.Status.OK).entity(pdfFileBase64).build();
    }

    @POST
    @Path("generateCAFDocument")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateCAFDocument(String inputJSON) {

        String pdfFileBase64 = "";
        try {



            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Roles roleObj = gson.fromJson(inputJSON.toString(), Roles.class);
            Roles.CP cpDetails = roleObj.cp;
            Roles.Investor invDetails = roleObj.investor;
            Roles.IPA ipaDetails = roleObj.ipa;
            Roles.NSDL nsdlDetails = roleObj.nsdl;

            final File tempFile = File.createTempFile("CAF", ".pdf");
            tempFile.deleteOnExit();

            String path = tempFile.getPath();

            String tempMatValue = cpDetails.maturityValue.replaceAll("[^0-9]", "");
            String tempFaceValue = cpDetails.faceValue.replaceAll("[^0-9]", "");

            Integer units = 0;

            if (tempFaceValue.length() != 0 || tempMatValue.length() != 0) {

                units = Integer.parseInt(tempMatValue) / Integer.parseInt(tempFaceValue);
            }
            Font mainFont = new Font(Font.FontFamily.TIMES_ROMAN, 18.0F, 1);
            new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1);
            Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 1);
            Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F);
            Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10.0F);
            SimpleDateFormat dtf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            //String allotmentDateStr = dtf.format(cpDetails.allotDate);
            String allotmentDateStr = cpDetails.allotDate;
            DateFormat dtf1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            String todayStr = dtf1.format(new Date());


            Document document = new Document(PageSize.A4, 50.0F, 50.0F, 50.0F, 50.0F);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Paragraph issuerPara = new Paragraph(cpDetails.issuerName + "\n"
                    + String.format(cpDetails.issuerAddress.replaceAll(", ", "\n")) + "\nT: " + cpDetails.officialContactNum
                    + "\nF: " + cpDetails.officialFaxNum + "\n" + cpDetails.officialEmail + "\n" +
                    cpDetails.officialWebsite + "\nDate: " + todayStr + "\nCIN: " + cpDetails.CIN, smallFont);
            issuerPara.setIndentationLeft(300);
            document.add(issuerPara);
            document.add(new Paragraph("To,\n" + nsdlDetails.nsdlContactPerson + "\n" + String.format(nsdlDetails.nsdlAddress.replaceAll(", ", "\n") + "\n"), normal));
            document.add(new Paragraph("We wish to issue Commercial Papers (CP) of our company in the demat mode." +
                    " The details of the CP are as follows: \n", normal));


            PdfPTable t = new PdfPTable(2);


            t.addCell(new Phrase("ISIN", smallFont));
            t.addCell(new Phrase(cpDetails.isinCode, smallBold));
            t.addCell(new Phrase("Date of Allotment ", smallFont));
            t.addCell(new Phrase(allotmentDateStr, smallFont));
            t.addCell(new Phrase("Face Value", smallFont));
            t.addCell(new Phrase(cpDetails.currency + " " + cpDetails.faceValue, smallFont));
            t.addCell(new Phrase("No. of Units to be credited", smallFont));
            t.addCell(new Phrase(units.toString(), smallFont));
            t.addCell(new Phrase("Name of the IPA", smallFont));
            t.addCell(new Phrase(ipaDetails.ipaName, smallFont));
            t.addCell(new Phrase("IPA CP Allotment Account - DP ID", smallFont));
            t.addCell(new Phrase(ipaDetails.ipaAllotmentDPID, smallFont));
            t.addCell(new Phrase("IPA CP Allotment Account - Client ID", smallFont));
            t.addCell(new Phrase(ipaDetails.ipaAllotmentClientID, smallFont));
            t.addCell(new Phrase("IPA CP Redemption Account - DP ID", smallFont));
            t.addCell(new Phrase(ipaDetails.ipaRedemptionDPID, smallFont));
            t.addCell(new Phrase("IPA CP Redemption Account - Client ID", smallFont));
            t.addCell(new Phrase(ipaDetails.ipaRedemptionClientID, smallFont));

            document.add(t);
            document.add(new Paragraph("We request you to credit the above mentioned securities to the CP Allotment" +
                    " account of the IPA.", normal));
            document.add(new Paragraph("Yours faithfully,", normal));
            document.add(new Paragraph("Name of the Authorized Signatory", normal));
            document.close();
            pdfFileBase64 = encodeFileToBase64Binary(path);

        }  catch (Exception ex) {
            ex.printStackTrace();
            return ErrorUtils.errorHttpResponse(ex, "Corporate Action Form Document Generation Error");
        }

        return Response.status(Response.Status.OK).entity(pdfFileBase64).build();
    }



    @GET
    @Path("getDocs/{docHash}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDocs(@PathParam("docHash") String docHash)
    {
        return DLAttachmentUtils.getInstance().downloadAttachment(docHash);
    }

    @GET
    @Path("getDoc/{docHash}/{docSubType}/{docExtension}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDoc(@PathParam("docHash") String docHash,
                           @PathParam("docSubType") String docSubType,
                           @PathParam("docExtension") String docExtension)
    {
        return DLAttachmentUtils.getInstance().downloadAttachment(docHash + "/" + docSubType + "." + docExtension);
    }

    public static String encodeFileToBase64Binary(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.US_ASCII);
    }

    @POST
    @Path("signDoc/{docType}/{currentUser}/{role}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response signDoc(@FormDataParam("file") InputStream uploadedInputStream, @PathParam("docType") String docType, @PathParam("currentUser") String currentUser, @PathParam("role") String role  )
    {

        Signature sign = new Signature();

         String outputB64Str = sign.initiateSignatureWorkflow(uploadedInputStream, docType, currentUser, role);
        return Response.ok(outputB64Str, MediaType.TEXT_PLAIN)
                .build();
    }

   /* @POST
    @Path("signDocFlow/{docName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response signDocFlow(@FormDataParam("file") InputStream uploadedInputStream, @PathParam("docType") String docType)
    {
        Signature sign = new Signature();
        String outputB64Str = sign.inputStreamSign(uploadedInputStream, docType);
        return Response.ok(outputB64Str, MediaType.TEXT_PLAIN)
                .build();

    }*/


    @POST
    @Path("verifyDocSignature")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifyDocSignature(@FormDataParam("file") InputStream uploadedInputStream)
    {
        String output = "";
        VerifySignature verify = new VerifySignature();
        output = verify.verify(verify.convertIStoB64(uploadedInputStream));

        return Response.ok(output, MediaType.TEXT_PLAIN)
                .build();

    }

    @POST
    @Path("verifyDocSignatureJSON")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyDocTest(@FormDataParam("file") InputStream uploadedInputStream)
    {
//        String output = "";
//        VerifySignature verify = new VerifySignature();
//        output = verify.verify(verify.convertIStoB64(uploadedInputStream));
//        VerificationResult vr = verify.readXML(output);
//
//        return Response.ok(vr, MediaType.APPLICATION_JSON)
//                .build();
        return null;
    }

    @POST
    @Path("verifyDocSignatureAndOrg/{docType}/{org}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifyDocSignAndOrg(@FormDataParam("file") InputStream uploadedInputStream,@PathParam("docType")String docType, @PathParam("org")String organization)
    {
        Boolean output;
        VerifySignature verify = new VerifySignature();
        output = verify.verifyWithOrg(uploadedInputStream,docType,organization);

        return Response.ok(output, MediaType.TEXT_PLAIN)
                .build();

    }

    /// To get number of days between two dates
    ///
    ///
    private static String dateDiff(String startDate, String endDate) throws Exception
    {
        String dayDifference = "";
        try {

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");

            //Setting dates
            date1 = dates.parse(startDate);
            date2 = dates.parse(endDate);

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            dayDifference = Long.toString(differenceDates);



        } catch (Exception exception) {

            exception.printStackTrace();
        }
        return dayDifference;
    }

//    @POST
//    @Path("uploadDoc")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response uploadDoc(InputStream uploadedInputStream){
//
//        Logger logger = Logger.getLogger(IndiaCPProgramApi.class.getName());
//        Client jerseyClient = ClientBuilder.newClient();
//        //Client jerseyClient = ClientBuilder.newClient(new ClientConfig().register(org.glassfish.jersey.jsonp.internal.JsonProcessingAutoDiscoverable.class));
//        //Feature feature = new LoggingFeature(logger, Level.INFO, null, null);
//        //jerseyClient.register(feature);
//        WebTarget dlRestEndPoint = jerseyClient.target(DLConfig.DLConfigInstance().getDLRestEndpoint());
//
//        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("myfile", uploadedInputStream, "myfile", MediaType.APPLICATION_OCTET_STREAM_TYPE);
//
//        final Response attachmentResponse = dlRestEndPoint.path(DLConfig.DLConfigInstance().getDLUploadAttachmentPath())
//                .request()
//                .post(Entity.entity(streamDataBodyPart, MediaType.MULTIPART_FORM_DATA));
//
//        String docHash = attachmentResponse.getEntity().toString(); //TODO: extract hash from attachmentResponse
//        System.out.println("file uploaded to DL");
//        return Response.status(Response.Status.OK).build();
//
//    }
}