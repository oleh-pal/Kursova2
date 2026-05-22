package com.example.kursova.services;

import com.example.kursova.models.Order_options;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendConfirmationEmail(String toEmail, String confirmationLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Підтвердження реєстрації");
        helper.setFrom("your-email@gmail.com");
        String htmlMsg = "<h3>Привіт!</h3>"
                + "<p>Для підтвердження реєстрації перейдіть за посиланням:</p>"
                + "<a href='" + confirmationLink + "'>Підтвердити Email</a>";

        helper.setText(htmlMsg, true);

        mailSender.send(message);
    }
    public void sendPasswordChangeEmail(String toEmail, String link) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Підтвердження зміни пароля");
        helper.setFrom("your-email@gmail.com");

        String htmlMsg = """
        <h3>Запит на зміну пароля</h3>
        <p>Щоб підтвердити зміну пароля, натисніть на посилання нижче:</p>
        <p><a href="%s">Підтвердити зміну пароля</a></p>
        <p>Якщо це були не ви — просто ігноруйте лист.</p>
    """.formatted(link);

        helper.setText(htmlMsg, true);

        mailSender.send(message);
    }
    public void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Скидання паролю");
        helper.setFrom("your-email@gmail.com");

        String htmlMsg = "<h3>Скидання паролю</h3>"
                + "<p>Щоб змінити пароль, перейдіть за посиланням:</p>"
                + "<p><a href=\"" + resetLink + "\">Змінити пароль</a></p>"
                + "<p>Якщо це не ви — ігноруйте лист.</p>";

        helper.setText(htmlMsg, true);
        mailSender.send(message);
    }
    @Async
    public void sendStatusEmail(String toEmail, String status, String name,String orderCar) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Змінення статусу замовлення");
        helper.setFrom("your-email@gmail.com");
        String Message=
                "Вітаємо,"+ name +".\n"+
                "Повідомляємо Вам, що статус Вашого замовлення на автомобіль "+orderCar+" змінений.\n"+
                "Новий статус Вашого замовлення: "+status+".\n"+
                "Ми повідомимо Вас про подальші оновлення вашого замовлення.\n"+
                "З повагою Ваш автодилер Drive Auto Center.\n";
        helper.setText(Message);
        mailSender.send(message);
    }
    @Async
    public void sendConfirmOrderEmail(String toEmail, String status, String name, String orderCar,List<Order_options> order_options,int totalPrice) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(toEmail);
        helper.setSubject("Ваше замовлення");
        helper.setFrom("your-email@gmail.com");
        StringBuilder sb = new StringBuilder();
        order_options.forEach(order_option -> {
            sb.append("\t-")
                    .append(order_option.getOrder_name())
                    .append(" - ")
                    .append(order_option.getOption_price())
                    .append("$")
                    .append("\n");
        });
        String options = sb.toString();
        String Message=
                "Дякуємо за Ваше замовлення,"+ name +"!\n"+
                        "Ви замовили наступний автомобіль: "+orderCar+".\n"+
                        "Обрані додаткові опції:\n"+options+
                        "Cтатус Вашого замовлення: "+status+".\n"+
                        "Cума замовлення склала: "+ totalPrice+"$.\n"+
                        "З повагою Ваш автодилер Drive Auto Center.\n";
        helper.setText(Message);
        mailSender.send(message);
    }
}

