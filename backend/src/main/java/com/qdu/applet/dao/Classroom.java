package com.qdu.applet.dao;

import com.qdu.applet.utils.SpringBeanUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Classroom {

    //教室所属的教学楼，校区打算存储在redis的集合里
    private String name;
    private Integer id;
    private boolean[] isAvliable;

    public Classroom() {
        this.isAvliable = new boolean[11];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAvliable(int n) {
        return isAvliable[n];
    }

    public void setAvliable(int n) {
        isAvliable[n] = true;
    }

    public boolean[] getIsAvliable() {
        return isAvliable;
    }

    @Override
    public java.lang.String toString() {
        return this.getName().toString() + " : " + this.getId() + " : " + Arrays.toString(this.isAvliable);
    }

    public boolean store() {
        RedisTemplate<String, String> redisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        for (int i = 0; i < 11; i++) {
            if (isAvliable[i]) {
                redisTemplate.opsForSet().add(Integer.toString(i + 1), this.name);
            }
        }
        return false;
    }

}
