package gsu.by.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

    public static final String PATH_TO_CONFIG_FILE = "config.yaml";

    public static final String API_KEY_AUTH_NAME = "Apikey";

    public static final String TICKET_DOCX_FILE_NAME_TEMPLATE = "ticket_%s.docx";

    public static final String TEMP_DOCX_FILE_NAME_TEMPLATE = "temp_%s.docx";

    public static final String TICKET_NUMBER_PLACEHOLDER = "n";

    public static final String QUESTION1_PLACEHOLDER = "q1";

    public static final String QUESTION2_PLACEHOLDER = "q2";

    public static final String QUESTION_PAIR_TEMPLATE = "%s:%s";

    public static final Integer MAX_FILES_TO_MERGE_PER_CALL = 10;

    public static final Integer MIN_FILES_TO_MERGE_PER_CALL = 2;

    public static final String TICKETS_SUCCESSFULLY_GENERATED_MESSAGE = "Tickets have been successfully generated";
}
