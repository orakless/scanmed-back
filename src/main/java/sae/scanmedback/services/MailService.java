package sae.scanmedback.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.ReportStateChange;
import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;
import sae.scanmedback.utilities.TranslationUtilities;

@Service
public class MailService implements IMailService {
    private final JavaMailSender emailSender;

    MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMailTo(String email, String subject, String body)
            throws MailException, MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.setSubject(subject);
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setText(body, true);
        emailSender.send(message);
    }

    @Override
    public void sendStateChangeMail(User user, Report report, ReportStateChange reportStateChange)
            throws MailException, MessagingException {
        String subject = "Changement d'état pour votre signalement #" + report.getId();
        String body = "Bonjour,<br/>Votre signalement #" + report.getId() + " a été modifié par nos équipes."
                        + " Il est passé de l'état <b>"
                        + TranslationUtilities.translateReportState(reportStateChange.getOldState())
                        + "</b> à l'état <b>"
                        + TranslationUtilities.translateReportState(reportStateChange.getNewState())
                        + "</b>.<br/>Nous restons disponible pour toute information annexe dont vous " +
                        "pourriez avoir besoin.<br/>Cordialement,<br/>L'équipe ScanMed";
        sendMailTo(user.getUsername(), subject, body);
    }

    @Override
    public void sendAccountCreationMail(User user) throws MailException, MessagingException {
        String subject = "Bienvenue sur ScanMed, " + user.getDisplayName();
        String body = "Bonjour,<br/>Votre compte ScanMed @" + user.getDisplayName() + " a bien été créé."
                + "<br/>Nous restons disponible pour toute information annexe dont vous pourriez avoir besoin. <br>"
                + "Cordialement,<br/>L'équipe ScanMed";
        sendMailTo(user.getUsername(), subject, body);
    }

    @Override
    public void sendTokenGenerationMail(User user, Token token) throws MailException, MessagingException {
        String subject = "Nouvel appareil enregistré sur ScanMed";
        String body = "Bonjour,<br/>Un nouvel appareil <b>"+token.getDevice()+"</b> a été enregistré sur votre compte "
                + "ScanMed. Si cette connexion ne provient pas de vous, veuillez changer votre mot de passe et annuler "
                + "la connexion de cet appareil depuis votre application.<br/>Cordialement,<br/>L'équipe ScanMed";
        sendMailTo(user.getUsername(), subject, body);
    }

    @Override
    public void sendAccountDeletionMail(String email) throws MailException, MessagingException {
        String subject = "Suppression de votre compte ScanMed";
        String body = "Bonjour,<br/>Suite à votre demande de suppression de compte, nous venons de "
                + "supprimer toutes les données vous concernant de notre base de données.<br>Cordialement," +
                "<br/>L'équipe ScanMed";
        sendMailTo(email, subject, body);
    }

    @Override
    public void sendPasswordResetMail(String email, String token) throws MailException, MessagingException {
        String subject = "Demande de réinitialisation du mot de passe";
        String body = "Bonjour,<br/>Le code pour réinitialiser votre mot de passe est <b>"+ token +"</b>.<br/><b>Vous n'êtes"
                + " pas à l'origine de cette demande ?</b><br/><br/>Il est possible qu'une personne ait accidentellement"
                + " saisi votre e-mail sur le formulaire.<br/>Cordialement,<br/>L'équipe ScanMed";
        sendMailTo(email, subject, body);
    }

    @Override
    public void sendPasswordResetConfirmationMail(String email) throws MailException, MessagingException {
        String subject = "Confirmation de la réinitialisation de votre mot de passe";
        String body = "Bonjour,<br/>Votre mot de passe a bien été réinitialisé. Vous pouvez désormais vous connecter"
                + " avec.<br/>Cordialement,<br/>L'équipe ScanMed";
        sendMailTo(email, subject, body);
    }
}
