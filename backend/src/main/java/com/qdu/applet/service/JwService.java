package com.qdu.applet.service;


import com.qdu.applet.dao.*;
import com.qdu.applet.utils.CommonUtil;
import com.qdu.applet.utils.HttpUtil;
import com.qdu.applet.utils.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

//TODO cookie 失效后返回出错消息
@Service
public class JwService {
    private final Log logger = LogFactory.getLog(JwService.class);

    public Map<String, Object> getCaptcha() {
        //TODO 验证码url中添加随机数，再验证码获取失败、超时的时候可以多次尝试
        String url = "http://jw.qdu.edu.cn/academic/getCaptcha.do";
        Map<String, Object> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity;
        //TODO 验证码异常处理
        //TODO 拦截器
        try {
            responseEntity = restTemplate.getForEntity(url, byte[].class);
            String imageString = "data:image/jpg;base64," + Base64Utils.encodeToString(responseEntity.getBody());
            HttpHeaders httpHeaders = responseEntity.getHeaders();
            String cookie = httpHeaders.getFirst(HttpHeaders.SET_COOKIE).split(";")[0].trim();
//            logger.info("cookie is " + cookie);
//            logger.info("imageString is " + imageString);
            result.put("code", imageString);
            result.put("cookie", cookie);
            Date date = new Date();
            long timeStamp = date.getTime();
            long expireTimeStamp = timeStamp + 1000 * 60 * 10;//设置为10分钟过期
            result.put("expireTime", expireTimeStamp);
            result.put("status", 200);
        } catch (HttpClientErrorException.NotFound notFound) {
            result.put("status", 404);
            logger.info("getCaptcha status 404 not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 500);
            logger.info("getCaptcha status 500 unknown error");
        } finally {
            return result;
        }
    }

    public boolean isLoginSuccess(User user) {
        String url = "http://jw.qdu.edu.cn/academic/j_acegi_security_check";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("j_username", user.getUsername());
        params.add("j_password", user.getPassword());
        params.add("j_captcha", user.getCaptcha());
        httpHeaders.add("cookie", user.getCookie());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String location = responseEntity.getHeaders().getFirst(HttpHeaders.LOCATION).trim();
        if (location.equals("http://jw.qdu.edu.cn/academic/index_new.jsp")) {
            logger.info(user.getUsername() + "\t登录成功");
            HttpHeaders responseEntityHeader = responseEntity.getHeaders();
            String cookie = responseEntityHeader.getFirst(HttpHeaders.SET_COOKIE).split(";")[0].trim();
            user.setCookie(cookie);
            return true;
        } else {
            logger.info(user.getUsername() + "\t登录失败");
            return false;
        }
    }

    //获取欢迎信息
    public String getGreeting(String cookie) {
        if (!checkCookie(cookie)) {
            return "cookie失效，请重新登录";
        }
        String url = "http://jw.qdu.edu.cn/academic/showHeader.do";
        Document document = HttpUtil.getDocument(cookie, url);
        Element element = document.getElementsByTag("span").first();
        String greetingMessage = "欢迎登录 " + element.text();
        logger.info(element.text());
        return greetingMessage;
    }

    //查成绩
    public ArrayList<Score> getScores(String cookie) {
        ArrayList<Score> scoreArrayList = new ArrayList<>();
        String url = "http://jw.qdu.edu.cn/academic/manager/score/studentOwnScore.do?groupId=&moduleId=2020&randomString=";
        Map<String, String> data = new HashMap<>();
        data.put("year", "");
        data.put("term", "");
        data.put("para", "0");
        data.put("sortColumn", "");
        data.put("Submit", "查询");
        Document document = HttpUtil.postDocument(cookie, url, data);
        Element table = document.getElementsByClass("datalist").first();
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            Score score = new Score();
            //TODO 自动识别行号，不要写死
            score.setYear(cols.get(0).text());
            score.setTerm(cols.get(1).text());
            score.setCourse(cols.get(4).text());
            score.setScore(cols.get(9).text());
            score.setCredit(cols.get(11).text());
            score.setAssess(cols.get(13).text());
            scoreArrayList.add(score);
        }
        Collections.sort(scoreArrayList);
        return scoreArrayList;
    }

