package gsu.by.generator;

import com.deepoove.poi.XWPFTemplate;
import gsu.by.GeneratorExecutor;
import gsu.by.config.YamlConfig;
import gsu.by.merger.DocumentMerger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import static gsu.by.constants.CommonConstants.*;
import static gsu.by.constants.ExceptionConstants.*;

public class TicketsGenerator {

    private final YamlConfig config;

    private final DocumentMerger documentMerger;

    public TicketsGenerator(String pathToConfig) {
        config = YamlConfig.loadConfig(pathToConfig);
        documentMerger = new DocumentMerger(config.getApiKey());
    }

    public void generateTicketsAsSingleDocument() throws Exception {
        List<String> questions = readQuestionsFromDocument(config.getQuestionsFilePath());

        int requiredTicketCount = config.getNumberOfTickets();

        validateTicketCount(requiredTicketCount, questions.size());

        List<String> ticketFileNames = generateAllTickets(questions, requiredTicketCount);

        File[] tickets = new File[ticketFileNames.size()];
        for (int i = 0; i < ticketFileNames.size(); i++) {
            tickets[i] = new File(ticketFileNames.get(i));
        }

        mergeDocuments(tickets);
        cleanUpFiles(tickets);
        System.out.println(TICKETS_SUCCESSFULLY_GENERATED_MESSAGE);
    }

    private List<String> generateAllTickets(List<String> questions, int requiredTicketCount) throws IOException {
        int totalQuestions = questions.size();

        List<String> firstHalf = questions.subList(0, totalQuestions / 2);
        List<String> secondHalf = questions.subList(totalQuestions / 2, totalQuestions);

        List<String> ticketFileNames = new ArrayList<>();
        Set<String> usedTicketPairs = new HashSet<>();

        Random rand = new Random();

        for (int i = 0; i < requiredTicketCount; i++) {
            Map<String, Object> context = new HashMap<>();
            context.put(TICKET_NUMBER_PLACEHOLDER, i + 1);

            String q1, q2;

            if (i < firstHalf.size() && i < secondHalf.size()) {
                q1 = firstHalf.get(i);
                q2 = secondHalf.get(i);
            } else {
                int maxAttempts = 100;
                int attempt = 0;
                do {
                    q1 = firstHalf.get(rand.nextInt(firstHalf.size()));
                    q2 = secondHalf.get(rand.nextInt(secondHalf.size()));
                    attempt++;
                    if (attempt > maxAttempts) {
                        throw new RuntimeException(FAILED_FINDING_UNIQUE_QUESTION_PAIR_EXCEPTION_MESSAGE);
                    }
                } while (usedTicketPairs.contains(QUESTION_PAIR_TEMPLATE.formatted(q1, q2)));
            }

            usedTicketPairs.add(QUESTION_PAIR_TEMPLATE.formatted(q1, q2));

            context.put(QUESTION1_PLACEHOLDER, q1);
            context.put(QUESTION2_PLACEHOLDER, q2);


            String ticketFileName = TICKET_DOCX_FILE_NAME_TEMPLATE.formatted(i + 1);

            generateTicket(context, ticketFileName);
            ticketFileNames.add(ticketFileName);
        }

        return ticketFileNames;
    }

    private void mergeDocuments(File[] files) throws IOException {
        List<File> fileList = new ArrayList<>(Arrays.asList(files));

        while (fileList.size() > 1) {
            List<File> newFiles = new ArrayList<>();

            int i = 0;
            while (i < fileList.size()) {
                int batchSize;
                if (fileList.size() - i >= MAX_FILES_TO_MERGE_PER_CALL) {
                    batchSize = MAX_FILES_TO_MERGE_PER_CALL;
                } else if (fileList.size() - i > MIN_FILES_TO_MERGE_PER_CALL) {
                    batchSize = MIN_FILES_TO_MERGE_PER_CALL;
                } else {
                    batchSize = MIN_FILES_TO_MERGE_PER_CALL;
                }

                File[] currentBatch = new File[batchSize];
                for (int j = 0; j < batchSize; j++) {
                    currentBatch[j] = fileList.get(i + j);
                }

                File mergedFile = mergeFiles(currentBatch);

                newFiles.add(mergedFile);

                cleanUpFilesByFilter(currentBatch, file -> !file.equals(mergedFile));

                i += batchSize;
            }

            fileList = newFiles;
        }

        File mergedResult = fileList.get(0);

        File finalMergedFile = new File(config.getOutputFilePath());
        if (!mergedResult.renameTo(finalMergedFile)) {
            throw new IOException(FAILED_RENAMING_FINAL_FILE_EXCEPTION_MESSAGE);
        }
    }

    public void generateTicket(Map<String, Object> context, String ticketFileName) throws IOException {
        XWPFTemplate.compile(
                        Objects.requireNonNull(GeneratorExecutor.class
                                .getClassLoader()
                                .getResourceAsStream(config.getTemplateFilePath()))
                )
                .render(context)
                .writeToFile(ticketFileName);
    }

    private void validateTicketCount(int requiredTicketCount, int totalQuestionCount) {
        if (totalQuestionCount < 2) {
            throw new IllegalArgumentException(NOT_ENOUGH_QUESTIONS_FOR_GENERATION_EXCEPTION_MESSAGE);
        }
        int halfQuestions = totalQuestionCount / 2;
        int uniquePairs = Math.min(halfQuestions, totalQuestionCount - halfQuestions);

        if (requiredTicketCount > uniquePairs && requiredTicketCount > totalQuestionCount) {
            throw new IllegalArgumentException(NOT_ENOUGH_UNIQUE_PAIRS_FOR_GENERATION_EXCEPTION_MESSAGE);
        }
    }

    private void cleanUpFiles(File[] files) {
        for (File file : files) {
            file.delete();
        }
    }

    private void cleanUpFilesByFilter(File[] files, Predicate<File> predicate) {
        for (File file : files) {
            if (predicate.test(file)) {
                file.delete();
            }
        }
    }

    private File mergeFiles(File[] filesBatch) {
        File outputFile = new File(TEMP_DOCX_FILE_NAME_TEMPLATE.formatted(System.nanoTime()));

        if (filesBatch.length == 2) {
            documentMerger.mergePair(outputFile.getPath(), filesBatch[0], filesBatch[1]);
        } else if (filesBatch.length == 10) {
            documentMerger.mergeMulti(outputFile.getPath(),
                    filesBatch[0], filesBatch[1], filesBatch[2], filesBatch[3], filesBatch[4],
                    filesBatch[5], filesBatch[6], filesBatch[7], filesBatch[8], filesBatch[9]
            );
        }
        return outputFile;
    }

    private List<String> readQuestionsFromDocument(String fileName) throws Exception {
        List<String> questions = new ArrayList<>();

        try (InputStream fis = GeneratorExecutor.class.getClassLoader().getResourceAsStream(fileName)) {
            if (fis == null) {
                throw new FileNotFoundException(FILE_NOT_FOUND_EXCEPTION_MESSAGE_TEMPLATE.formatted(fileName));
            }

            XWPFDocument document = new XWPFDocument(fis);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText().trim();
                if (!text.isEmpty()) {
                    questions.add(text);
                }
            }
        }

        return questions;
    }
}
