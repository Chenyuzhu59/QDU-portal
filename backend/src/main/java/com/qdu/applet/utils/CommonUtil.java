package com.qdu.applet.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    public static ArrayList<Integer> toWeekList(String rawString) {
        rawString = rawString.trim();
        ArrayList<Integer> weekList = new ArrayList<>();

        String numPattern = "\\d+";
        String odd = "单";
        String even = "双";

        ArrayList<String> matchStringList = regMatch(rawString, numPattern);
        if (matchStringList.size() == 1) {
            //匹配只有一个数字的情况
            weekList.add(Integer.valueOf(matchStringList.get(0)));
        } else if (matchStringList.size() == 2) {
            //匹配有两个数字的情况，说明是一个范围
            int start = Integer.valueOf(matchStringList.get(0));
            int end = Integer.valueOf(matchStringList.get(1));
            if (rawString.contains(odd)) {
                //单周
                for (int i = start; i <= end; i++) {
                    if (i % 2 == 1) {
                        weekList.add(i);
                    }
                }
            } else if (rawString.contains(even)) {
                //双周
                for (int i = start; i <= end; i++) {
                    if (i % 2 == 0) {
                        weekList.add(i);
                    }
                }
            } else {
                //每周
                for (int i = start; i <= end; i++) {
                    weekList.add(i);
                }
            }
        } else {
            weekList.add(0);
        }

        return weekList;
    }

    public static ArrayList<String> regMatch(String rawString, String patternString) {
        ArrayList<String> reslutList = new ArrayList<>();

        Pattern rule = Pattern.compile(patternString);
        Matcher matcher = rule.matcher(rawString);
        while (matcher.find()) {
            reslutList.add(matcher.group());
        }
        //如果什么都没匹配到，返回空字符串
        if (reslutList.size() == 0) {
            reslutList.add("");
        }
        return reslutList;
    }

    public static Integer toWeekInteager(String weekString) {
        switch (weekString) {
            case "星期一":
                return 1;
            case "星期二":
                return 2;
            case "星期三":
                return 3;
            case "星期四":
                return 4;
            case "星期五":
                return 5;
            case "星期六":
                return 6;
            case "星期日":
                return 7;
            default:
                return 0;
        }
    }

    public static String getNow() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(d);
    }

    //获取今天周几，周一知周日对应1至7
    public static Integer getTodayWeekNumber() {
        Calendar now = Calendar.getInstance();
        Integer number = now.get(Calendar.DAY_OF_WEEK);
        Map<Integer, Integer> resultMap = new HashMap<>();
        resultMap.put(1, 7);
        resultMap.put(2, 1);
        resultMap.put(3, 2);
        resultMap.put(4, 3);
        resultMap.put(5, 4);
        resultMap.put(6, 5);
        resultMap.put(7, 6);
        return resultMap.get(number);
    }
}