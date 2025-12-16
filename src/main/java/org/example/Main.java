package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Main {
    static void main() {

        final String usuario = "4bb29203af79c3";
        final String contrasena = "1841e2be21dd50";

        // Configuración para enviar el correo
        Properties propsenvio = new Properties();
        propsenvio.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // host address
        propsenvio.put("mail.smtp.port", "2525"); // puerto
        propsenvio.put("mail.smtp.auth", "true"); // habilitar autenticación
        propsenvio.put("mail.smtp.starttls.enable", "true"); // habilitar STARTTLS

        Session session = Session.getInstance(propsenvio, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contrasena);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("prueba@java.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("destino@cualqiera.com"));

            msg.setSubject("Prueba de Agente - [Dani]"); // asunto
            msg.setText("El sistema de notificaciones está activo"); // cuerpo del mensaje

            Transport.send(msg); // enviar el mensaje
            IO.println("Enviado. Revisa la bandeja de Mailtrap");

        } catch (MessagingException e) {
            IO.println("Error al enviar el correo: " + e.getMessage());
        }

        IO.println("---------------------------");

        // Configuración para recibir el correo
        Properties propsRecenpcion = new Properties();
        propsRecenpcion.put("mail.pop3.host", "pop3.mailtrap.io");
        propsRecenpcion.put("mail.pop3.port", "1100");
        propsRecenpcion.put("mail.pop3.starttls.enable", "true");

        Session sessionRecepcion = Session.getInstance(propsRecenpcion);
        try {
            Store store = sessionRecepcion.getStore("pop3");
            store.connect("pop3.mailtrap.io", 1100,usuario, contrasena);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] mensajes = inbox.getMessages();
            IO.println("Tienes " + mensajes.length + " mensajes.");

            for (Message mensaje : mensajes) {
                IO.println("De: " + mensaje.getFrom()[0].toString());
                IO.println("Asunto: " + mensaje.getSubject());
                //mensaje.setFlag(Flags.Flag.DELETED, true);
            }
            inbox.close(false); // true para eliminar mensajes marcados y false para no eliminarlos, pero ahora no hace nada
            store.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
