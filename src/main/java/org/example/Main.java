package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.net.InetAddress;
import java.util.Properties;

public class Main {
    static void main() {

        // Configuración para enviar el correo

        Properties props = new Properties();
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // host address
        props.put("mail.smtp.port", "25"); // puerto
        props.put("mail.smtp.auth", "true"); // habilitar autenticación
        props.put("mail.smtp.starttls.enable", "true"); // habilitar STARTTLS

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("4bb29203af79c3", "1841e2be21dd50");
            };
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("prueba@java.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("destino@cualqiera.com"));

            msg.setSubject("prueba Mailtrap"); // asunto
            msg.setText("Si ves esto es que funciona"); // cuerpo del mensaje

            Transport.send(msg); // enviar el mensaje
            IO.println("Enviadp. Revisa la bandeja de Mailtrap");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // Configuración para recibir el correo


    }
}
