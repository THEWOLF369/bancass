package com.star.Ba.service;

import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;

import com.star.Ba.commun.BusinessException;
import com.star.Ba.commun.Constante;

import java.util.Properties;

@Stateless
public class MailServiceImpl implements MailService {
/*
    @Override
    public void sendOtpEmail(String to, String otp) {

        try {

            Properties props = new Properties();

            props.put("mail.smtp.host", Constante.mail_smtp_host);
            props.put("mail.smtp.port", Constante.mail_smtp_port);
            props.put("mail.smtp.auth", Constante.mail_smtp_auth);
            props.put("mail.smtp.starttls.enable", Constante.mail_smtp_starttls_enable);
            props.put("mail.smtp.ssl.protocols", Constante.mail_smtp_ssl_protocols);
            props.put("mail.mime.charset", Constante.mail_mime_charset);

            final String username = Constante.mail_username;
            final String password = Constante.mail_password;

            Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
            );

            message.setSubject("Code OTP de sécurité");

            message.setText(
                "Votre code OTP est : " + otp +
                "\nIl expire dans 5 minutes."
            );

            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erreur envoi email OTP", e);
        }
    }
*/    
    @Override
    public void sendOtpEmail(String to, String otp) {

        try {

            // =====================
            // SMTP CONFIG
            // =====================
            Properties props = new Properties();

            props.put("mail.smtp.host", Constante.mail_smtp_host);
            props.put("mail.smtp.port", Constante.mail_smtp_port);
            props.put("mail.smtp.auth", Constante.mail_smtp_auth);

            // STARTTLS obligatoire pour Office365
            props.put("mail.smtp.starttls.enable", Constante.mail_smtp_starttls_enable);
            props.put("mail.smtp.starttls.required", Constante.mail_smtp_starttls_required);

            // Forcer TLS 1.2 (important pour Office365)
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            // optionnel mais utile pour éviter certains errors SSL
            props.put("mail.smtp.ssl.trust", Constante.mail_smtp_host);

            final String username = Constante.mail_username;
            final String password = Constante.mail_password;

            // =====================
            // SESSION AUTH
            // =====================
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // (optionnel debug)
            // session.setDebug(true);

            // =====================
            // MESSAGE
            // =====================

            MimeMessage message = new MimeMessage(session);

            message.setFrom(
            	    new InternetAddress(username, "Sécurité Application", "UTF-8")
            	);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));


            message.setSubject("🔐 Votre code OTP de vérification", "UTF-8");

            String htmlContent =
                "<div style='font-family:Arial,sans-serif; padding:20px; background:#f4f4f4;'>" +

                    "<div style='max-width:500px; margin:auto; background:white; padding:20px; border-radius:10px;'>" +

                        "<h2 style='color:#2c3e50;'>Vérification de sécurité</h2>" +

                        "<p>Bonjour,</p>" +

                        "<p>Nous avons reçu une demande de connexion ou d’action nécessitant une vérification.</p>" +

                        "<p style='margin-top:20px;'>Votre code OTP est :</p>" +

                        "<div style='text-align:center; font-size:28px; letter-spacing:5px; " +
                        "font-weight:bold; color:#ffffff; background:#2c3e50; padding:15px; border-radius:8px;'>" +
                            otp +
                        "</div>" +

                        "<p style='margin-top:20px; color:#e67e22;'>" +
                            "⏱ Ce code est valable pendant 5 minutes." +
                        "</p>" +

                        "<hr>" +

                        "<p style='font-size:12px; color:gray;'>" +
                            "Si vous n'êtes pas à l'origine de cette demande, ignorez cet email." +
                        "</p>" +

                    "</div>" +

                "</div>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            // =====================
            // SEND
            // =====================
            Transport.send(message);

            System.out.println("OTP EMAIL SENT TO: " + to);

        } catch (Exception e) {
        	e.printStackTrace();
//            throw new RuntimeException("Erreur envoi email OTP", e);
        	 throw new BusinessException(
        		        "Le service d'envoi d'email est momentanément indisponible. Veuillez réessayer dans quelques instants.",
        		        e
        		    );
        }
    }
}