    //查课表
    public ArrayList<Course> getCourses(String cookie, String year, String term) {
        //TODO 对所有传入数据进行检查 比如此处，年不合法，学期不合法,可以放在control里？
        year = Integer.toString(Integer.valueOf(year) - 1980);
        ArrayList<Course> courseArrayList = new ArrayList<>();
        String url = "http://jw.qdu.edu.cn/academic/student/currcourse/currcourse.jsdo";
        Connection connection = Jsoup.connect(url).header("cookie", cookie);
        connection.data("year", year).data("term", term);
        Document document = null;
        try {
            document = connection.get();
//            logger.info(document);
            Element table = document.getElementsByClass("infolist_tab").first();
            Elements rows = table.child(0).children();
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.children();
                Course course = new Course();
                course.setName(cols.get(2).text());
                course.setTeacher(cols.get(3).text());
                course.setCredit(cols.get(4).text());
                course.setAssess(cols.get(6).text());
                Elements trs = cols.get(9).select("tbody>tr");
                ArrayList<String[]> rawStrings = new ArrayList<>();
                ArrayList<CourseItem> courseItems = new ArrayList<>();
                for (Element tr : trs) {
                    Elements tds = tr.children();
                    String[] strings = new String[4];
                    for (int j = 0; j < tds.size(); j++) {
                        strings[j] = tds.get(j).text().trim();
                    }
                    rawStrings.add(strings);
                }

                for (String[] item : rawStrings) {
                    //标记数据是否合法
                    boolean isLegal = true;
                    for (int j = 0; j < 4; j++) {
                        if (item[j].equals(" ")) {
                            isLegal = false;
                        }
                    }

                    if (isLegal == true) {
                        CourseItem courseItem = new CourseItem();
                        courseItem.setWeeks(CommonUtil.toWeekList(item[0]));
                        courseItem.setWeekday(CommonUtil.toWeekInteager(item[1]));
                        courseItem.setStart(Integer.valueOf(item[2].split("、|-")[0]));
                        courseItem.setEnd(Integer.valueOf(item[2].split("、|-")[1]));
                        courseItem.setPlace(item[3]);
                        courseItems.add(courseItem);
                    }
                }
                course.setTimeAndPlace(courseItems);
                courseArrayList.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return courseArrayList;
        }
    }

    //获取当前周
    public Integer getWeek() {
        Integer weekNum = 0;

        String url = "http://jw.qdu.edu.cn/academic/listLeft.do?randomString=IuKxBsmVTS";
        Document document = HttpUtil.getDocument("", url);
        Element element = null;
        try {
            element = document.getElementsByTag("span").first();
        } catch (NullPointerException e) {
            logger.info("获取当前周失败，重试");
            document = HttpUtil.getDocument("", url);
            element = document.getElementsByTag("span").first();
        }


        String pattern = "(?<=第)\\d+(?=周)";
        ArrayList<String> matchArray = CommonUtil.regMatch(element.text(), pattern);
        if (matchArray.size() > 0) {
            weekNum = Integer.valueOf(matchArray.get(0));
        }
        return weekNum;
    }

    public boolean checkCookie(String cookie) {
        boolean valid = false;
        String url = "http://jw.qdu.edu.cn/academic/index_new.jsp";
        Connection connection = Jsoup.connect(url).
                header("cookie", cookie).
                followRedirects(false).
                method(Connection.Method.GET);
        try {
            Connection.Response response = connection.execute();
            if (response.statusCode() == 200) {
                valid = true;
            }
        } catch (IOException e) {
            logger.info(e);
        } finally {
            return valid;
        }
    }

