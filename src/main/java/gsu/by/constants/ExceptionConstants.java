package gsu.by.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConstants {

    public static final String FILE_NOT_FOUND_EXCEPTION_MESSAGE_TEMPLATE = "File not found: %s";

    public static final String TEMPLATE_FILE_PATH_IS_NULL_EXCEPTION_MESSAGE = "Template file path is null!";

    public static final String QUESTIONS_FILE_PATH_IS_NULL_EXCEPTION_MESSAGE = "Questions file path is null!";

    public static final String OUTPUT_FILE_PATH_IS_NULL_EXCEPTION_MESSAGE = "Output file path is null!";

    public static final String TICKETS_NUMBER_IS_NULL_EXCEPTION_MESSAGE = "Number of tickets is null!";

    public static final String API_KEY_IS_NULL_EXCEPTION_MESSAGE = "Api key is null!";

    public static final String FAILED_READING_CONFIG_EXCEPTION_MESSAGE = "Failed reading config!";

    public static final String FAILED_RENAMING_FINAL_FILE_EXCEPTION_MESSAGE = "Failed renaming final file!";

    public static final String FAILED_FINDING_UNIQUE_QUESTION_PAIR_EXCEPTION_MESSAGE = "Failed finding unique question pair!";

    public static final String NOT_ENOUGH_QUESTIONS_FOR_GENERATION_EXCEPTION_MESSAGE = "Not enough questions for tickets generation!";

    public static final String NOT_ENOUGH_UNIQUE_PAIRS_FOR_GENERATION_EXCEPTION_MESSAGE  = "Not enough unique question pairs for tickets generation!";
}
