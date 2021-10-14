package com.example.proyectoservicios;

import android.os.StrictMode;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvioDeCorreos {
    String correoAdmin="santiago.grosso051600@gmail.com";
    String contraseñaAdmin="tatiana051600";
    Session session;

    public void enviarCorreo(String fecha,String nombreServicio, String correoUsuario){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties p = new Properties();
        p.put("mail.smtp.host","smtp.googlemail.com");
        p.put("mail.smtp.socketFactory.port","465");
        p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth","true");
        p.put("mail.smtp.port","465");

        try {
            session=Session.getDefaultInstance(p,new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(correoAdmin,contraseñaAdmin);
                }
            });
            if(session!= null){
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correoAdmin));
                message.setSubject("SOLICITUD DE AGENDAMIENTO");
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(correoUsuario));
                message.setContent("Usted realizo una solicitud de agendamiento para un servicio de: "+nombreServicio+" para el: "+fecha,"text/html; charset=utf-8");
                Transport.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }



}

