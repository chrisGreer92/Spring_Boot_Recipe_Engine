package chrisgreer.recipeengine.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class SheetsAPIService {

    private static final String APPLICATION_NAME = "RecipeEngineApp";
    private final Sheets sheets;

    @Value("${sheets.spreadsheet.id}")
    private String spreadsheetId;
    @Value("${sheets.range}")
    private String range;

    public SheetsAPIService() throws Exception {
        String jsonKey = System.getenv("SHEETS_API_JSON");
        if (jsonKey == null || jsonKey.isBlank()) {
            throw new IllegalStateException("Missing SHEETS_API_JSON environment variable");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ByteArrayInputStream(jsonKey.getBytes(StandardCharsets.UTF_8))
        ).createScoped(List.of(SheetsScopes.SPREADSHEETS));

        this.sheets = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void updateCell(String value) throws IOException {
        ValueRange body = new ValueRange().setValues(List.of(List.of(value)));

        sheets.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();
    }

}