    public ArrayList<String> getEmptyClassroom(String cookie, String area, Integer start, Integer end) {
        RedisTemplate<String, String> redisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        ArrayList<String> classrooms = new ArrayList<>();
        //如果正在查询中则等待
        while (redisTemplate.opsForValue().get("isUpdating") != null) {
            try {
                Thread.sleep(1000 * 5);
                logger.info("教室信息更新中，等待......");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //cookie失效则直接返回空数组
        if (!checkCookie(cookie)) {
            classrooms.add("请登录后使用");
            return classrooms;
        }
        //教室占用信息过期检测，过期则刷新
        if (redisTemplate.opsForValue().get("lastUpdate") == null) {
            emptyClassroomInit(cookie);
        } else {
            boolean isUpdatedToday = Integer.valueOf(redisTemplate.opsForValue().get("lastUpdate")) == CommonUtil.getTodayWeekNumber();
            if (!isUpdatedToday) {
                emptyClassroomInit(cookie);
            }
        }

        List<String> rang = new LinkedList<>();
        for (int i = start; i < end; i++) {
            rang.add(Integer.toString(i));
        }
        String tmpSetName = "tmp";

        redisTemplate.opsForSet().intersectAndStore(Integer.toString(end), rang, tmpSetName);
        redisTemplate.opsForSet().differenceAndStore(tmpSetName, "empty", tmpSetName);
        Set<String> classroomSet = redisTemplate.opsForSet().intersect(tmpSetName, area);
        classrooms = new ArrayList<>(classroomSet);
        for (int i = 0; i < classrooms.size(); i++) {
            //规则匹配到的教室从结果中删除
            String pattern = ".*[球|场|虚拟|校外|厅|馆|团体|摄影|博逸].*";
            if (classrooms.get(i).matches(pattern)) {
                classrooms.remove(i);
                i--;
            }
        }
        Collections.sort(classrooms);
        return classrooms;
    }

    //查空教室功能的初始化，建立需要的数据库。
    public boolean emptyClassroomInit(String cookie) {
        //清空redis数据库
        RedisTemplate<String, String> redisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        //如果已在查询则不初始化
        if (redisTemplate.hasKey("isUpdating")) {
            return false;
        }
        redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
        //标记正在更新数据库中
        redisTemplate.opsForValue().set("isUpdating", "true");

        //存储当日所有教室的占用信息
        PairMap pairMapBuilding = getBuildingIds(cookie, getAreaIds(cookie, "all"));
        String whichWeek = getWeek().toString();
        String week = CommonUtil.getTodayWeekNumber().toString();
        logger.info("今天是第" + whichWeek + "周，星期" + week + "。正在初始化教室占用信息...........");

        Iterator<Integer> iterator = pairMapBuilding.getIdIntegrator();

        while (iterator.hasNext()) {
            String buildingId = iterator.next().toString();
            storeClassroomInfoByBuildingId(cookie, buildingId, whichWeek, week);
        }
        //存储校区教室信息
        PairMap pairMapFushan = getClassroomIds(cookie, getBuildingIds(cookie, getAreaIds(cookie, "浮山校区")));
        PairMap pairMapJinjialing = getClassroomIds(cookie, getBuildingIds(cookie, getAreaIds(cookie, "金家岭校区")));
        PairMap pairMapSongshan = getClassroomIds(cookie, getBuildingIds(cookie, getAreaIds(cookie, "松山校区")));
        Iterator<String> iteratorString = pairMapFushan.getNameIntegrator();
        while (iteratorString.hasNext()) {
            redisTemplate.opsForSet().add("fushan", iteratorString.next());
        }
        iteratorString = pairMapJinjialing.getNameIntegrator();
        while (iteratorString.hasNext()) {
            redisTemplate.opsForSet().add("jinjialing", iteratorString.next());
        }
        iteratorString = pairMapSongshan.getNameIntegrator();
        while (iteratorString.hasNext()) {
            redisTemplate.opsForSet().add("songshan", iteratorString.next());
        }
        //存储整日为空的教室列表
        List<String> range = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        redisTemplate.opsForSet().intersectAndStore("1", range, "empty");

        redisTemplate.opsForValue().set("lastUpdate", week);
        redisTemplate.delete("isUpdating");
        logger.info("教室占用信息初始化完毕");
        return true;
    }


    //查询整栋楼的教室占用情况并存储到redis中
    public void storeClassroomInfoByBuildingId(String cookie, String buildingId, String whichWeek, String week) {
        String url = "http://jw.qdu.edu.cn/academic/teacher/teachresource/roomschedule_week.jsdo";
        Map<String, String> data = new HashMap<>();
        data.put("buildingid", buildingId);
        data.put("whichweek", whichWeek);
        data.put("week", week);
        Document document = HttpUtil.postDocument(cookie, url, data);
        Elements elementsInfo = document.select(".infolist_common");
        for (Element elementClassroom : elementsInfo) {
            //只爬取教室类型为普通教室或多媒体教室的教室
            if (elementClassroom.children().get(5).text().matches("普通教室|多媒体教室")) {
                Classroom classroom = new Classroom();
                classroom.setName(elementClassroom.children().first().text());
                classroom.setId(Integer.valueOf(elementClassroom.attr("id").substring(2)));

                Elements elementsBoolean = elementClassroom.select("tbody>tr:eq(1)>td");
                //设置serialNumberList跳过教室占用中的T1，T2
                int[] serialNumberList = {0, 1, 2, 3, 5, 6, 7, 8, 10, 11, 12};
                for (int i = 0; i < serialNumberList.length; i++) {
                    Element element = elementsBoolean.get(serialNumberList[i]);
                    if (element.text().equals(" ")) {
                        classroom.setAvliable(i);
                    }
                }
//                logger.info(classroom);
                classroom.store();
            }
        }
    }

    //从网页中解析name和id
    public PairMap getNameAndId(String cookie, String selectName, Map<String, String> data) {
        PairMap pairMap = new PairMap();
        String url = "http://jw.qdu.edu.cn/academic/teacher/teachresource/roomschedulequery.jsdo";
        Document document = HttpUtil.postDocument(cookie, url, data);
        Elements elements = document.select("select[name=" + selectName + "]>option:not(:first-child)");
        for (Element element : elements) {
            pairMap.put(element.text(), Integer.valueOf(element.val()));
        }
        return pairMap;
    }

    public PairMap getIds(String cookie, String type) {
        PairMap pairMapArea;
        PairMap pairMapBuilding = new PairMap();
        PairMap pairMapClassroom = new PairMap();
        Map<String, String> data = new HashMap<>();
        data.put("aid", "1709");
        pairMapArea = getNameAndId(cookie, "aid", data);
        Iterator<Integer> integratorArea = pairMapArea.getIdIntegrator();
        while (integratorArea.hasNext()) {
            data.put("aid", Integer.toString(integratorArea.next()));
            pairMapBuilding.putAll(getNameAndId(cookie, "buildingid", data));
        }
        data.clear();
        Iterator<Integer> integratorBuilding = pairMapBuilding.getIdIntegrator();
        while (integratorBuilding.hasNext()) {
            data.put("buildingid", Integer.toString(integratorBuilding.next()));
            pairMapClassroom.putAll(getNameAndId(cookie, "room", data));
        }
        switch (type) {
            case "all": {
                PairMap pairMapResult = new PairMap();
                pairMapResult.putAll(pairMapArea);
                pairMapResult.putAll(pairMapBuilding);
                pairMapResult.putAll(pairMapClassroom);
                return pairMapResult;
            }
            case "area":
                return pairMapArea;
            case "building":
                return pairMapBuilding;
            case "classroom":
                return pairMapClassroom;
            default:
                return pairMapClassroom;

        }
    }

    public PairMap getAreaIds(String cookie, String area) {
        PairMap pairMapArea;
        Map<String, String> data = new HashMap<>();
        data.put("aid", "1709");
        pairMapArea = getNameAndId(cookie, "aid", data);
        if (pairMapArea.get(area) != null) {
            return new PairMap().put(area, pairMapArea.get(area));
        } else {
            return pairMapArea;
        }
    }

    public PairMap getBuildingIds(String cookie, PairMap pairMapArea) {
        PairMap pairMapBuilding = new PairMap();
        Map<String, String> data = new HashMap<>();
        Iterator<Integer> integratorArea = pairMapArea.getIdIntegrator();
        while (integratorArea.hasNext()) {
            data.put("aid", Integer.toString(integratorArea.next()));
            pairMapBuilding.putAll(getNameAndId(cookie, "buildingid", data));
        }
        return pairMapBuilding;
    }

    public PairMap getClassroomIds(String cookie, PairMap pairMapBuilding) {
        PairMap pairMapClassroom = new PairMap();
        Map<String, String> data = new HashMap<>();
        Iterator<Integer> integratorBuilding = pairMapBuilding.getIdIntegrator();
        while (integratorBuilding.hasNext()) {
            data.put("buildingid", Integer.toString(integratorBuilding.next()));
            pairMapClassroom.putAll(getNameAndId(cookie, "room", data));
        }
        return pairMapClassroom;
    }
}