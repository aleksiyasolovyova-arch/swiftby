package be.kdg.swiftby.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

//THIS IS FOR TESTING PURPOSES

@RestController
@RequestMapping("/api/email")
public class EmailApiController {

    private EmailService emailService;

    public EmailApiController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
        String to = request.get("to");
        String subject = request.get("subject");
        String body = request.get("body");
        String fileName = request.get("file");
        String attachmentPath = "src/main/resources/uploads/" + fileName;
        if (!Files.exists(Paths.get(attachmentPath))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Attachment file not found at " + attachmentPath);
        }
        try {
            emailService.sendEmail(to, subject, body, attachmentPath);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
