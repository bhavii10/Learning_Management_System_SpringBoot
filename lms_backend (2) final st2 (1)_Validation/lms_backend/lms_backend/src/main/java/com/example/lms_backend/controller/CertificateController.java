//package com.example.lms_backend.controller;
//
//import com.example.lms_backend.dto.CertificateRequest;
//import com.example.lms_backend.service.CertificateService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/certificates")
//@RequiredArgsConstructor
//public class CertificateController {
//
//    private final CertificateService certificateService;
//
//    @PostMapping("/generate")
//    public ResponseEntity<byte[]> generateCertificate(@RequestBody CertificateRequest request) {
//        byte[] pdf = certificateService.generateCertificate(
//                request.getUsername(),
//                request.getCourseTitle()
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment",
//                request.getCourseTitle() + "_certificate.pdf");
//
//        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
//    }
//}
package com.example.lms_backend.controller;

import com.example.lms_backend.dto.CertificateRequest;
import com.example.lms_backend.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling certificate generation requests.
 */
@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    /**
     * Generate a course completion certificate as a PDF.
     *
     * @param request contains the username and course title
     * @return PDF file as a byte array with download headers
     */
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateCertificate(@RequestBody CertificateRequest request) {
        // Generate the certificate PDF
        byte[] pdf = certificateService.generateCertificate(
                request.getUsername(),
                request.getCourseTitle()
        );

        // Set response headers for PDF download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                request.getCourseTitle() + "_certificate.pdf"
        );

        // Return the response with PDF and headers
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
