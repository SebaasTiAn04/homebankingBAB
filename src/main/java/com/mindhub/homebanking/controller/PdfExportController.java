package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.TransactionDateDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.service.implement.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PdfExportController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @PostMapping("/api/pdf/generate")
    public void generatePDF(HttpServletResponse response, Authentication authentication, @RequestBody TransactionDateDTO transactionDateDTO) throws IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        response.setHeader("Content-Disposition", "attachment; filename=pdf_.pdf");
        this.pdfGeneratorService.export(response, authentication, transactionDateDTO.getNumber(),
                                transactionDateDTO.getYearStart(), transactionDateDTO.getMonthStart(), transactionDateDTO.getDayStart(),
                                transactionDateDTO.getYearEnd(), transactionDateDTO.getMonthEnd(), transactionDateDTO.getDayEnd());
    }
}
