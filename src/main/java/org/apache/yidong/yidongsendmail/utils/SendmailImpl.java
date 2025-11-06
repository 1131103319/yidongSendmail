package org.apache.yidong.yidongsendmail.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendmailImpl{
//    @Autowired
    private JavaMailSender mailSender;
//    @Value("${spring.mail.username}")  //发送人的邮箱  比如13XXXXXX@139.com
    private String from;

    /**
     * *发送简单的文本邮件
     * @param title
     * @param content
     * @param email
     */
    public void sendSimpleMail(String title,String content,String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);//发送人邮箱
        message.setSubject(title);//标题
        message.setTo(email);//对方邮箱
        message.setText(content);//内容
        mailSender.send(message);
    }
    /**
     * *多个收件人
     * @param title
     * @param content
     * @param email
     * @throws MessagingException
     */
    public void sendBatchMai(String title,String content,String[] email,JavaMailSender mailSender,String from) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        //true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setSubject(title);
        helper.setText(content,false);
        //多个收件人
        List<InternetAddress> list = new ArrayList<InternetAddress>();// 不能使用string类型的类型，这样只能发送一个收件人
        for(int i=0;i<email.length;i++) {
            list.add(new InternetAddress(email[i]));
        }
        InternetAddress[] address = list.toArray(new InternetAddress[list.size()]);
        message.setRecipients(Message.RecipientType.TO, address);
        message = helper.getMimeMessage();
        mailSender.send(message);
    }

    /**
     * *邮件发送全
     * @param title   标题
     * @param content 内容
     * @param email   发件人地址
     * @param attaches    附件
     * @throws Exception
     */
    public void sendMail(String title,String content,String[] email,List<File> attaches) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        //true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
        helper.setFrom(from);
        helper.setSubject(title);
        helper.setText(content,true);
        //文件附件
        if(attaches.size()>0) {
            for(File file : attaches) {
                helper.addAttachment(MimeUtility.encodeText(file.getName()), file);
            }
        }
        //多个收件人
        List<InternetAddress> list = new ArrayList<InternetAddress>();// 不能使用string类型的类型，这样只能发送一个收件人 
        for(int i=0;i<email.length;i++) {
            list.add(new InternetAddress(email[i]));
        }
        InternetAddress[] address = list.toArray(new InternetAddress[list.size()]);
        message.setRecipients(Message.RecipientType.TO, address);
        message = helper.getMimeMessage();
        mailSender.send(message);
    }
}