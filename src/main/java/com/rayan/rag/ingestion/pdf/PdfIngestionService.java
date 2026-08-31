package com.rayan.rag.ingestion.pdf;

import com.rayan.rag.ingestion.model.IngestDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PdfIngestionService {

    private static final String PDF_DIR = "data/pdfs";

    public List<IngestDocument> ingestPdfs() throws Exception {
        File[] pdfFiles = new File(PDF_DIR).listFiles();

        List<IngestDocument> docs = new ArrayList<>();

        for (File pdfFile : pdfFiles) {
            docs.add(ingestSinglePdf(pdfFile));
        }

        return docs;
    }

    private IngestDocument ingestSinglePdf(File pdfFile) throws Exception {
        log.info("Ingesting PDF: {}", pdfFile.getName());

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            log.info("------ Extracted Text ({}) ------", pdfFile.getName());
            log.info(text);

            return new IngestDocument(
                    "PDF",
                    text,
                    Map.of("filename", pdfFile.getName())
            );
        }
    }
}
