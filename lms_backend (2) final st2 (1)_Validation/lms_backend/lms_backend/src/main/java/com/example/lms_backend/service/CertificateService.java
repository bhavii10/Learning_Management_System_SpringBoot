//package com.example.lms_backend.service;
//
//import com.example.lms_backend.dto.CertificateRequest;
//import com.example.lms_backend.model.Certificate;
//import com.example.lms_backend.repository.CertificateRepository;
//import lombok.RequiredArgsConstructor;
//import org.apache.pdfbox.pdmodel.*;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class CertificateService {
//
//    private final CertificateRepository certificateRepository;
//
//    public byte[] generateCertificate(String username, String courseTitle) {
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            PDPageContentStream contentStream = new PDPageContentStream(document, page);
//
//            // Title
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 26);
//            contentStream.newLineAtOffset(150, 700);
//            contentStream.showText("Certificate of Completion");
//            contentStream.endText();
//
//            // User Name
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
//            contentStream.newLineAtOffset(100, 600);
//            contentStream.showText("This is to certify that: " + username);
//            contentStream.endText();
//
//            // Course Title
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA, 18);
//            contentStream.newLineAtOffset(100, 550);
//            contentStream.showText("Has successfully completed the course: " + courseTitle);
//            contentStream.endText();
//
//            // Date
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 16);
//            contentStream.newLineAtOffset(100, 500);
//            contentStream.showText("Issued on: " + LocalDateTime.now());
//            contentStream.endText();
//
//            contentStream.close();
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            document.save(baos);
//
//            byte[] pdfBytes = baos.toByteArray();
//
//            // Save in DB
//            Certificate certificate = Certificate.builder()
//                    .username(username)
//                    .courseTitle(courseTitle)
//                    .issuedAt(LocalDateTime.now())
//                    .pdfData(pdfBytes)
//                    .build();
//
//            certificateRepository.save(certificate);
//
//            return pdfBytes;
//        } catch (IOException e) {
//            throw new RuntimeException("Error while generating certificate PDF", e);
//        }
//    }
//}
package com.example.lms_backend.service;

import com.example.lms_backend.model.Certificate;
import com.example.lms_backend.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;


public byte[] generateCertificate(String username, String courseTitle) {
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        // === Border (single elegant black border) ===
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(3);
        contentStream.addRect(40, 40, pageWidth - 80, pageHeight - 80);
        contentStream.stroke();

        // === Logo (optional, centered at top) ===
        try {
            PDImageXObject logo = PDImageXObject.createFromFile("src/main/resources/logo.png", document);
            float logoWidth = 100;
            float logoHeight = 100;
            contentStream.drawImage(logo, (pageWidth - logoWidth) / 2, pageHeight - 150, logoWidth, logoHeight);
        } catch (Exception e) {
            System.out.println("Logo not found, skipping...");
        }

        // === Title ===
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 32);
        contentStream.setNonStrokingColor(Color.BLACK);
        float titleWidth = (PDType1Font.TIMES_BOLD.getStringWidth("Certificate of Completion") / 1000) * 32;
        contentStream.newLineAtOffset((pageWidth - titleWidth) / 2, pageHeight - 220);
        contentStream.showText("Certificate of Completion");
        contentStream.endText();

        // === Subtitle ===
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 16);
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        String subtitle = "This is to certify that";
        float subWidth = (PDType1Font.HELVETICA_OBLIQUE.getStringWidth(subtitle) / 1000) * 16;
        contentStream.newLineAtOffset((pageWidth - subWidth) / 2, pageHeight - 270);
        contentStream.showText(subtitle);
        contentStream.endText();

        // === User’s Name (big + bold, centered) ===
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 26);
        contentStream.setNonStrokingColor(new Color(25, 25, 112)); // navy
        float nameWidth = (PDType1Font.TIMES_BOLD_ITALIC.getStringWidth(username) / 1000) * 26;
        contentStream.newLineAtOffset((pageWidth - nameWidth) / 2, pageHeight - 320);
        contentStream.showText(username);
        contentStream.endText();

        // === Completion Line ===
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 16);
        contentStream.setNonStrokingColor(Color.BLACK);
        String line = "has successfully completed the course";
        float lineWidth = (PDType1Font.HELVETICA.getStringWidth(line) / 1000) * 16;
        contentStream.newLineAtOffset((pageWidth - lineWidth) / 2, pageHeight - 360);
        contentStream.showText(line);
        contentStream.endText();

        // === Course Title ===
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 22);
        contentStream.setNonStrokingColor(new Color(128, 0, 128)); // purple
        float courseWidth = (PDType1Font.TIMES_BOLD.getStringWidth(courseTitle) / 1000) * 22;
        contentStream.newLineAtOffset((pageWidth - courseWidth) / 2, pageHeight - 400);
        contentStream.showText(courseTitle);
        contentStream.endText();

        // === Footer ===
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
        contentStream.setNonStrokingColor(Color.GRAY);
        String footer = "Issued on: " + LocalDateTime.now().toLocalDate();
        float footerWidth = (PDType1Font.HELVETICA_OBLIQUE.getStringWidth(footer) / 1000) * 12;
        contentStream.newLineAtOffset((pageWidth - footerWidth) / 2, 60);
        contentStream.showText(footer);
        contentStream.endText();

        contentStream.close();

        // Save PDF into byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);

        byte[] pdfBytes = baos.toByteArray();

        // Save in DB
        Certificate certificate = Certificate.builder()
                .username(username)
                .courseTitle(courseTitle)
                .issuedAt(LocalDateTime.now())
                .pdfData(pdfBytes)
                .build();

        certificateRepository.save(certificate);

        return pdfBytes;
    } catch (IOException e) {
        throw new RuntimeException("Error while generating certificate PDF", e);
    }
}

}
