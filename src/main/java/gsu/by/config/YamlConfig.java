package gsu.by.config;

import gsu.by.exception.YamlConfigReadException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import static gsu.by.constants.ExceptionConstants.*;
import static gsu.by.constants.YamlConfigConstants.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlConfig {

    private final String templateFilePath;

    private final String questionsFilePath;

    private final String outputFilePath;

    private final Integer numberOfTickets;

    private final String apiKey;

    public static YamlConfig loadConfig(String pathToConfig) {
        Yaml yaml = new Yaml();
        Map<String, Object> props;

        try (InputStream in = YamlConfig.class.getClassLoader().getResourceAsStream(pathToConfig)) {
            if (in == null) {
                throw new FileNotFoundException(FILE_NOT_FOUND_EXCEPTION_MESSAGE_TEMPLATE.formatted(pathToConfig));
            }
            props = yaml.load(in);
        } catch (IOException e) {
            throw new YamlConfigReadException(FAILED_READING_CONFIG_EXCEPTION_MESSAGE, e);
        }

        if (props != null) {
            String templateFileName = (String) props.get(TEMPLATE_FILE_PATH_PROP);
            String questionsFileName = (String) props.get(QUESTIONS_FILE_PATH_PROP);
            String outputFileName = (String) props.get(OUTPUT_FILE_PATH_PROP);
            Integer numberOfTickets = (Integer) props.get(NUMBER_OF_TICKETS_PROP);
            String apiKey = (String) props.get(API_KEY_PROP);
            YamlConfig yamlConfig = new YamlConfig(templateFileName, questionsFileName, outputFileName, numberOfTickets, apiKey);
            yamlConfig.validateConfig();
            return yamlConfig;
        }

        throw new YamlConfigReadException(FAILED_READING_CONFIG_EXCEPTION_MESSAGE);
    }

    private void validateConfig() {
        try {
            Objects.requireNonNull(templateFilePath, TEMPLATE_FILE_PATH_IS_NULL_EXCEPTION_MESSAGE);
            Objects.requireNonNull(questionsFilePath, QUESTIONS_FILE_PATH_IS_NULL_EXCEPTION_MESSAGE);
            Objects.requireNonNull(outputFilePath, OUTPUT_FILE_PATH_IS_NULL_EXCEPTION_MESSAGE);
            Objects.requireNonNull(numberOfTickets, TICKETS_NUMBER_IS_NULL_EXCEPTION_MESSAGE);
            Objects.requireNonNull(apiKey, API_KEY_IS_NULL_EXCEPTION_MESSAGE);
        } catch (NullPointerException e) {
            throw new YamlConfigReadException(e);
        }
    }
}
