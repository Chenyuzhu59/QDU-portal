package com.qdu.applet.dao;

import com.qdu.applet.utils.SpringBeanUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class Request {
    private String cookie;

    public String getCookie() {
        RedisTemplate<String, String> redisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        redisTemplate.opsForValue().set("lastCookie", this.cookie);
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
