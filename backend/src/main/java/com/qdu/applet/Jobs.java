package com.qdu.applet;

import com.qdu.applet.service.JwService;
import com.qdu.applet.utils.CommonUtil;
import com.qdu.applet.utils.HttpUtil;
import com.qdu.applet.utils.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Jobs {
    private final Log logger = LogFactory.getLog(JwService.class);
    @Autowired
    private JwService jwService;

    @Scheduled(cron = "0 0 6 * * ?")
    public void autoUpdateClassroomIonfo() {
        RedisTemplate<String, String> redisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        String cookie = redisTemplate.opsForValue().get("lastCookie");
        String messageSuccess = "教室占用信息准备完毕";
        String messageFailure = "教室占用信息未更新";
        String pushUrl = "https://sc.ftqq.com/SCU25090T5e76148f7d2c5637ccd3c2f63ada3db95ad952f88c51a.send?text=";
        //不存在lastCookie则说明是第一次启动需要更新
        if (!redisTemplate.hasKey("lastCookie")) {
            HttpUtil.getDocument(pushUrl + messageFailure);
            logger.info(messageFailure);
        } else {
            boolean isUpdatedToday = Integer.valueOf(redisTemplate.opsForValue().get("lastUpdate")) == CommonUtil.getTodayWeekNumber();
            if (isUpdatedToday) {
                logger.info("今日无需更新，" + messageSuccess);
                HttpUtil.getDocument(pushUrl + "教室占用信息无需更新");
            } else if (jwService.checkCookie(cookie)) {
                jwService.emptyClassroomInit(cookie);
                HttpUtil.getDocument(pushUrl + messageSuccess);
                logger.info(messageSuccess);
            } else {
                HttpUtil.getDocument(pushUrl + messageFailure);
                logger.info(messageFailure);
            }
        }
    }
}
