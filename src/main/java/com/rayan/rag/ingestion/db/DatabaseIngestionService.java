package com.rayan.rag.ingestion.db;

import com.rayan.rag.ingestion.model.IngestDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatabaseIngestionService {


    private final JdbcTemplate jdbcTemplate;


    public List<IngestDocument> ingestDatabaseContent() {
        List<IngestDocument> docs = new ArrayList<>();
        docs.addAll(ingestFaqs());
        docs.addAll(ingestReleaseNotes());
        docs.addAll(ingestAnnouncements());

        return docs;
    }

    public List<IngestDocument> ingestFaqs() {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList("SELECT id, question, answer, department, visibility FROM faqs");

        List<IngestDocument> docs = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            log.info("-------- FAQ --------");
            log.info("Question: {}", row.get("question"));
            log.info("Answer: {}", row.get("answer"));

            String content = "Question: " + row.get("question") + "\n" +
                    " Answer: " + row.get("answer");
            docs.add(new IngestDocument(
                    "DB",
                    content,
                    Map.of("table", "faqs",
                            "id", row.get("id"),
                            "department", row.get("department"),
                            "visibility", row.get("visibility")
                    )
            ));
        }
        return docs;
    }

    public List<IngestDocument> ingestReleaseNotes() {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList("SELECT id, version, summary, details, release_date FROM release_notes");

        List<IngestDocument> docs = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            log.info("-------- Release Notes --------");
            log.info("Version: {}", row.get("version"));
            log.info("c: {}", row.get("c"));

            String content = "Version: " + row.get("version") + "\n" +
                    " version: " + row.get("version") + "Details" + row.get("details");

            docs.add(new IngestDocument(
                    "DB",
                    content,
                    Map.of("table", "Release Notes",
                            "id", row.get("id"),
                            "version", row.get("version"),
                            "releaseDate", row.get("release_date")
                    )
            ));
        }
        return docs;
    }

    public List<IngestDocument> ingestAnnouncements() {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList("SELECT id, subject, body, category, effective_from, effective_to, source_type FROM announcements");

        List<IngestDocument> docs = new ArrayList<>();


        for (Map<String, Object> row : rows) {
            log.info("-------- Announcements --------");
            log.info("Title: {}", row.get("subject"));

            String content = "Subject: " + row.get("subject") + "\n" + row.get("body");

            docs.add(new IngestDocument(
                    "DB",
                    content,
                    Map.of("table", "Announcements",
                            "id", row.get("id"),
                            "category", row.get("category"),
                            "effectiveFrom", row.get("effective_from"),
                            "effectiveTo", row.get("effective_to") != null ? row.get("effective_to") : "",
                            "sourceType", row.get("source_type")
                    )
            ));
        }
        return docs;
    }
}
