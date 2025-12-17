package org.apache.yidong.yidongsendmail.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.yidong.yidongsendmail.utils.SendmailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Service
public class service {
    @Autowired
    @Qualifier("javaMailSender")  // 注入默认的邮件发送器（spring.mail）
    private JavaMailSender mailSender1;

    @Autowired
    @Qualifier("javaMailSender2")  // 注入第二个邮件发送器（spring.mail2）
    private JavaMailSender mailSender2;

    @Autowired
    @Qualifier("javaMailSender2")  // 注入第二个邮件发送器（spring.mail2）
    private JavaMailSender mailSender3;

    @Value("${spring.mail.username}")  //发送人的邮箱  比如13XXXXXX@139.com
    private String from1;
    @Value("${spring.mail2.username}")  //发送人的邮箱  比如13XXXXXX@139.com
    private String from2;

    @Value("${spring.mail3.username}")  //发送人的邮箱  比如13XXXXXX@139.com
    private String from3;

    @Autowired
    private SendmailImpl sendmail;
    @Value("${usernames}")
    private String[] mailUsernames;
    @Value("${file}")
    private String[] file;
    @Value("${title}")
    private String[] title;
    @PostConstruct
    public void init() {
        log.info(Arrays.toString(mailUsernames));
        log.info(Arrays.toString(file));
        log.info(Arrays.toString(title));
    }

    @Scheduled(cron = "${cron1}")
    public void dingshi1() {
        sendmailtoyidong(file[0],title[0],mailUsernames);
    }

    @Scheduled(cron = "${cron2}")
    public void dingshi2() {
        int hour = LocalDateTime.now().getHour();
        log.info("当前时间为"+LocalDateTime.now());
        if(hour==9||hour>=13) {
            sendmailtoyidong(file[1],title[1],mailUsernames);
        }
    }
    @Scheduled(cron = "${cron3}")
    public void dingshi3() {
        sendmailtoyidong(file[2],title[2],mailUsernames);
    }
    @Scheduled(cron = "${cron4}")
    public void dingshi4() {
        sendmailtoyidong(file[3],title[3],mailUsernames);
    }
    @Scheduled(cron = "${cron5}")
    public void dingshi5() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[4],title[4],mailUsernames);
    }
    @Scheduled(cron = "${cron6}")
    public void dingshi6() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[5],title[5],mailUsernames);
    }
    @Scheduled(cron = "${cron7}")
    public void dingshi7() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[6],title[6],mailUsernames);
    }
    @Scheduled(cron = "${cron8}")
    public void dingshi8() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[7],title[7],mailUsernames);
    }
    @Scheduled(cron = "${cron9}")
    public void dingshi9() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[8],title[8],mailUsernames);
    }
    @Scheduled(cron = "${cron10}")
    public void dingshi10() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[9],title[9],mailUsernames);
    }
    @Scheduled(cron = "${cron11}")
    public void dingshi11() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[10],title[10],mailUsernames);
    }
    @Scheduled(cron = "${cron12}")
    public void dingshi12() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[11],title[11],mailUsernames);
    }
    @Scheduled(cron = "${cron13}")
    public void dingshi13() {
        log.info("开始执行定时任务");
        sendmailtoyidong(file[12],title[12],mailUsernames);
    }



    public void sendmailtoyidong(String filename,String title,String[] mailUsernames){
        log.info("开始执行定时任务{}",title);
        String line = null;
        BufferedReader bufferedReader = null;
        File file = new File(filename);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (file.exists()&&file.isFile()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            }
            String content = stringBuilder.toString();
            if (!content.trim().isEmpty()) {
                sendmail.sendBatchMai(title, content, mailUsernames,mailSender2,from2);
                log.info("title:{},content:{},mailUsernames:{},status:success", title, content, mailUsernames);
            } else {
                log.warn("content is empty");
            }
//            throw new Exception("hello");
        } catch (Exception e) {
            log.error("发送mail异常,重试", e);
            try{
                String content = stringBuilder.toString();
                if (!content.trim().isEmpty()) {
                    sendmail.sendBatchMai(title, content, mailUsernames,mailSender1,from1);
                    log.info("title:{},content:{},mailUsernames:{},status:success", title, content, mailUsernames);
                } else {
                    log.warn("content is empty");
                }
            }catch (Exception ex){
                log.error("发送mail异常,重试1", e);
                try{
                    String content = stringBuilder.toString();
                    if (!content.trim().isEmpty()) {
                        sendmail.sendBatchMai(title, content, mailUsernames,mailSender3,from3);
                        log.info("title:{},content:{},mailUsernames:{},status:success", title, content, mailUsernames);
                    } else {
                        log.warn("content is empty");
                    }
                }catch (Exception ex1){
                    log.error("发送mail异常", e);
                }
            }
        }
    }
}
