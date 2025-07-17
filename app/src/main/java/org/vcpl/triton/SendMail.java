package org.vcpl.triton;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Properties;

public class SendMail {

    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }

    public static  void sendMailUsingEWS() throws Exception {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2); // This is the latest version of this library

       //ExchangeCredentials credentials = new WebCredentials("VivritiGuest@vivriticapital.com", "Welcome@12345");
        ExchangeCredentials credentials = new WebCredentials("ssurve20@outlook.com", "Microsoft@1989");
        service.setCredentials(credentials);
// this.exchangeService.setWebProxy(new WebProxy("xx.xxx.xxx.xx", 8080)); // If you're behind a proxy
        service.setUrl(new URI("https://outlook.office365.com/EWS/Exchange.asmx")); // This is the standard URL
        //service.setUrl(new URI("https://portal.office.com")); // This is the standard URL

       // service.autodiscoverUrl("vivritiguest@vivriticapital.com" , new RedirectionUrlCallback());

        Folder inboxFolder = Folder.bind(service, WellKnownFolderName.Inbox);

        FindItemsResults<Item> results = service.findItems(inboxFolder.getId(), new ItemView(2)); // 10 is the number of items to fetch (pagesize)

        for (Item result : results)
        {
            EmailMessage currentEmail = (EmailMessage) result;


            // And so on
        }
    }

    public static void sendmail(String subject, String content) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols","TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {//vivritiguest@vivriticapital.com
               // return new PasswordAuthentication("api.digitalization@vivriticapital.com", "Boh38556");
                return new PasswordAuthentication("info@vivriticapital.com", "A1@Mem0()$42kW24@");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("info@vivriticapital.com", false));


        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("alagubalakumar.n@vivriticapital.com"));
        //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Mahendraraahul.CR@vivriticapital.com"));
        //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("thameem.m@vivriticapital.com"));

        msg.setSubject(subject);
        msg.setContent(content, "text/html");
        //msg.setText("Test Email");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
    }


    public static void main(String[] args) {
        try{
           // sendmail("Test-mail","Email body");
            sendMailUsingEWS();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
