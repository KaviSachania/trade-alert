package com.cryptoalert.cryptoalert.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

@Service
@Slf4j
public class AwsSesService {

    private static final String CHAR_SET = "UTF-8";
    private final SesClient client;
    private final String sender;

    @Autowired
    public AwsSesService(SesClient client,
                         @Value("${email.sender}") String sender) {
        this.client = client;
        this.sender = sender;
    }

    /**
     * This method send email using the aws ses sdk
     * @param recipient recipient
     * @param subject subject
     * @param bodyHTML bodyHTML
     */
    public void sendEmail(String recipient, String subject, String bodyHTML) {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        try {
            // Add subject, from and to lines
            message.setSubject(subject, "UTF-8");
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Create a multipart/alternative child container
            MimeMultipart msgBody = new MimeMultipart("alternative");

            // Create a wrapper for the HTML and text parts
            MimeBodyPart wrap = new MimeBodyPart();

    //        // Define the text part
    //        MimeBodyPart textPart = new MimeBodyPart();
    //        textPart.setContent(bodyText, "text/plain; charset=UTF-8");

            // Define the HTML part
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

            // Add the text and HTML parts to the child container
    //        msgBody.addBodyPart(textPart);
            msgBody.addBodyPart(htmlPart);

            // Add the child container to the wrapper object
            wrap.setContent(msgBody);

            // Create a multipart/mixed parent container
            MimeMultipart msg = new MimeMultipart("mixed");

            // Add the parent container to the message
            message.setContent(msg);

            // Add the multipart/alternative part to the message
            msg.addBodyPart(wrap);

            System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

            client.sendRawEmail(rawEmailRequest);

        } catch (SesException | IOException | MessagingException e) {
            System.err.println(e);
            System.out.printf("Error occurred sending email to {} ", recipient, e);
        }

//        try {
//            // The time for request/response round trip to aws in milliseconds
//            int requestTimeout = 3000;
//
//            SendEmailRequest request = new SendEmailRequest()
//                    .withDestination(
//                            new Destination().withToAddresses(email))
//                    .withMessage(new Message()
//                            .withBody(new Body()
//                                    .withHtml(new Content()
//                                            .withCharset(CHAR_SET).withData(body)))
//                            .withSubject(new Content()
//                                    .withCharset(CHAR_SET).withData(subject)))
//                    .withSource(sender).withSdkRequestTimeout(requestTimeout);
//            sesClient.sendEmail(request);
//        } catch (RuntimeException e) {
//            System.out.printf("Error occurred sending email to {} ", email, e);
//        }
    }
}