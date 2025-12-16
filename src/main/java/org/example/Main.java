package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private final static String ARCHIVO_CONFIG = "config.properties";
    private static String destinatario = "destino@cualqiera.com";
    private static String asunto = "Prueba de Agente - [Dani]";
    private static String cuerpo = "El sistema de notificaciones está activo";

    static void main() {

        Properties ficheroProps = new Properties();
        try(FileInputStream entrada = new FileInputStream(ARCHIVO_CONFIG)) {
            ficheroProps.load(entrada);
        } catch (IOException e) {
            IO.println("Error al leer el fichero de configuración: " + e.getMessage());
            return;
        }

        ServicioCorreo servicioCorreo = new ServicioCorreo(ficheroProps);
        servicioCorreo.enviarCorreo(destinatario, asunto, cuerpo);
        servicioCorreo.leerCorreos();



    }
}
