package gsu.by.merger;

import com.cloudmersive.client.MergeDocumentApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;
import gsu.by.exception.DocumentMergerException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static gsu.by.constants.CommonConstants.API_KEY_AUTH_NAME;

public class DocumentMerger {

    private final MergeDocumentApi apiInstance;

    public DocumentMerger(String apiKey) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication(API_KEY_AUTH_NAME);
        apiKeyAuth.setApiKey(apiKey);
        apiInstance = new MergeDocumentApi();
    }

    public void mergePair(String outputFileName, File file1, File file2) {
        try {
            byte[] result = apiInstance.mergeDocumentDocx(file1, file2);
            writeToFile(outputFileName, result);
        } catch (ApiException | IOException e) {
            throw new DocumentMergerException(e);
        }
    }

    public void mergeMulti(String outputFileName, File file0, File file1, File file2, File file3,
                           File file4, File file5, File file6, File file7, File file8, File file9) {

        try {
            byte[] result = apiInstance.mergeDocumentDocxMulti(file0, file1, file2, file3, file4, file5,
                    file6, file7, file8, file9
            );
            writeToFile(outputFileName, result);
        } catch (ApiException | IOException e) {
            throw new DocumentMergerException(e);
        }
    }

    private void writeToFile(String fileName, byte[] bytes) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(bytes);
        }
    }
}
