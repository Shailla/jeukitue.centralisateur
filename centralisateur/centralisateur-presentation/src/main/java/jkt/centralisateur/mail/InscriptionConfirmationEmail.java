package jkt.centralisateur.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class InscriptionConfirmationEmail extends Email {

    /**
     * Construit un émail à envoyer à l'utilisateur qui vient de s'inscrire
     * pour l'inviter à valider son inscription
     * 
     * @param lienConfirmation URL de confirmation d'inscription
     * @param userMail email du destinataire
     * @return
     * @throws IOException 
     */
    public SimpleMailMessage construct(final StringBuilder lienConfirmation,
                                       final String userMail) throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("lienConfirmation", lienConfirmation.toString());
        
        String templatePath = vmTemplate.getURL().getPath();
        String texte = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, "utf-8", model);
        
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userMail);
        mail.setSubject("Confirmation de votre inscription sur le serveur JKT");
        mail.setFrom("ahuut@free.fr");
        mail.setText(texte);
        
        return mail;
    }
}
