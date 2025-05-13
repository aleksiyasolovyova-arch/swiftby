package be.kdg.swiftby.email;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.data.model.messages.Attachment;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class EmailService {

    private final ApiClient apiClient;

    @Value("${email}")
    private String email;

    public EmailService(@Value("${emailapi.key}") String key) {

        this.apiClient = Postmark.getApiClient(key);
    }

    public void sendEmail(String to, String subject, String body, String attachmentPath) throws IOException, PostmarkException {
        Message message = new Message();

        message.setFrom(email);
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

    public void sendAccountRegistrationEmail(String userEmail, String userName, String link) throws IOException, PostmarkException {
        String subject = "Your Swiftby Account Registration";
        String body = String.format(
                "Hey %s,\n\nYour account has been registered in the system, to set up your password click on this link: %s\n\nKind regards,\nTeam Swiftby",
                userName,
                link
        );
        sendEmail(userEmail, subject, body, null);
    }
}
