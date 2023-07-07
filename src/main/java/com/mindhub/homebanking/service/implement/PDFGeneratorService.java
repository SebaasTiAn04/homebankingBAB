package com.mindhub.homebanking.service.implement;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

@Service
public class PDFGeneratorService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    public void export(HttpServletResponse response, Authentication authentication, String number, int yearStart, int monthStart, int dayStart, int yearEnd, int monthEnd, int dayEnd) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        AccountDTO accountDTO = new AccountDTO(accountService.findByNumber(number));
        Client client =  clientService.findByEmail(authentication.getName());
        LocalDateTime start = null;
        LocalDateTime end = null;

        if(yearStart == 0 && monthStart == 0 && dayStart == 0 && yearEnd == 0 && monthEnd == 0 && dayEnd == 0){

            start = LocalDateTime.of(1999, Month.JANUARY, 01,0,0,0,0);
            end = LocalDateTime.now();
        }else if(yearEnd == 0 && monthEnd == 0 && dayEnd == 0){

            start = LocalDateTime.of(yearStart, Month.of(monthStart), dayStart, 0,0,0,0);
            end = LocalDateTime.now();
        }else if(yearStart == 0 && monthStart == 0 && dayStart == 0){

            start = LocalDateTime.of(1999, Month.JANUARY, 01,0,0,0,0);
            end = LocalDateTime.of(yearEnd, Month.of(monthEnd), dayEnd, 23,59,59,0);
        }else {

            start = LocalDateTime.of(yearStart, Month.of(monthStart), dayStart, 0,0,0,0);
            end = LocalDateTime.of(yearEnd, Month.of(monthEnd), dayEnd, 23,59,59,0);
        }

        List<Transaction> transactions = transactionService.findByDateBetween(accountDTO.getId(), start, end);
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Buenos Aires Bank: ", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont((FontFactory.HELVETICA));
        fontParagraph.setSize(12);

        Paragraph paragraph1 = new Paragraph( "Account holder: " +client.getFirstName() + " " + client.getLastName(), fontParagraph);
        Paragraph paragraph2 = new Paragraph( "Account number: " + accountDTO.getNumber(), fontParagraph);
        Paragraph paragraph3 = new Paragraph( "Account type: " + accountDTO.getType(), fontParagraph);
        Paragraph paragraph4 = new Paragraph( "Balance: " + accountDTO.getBalance(), fontParagraph);
        Paragraph paragraph5 = new Paragraph( "Creation Date: " + accountDTO.getCreationDate(), fontParagraph);
        Paragraph paragraph6 = new Paragraph( "transactions: ", fontParagraph);
        Paragraph paragraph7 = new Paragraph( " " , fontParagraph);

        PdfPTable tableTransactions = new PdfPTable(5);

        transactions.forEach(t -> {
            tableTransactions.addCell(t.getType().toString());
            tableTransactions.addCell(String.valueOf(t.getAmount()));
            tableTransactions.addCell(t.getDescription());
            tableTransactions.addCell(String.valueOf(t.getAccountAmount()));
            tableTransactions.addCell(String.valueOf(t.getDate()));
        });

        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.add(paragraph6);
        document.add(paragraph7);
        document.add(tableTransactions);
        document.close();
    }
}
