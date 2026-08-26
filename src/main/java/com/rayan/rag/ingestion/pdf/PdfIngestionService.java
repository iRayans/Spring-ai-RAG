package com.rayan.rag.ingestion.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class PdfIngestionService {

    private static final String PDF_DIR = "data/pdfs";

    public void ingestPdfs() throws Exception {
        File[] pdfFiles = new File(PDF_DIR).listFiles();

        for (File pdfFile : pdfFiles) {
            ingestSinglePdf(pdfFile);
        }
    }

    private void ingestSinglePdf(File pdfFile) throws Exception {
        log.info("Ingesting PDF: {}", pdfFile.getName());

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            log.info("------ Extracted Text ({}) ------", pdfFile.getName());
            log.info(text);
        }
    }
}
