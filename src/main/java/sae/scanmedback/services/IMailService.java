package sae.scanmedback.services;

import jakarta.mail.MessagingException;
import org.springframework.mail.MailException;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.ReportStateChange;
import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;

import java.io.UnsupportedEncodingException;

public interface IMailService {
    void sendMailTo(String email, String subject, String body)
            throws MailException, MessagingException;

    void sendStateChangeMail(User user, Report report, ReportStateChange reportStateChange)
        throws MailException, MessagingException;

    void sendAccountCreationMail(User user)
        throws MailException, MessagingException;

    void sendTokenGenerationMail(User user, Token token) throws MailException, MessagingException;

    void sendAccountDeletionMail(String email) throws MailException, MessagingException;

    void sendPasswordResetMail(String email, String token) throws MailException, MessagingException;

    void sendPasswordResetConfirmationMail(String email) throws MailException, MessagingException;
}
