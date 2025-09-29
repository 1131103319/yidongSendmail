package org.apache.yidong.yidongsendmail.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.yidong.yidongsendmail.utils.SendmailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public void sendmailtoyidong(String filename,String title,String[] mailUsernames){
        log.info("开始执行定时任务{}",title);
        try {
            File file = new File(filename);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            BufferedReader bufferedReader = null;
            if (file.exists()&&file.isFile()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            }
            String content = stringBuilder.toString();
            if (!content.trim().isEmpty()) {
                sendmail.sendBatchMai(title, content, mailUsernames);
                log.info("title:{},content:{},mailUsernames:{},status:success", title, content, mailUsernames);
            } else {
                log.warn("content is empty");
            }
        } catch (Exception e) {
            log.error("发送mail异常", e);
        }
    }

}
