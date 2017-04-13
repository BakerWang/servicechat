package com.xzg.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*定时任务*/
//@Component 取消注入spring
public class SchedulerTask {
// @Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron=*/6 * * * * ?,一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。
    private int count=0;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron="*/6 * * * * ?")
    // @Scheduled(fixedRate = 6000)
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
        System.out.println("现在时间：" +dateFormat.format(new Date()));
    }

}