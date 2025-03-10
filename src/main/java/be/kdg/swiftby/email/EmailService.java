package be.kdg.swiftby.email;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.data.model.messages.Attachment;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class EmailService {

    private final ApiClient apiClient;

    public EmailService() {
        Dotenv dotenv = Dotenv.load();
        String emailApi = dotenv.get("EMAIL_API");
        this.apiClient = Postmark.getApiClient(emailApi);
    }

    public void sendEmail(String to, String subject, String body, String attachmentPath) throws IOException, PostmarkException {
        Message message = new Message();

        message.setFrom("mohamed-jan.jalloh@student.kdg.be");
        message.setTo(to);
        message.setSubject(subject);
        message.setTextBody(body);

        if (attachmentPath != null && !attachmentPath.isEmpty()) {
            Path path = Paths.get(attachmentPath);

            byte[] fileContent = Files.readAllBytes(path);
            String base64File = Base64.getEncoder().encodeToString(fileContent);
            String fileName = path.getFileName().toString();

            Attachment attachment = new Attachment();

            attachment.setName(fileName);
            attachment.setContent(base64File);
            attachment.setContentType("text/pdf");
            message.addAttachment(fileName, fileContent, base64File);
        }
        MessageResponse response = apiClient.deliverMessage(message);
    }
}
