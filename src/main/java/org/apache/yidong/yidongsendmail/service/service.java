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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class service {
    @Autowired
    private SendmailImpl sendmail;
    @Value("${usernames}")
    private String[] mailUsernames;
    @Value("${usernames1}")
    private String[] mailUsernames1;
    @Value("${file1}")
    private String file1;
    @Value("${file2}")
    private String file2;
    @Value("${title}")
    private String title;
    @Value("${title1}")
    private String title1;
    Executor executor = Executors.newFixedThreadPool(3);

    @PostConstruct
    public void init() {
        System.out.println(Arrays.toString(mailUsernames));
        System.out.println(file1 + " " + file2);
    }

    @Scheduled(cron = "${cron}")
    public void dingshi() {
        log.info("开始执行定时任务");
        try {
            File file = new File(file1);
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

    @Scheduled(cron = "${cron1}")
    public void dingshi1() {
        log.info("开始执行定时任务");
        int hour = LocalDateTime.now().getHour();
        if(hour==9||hour>=13) {

            try {
                File file = new File(file2);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                BufferedReader bufferedReader = null;
                if (file.exists() && file.isFile()) {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append("\n");
                    }
                }
                String content = stringBuilder.toString();
                if (!content.trim().isEmpty()) {
                    sendmail.sendBatchMai(title1, content, mailUsernames1);
                    log.info("title:{},content:{},mailUsernames:{},status:success", title1, content, mailUsernames1);
                } else {
                    log.warn("content is empty");
                }
            } catch (Exception e) {
                log.error("发送mail异常", e);
            }
        }
    }

}
