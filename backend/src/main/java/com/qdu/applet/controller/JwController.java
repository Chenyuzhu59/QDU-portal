package com.qdu.applet.controller;

import com.qdu.applet.dao.Course;
import com.qdu.applet.dao.Request;
import com.qdu.applet.dao.Score;
import com.qdu.applet.dao.User;
import com.qdu.applet.service.JwService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jw/")
public class JwController {
    private final Log logger = LogFactory.getLog(JwController.class);

    @Autowired
    private JwService jwService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping("captcha")
    public Map<String, Object> captcha() {
        Map<String, Object> map = jwService.getCaptcha();
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        if (jwService.isLoginSuccess(user)) {
            //保存登录成功的验证码
            map.put("isLoginSuccess", true);
            String greeting = jwService.getGreeting(user.getCookie());
            map.put("msg", greeting);
            map.put("cookie", user.getCookie());
        } else {
            map.put("isLoginSuccess", false);
            map.put("msg", "登录失败（账号或密码错误或验证码错误）");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("score")
    public ArrayList<Score> Score(@RequestBody Request request) {
        ArrayList<Score> scoreArrayList = jwService.getScores(request.getCookie());
        return scoreArrayList;
    }

    //TODO 接口都改成get，从curl里拿数据，别从RequestBody里拿
    @ResponseBody
    @RequestMapping("course")
    public ArrayList<Course> Course(@RequestBody Map<String, String> request) {
        ArrayList<Course> courseArrayList = jwService.getCourses(request.get("cookie"), request.get("year"), request.get("term"));
        return courseArrayList;
    }

    @ResponseBody
    @RequestMapping("week")
    public Map<String, Integer> week() {
        //TODO cookie失效
        Map<String, Integer> map = new HashMap<>();
        Integer n = jwService.getWeek();
        map.put("number", n);
        return map;
    }

    @ResponseBody
    @RequestMapping("cookie")
    public Map<String, Boolean> Cookie(@RequestBody Request request) {
        Map<String, Boolean> map = new HashMap<>();
        boolean valid = jwService.checkCookie(request.getCookie());
        map.put("valid", valid);
        return map;
    }

    @ResponseBody
    @RequestMapping("classroom")
    public Map<String, Object> classroom(@RequestBody Map<String, String> request) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<String> classrooms = jwService.getEmptyClassroom(
                request.get("cookie"),
                request.get("area"),
                Integer.valueOf(request.get("start")),
                Integer.valueOf(request.get("end"))
        );
        map.put("results", classrooms);
        return map;
    }

    @ResponseBody
    @RequestMapping("ids")
    public Map<String, Integer> classroomIds(@RequestBody Request request) {
        Map<String, Integer> map = jwService.getIds(request.getCookie(), "building").getNameMap();
        return map;
    }

}
