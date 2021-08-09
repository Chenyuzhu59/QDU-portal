package com.qdu.applet.service;

import com.qdu.applet.dao.BookItem;
import com.qdu.applet.dao.BookPlaceItem;
import com.qdu.applet.utils.CommonUtil;
import com.qdu.applet.utils.HttpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LibraryService {
    private final Log logger = LogFactory.getLog(JwService.class);

    public Map<String, Object> searchBook(String type, String text, Integer page) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<BookItem> bookItems = new ArrayList<>();

        //lib.qducc.com api中 0 代表第一页,1表示第二页，第一页和第二页相同
        String url = "http://lib.qducc.com/library/search_books.html?search_title=" + text
                + "&index=" + type + "&page=" + page;

        Document document = HttpUtil.getDocument(url);

        if (document == null) {
            map.put("results", bookItems);
            map.put("count", 0);
        } else {
            Elements elementList = document.getElementsByClass("weui_panel_bd").first()
                    .getElementsByTag("a");
            for (Element item : elementList) {
                BookItem bookItem = new BookItem();
                bookItem.setName(item.getElementsByClass("weui_media_title").text());
                String bookInfoRaw = item.getElementsByClass("weui_media_desc").text();
                bookItem.setAuthor(CommonUtil.regMatch(bookInfoRaw, "(?<=By ).*(?= @)").get(0));
                bookItem.setPress(CommonUtil.regMatch(bookInfoRaw, "(?<=@ ).*").get(0));
                String rawUrl = item.absUrl("href");
                Integer id = Integer.valueOf(CommonUtil.regMatch(rawUrl, "(?<=book_id=)\\d+").get(0));
                bookItem.setId(id);
                bookItems.add(bookItem);
            }
            String rawPage = document.getElementsByClass("button_sp_area").first().text();
            Integer pageCount = Integer.valueOf(CommonUtil.regMatch(rawPage, "(?<=共)\\d*(?=页)").get(0));
            map.put("results", bookItems);
            //lib.qducc.com api中 0 代表第一页,1表示第二页，第一页和第二页相同
            map.put("count", pageCount > 1 ? pageCount - 1 : pageCount);
        }
        return map;
    }

    public Map<String, Object> getBookDetail(Integer id) {
        Map<String, Object> map = new LinkedHashMap<>();
        ArrayList<BookPlaceItem> bookPositionList = new ArrayList<>();

        String url = "http://lib.qducc.com/library/book_detail.html?book_id=" + id;
        Document document = HttpUtil.getDocument(url);
        Element elementTitle = document.getElementsByClass("weui_media_title").first();
        map.put("name", elementTitle.text().trim());
        Elements elementsBookInfo = document.select(".weui_media_box:eq(0)>.weui_media_desc[style*=height:20px]");
        Map<String, String> regMap = new LinkedHashMap<>();
        regMap.put("author", "(?<=作者: )[^ ]+");
        regMap.put("press", "(?<=出版社 )[^ ]+");
        regMap.put("pageCount", "(?<=页数: )[^ ]+");
        regMap.put("price", "(?<=价格: )[^ ]+");
        regMap.put("sort", "(?<=分类: )[^ ]+");
        regMap.put("index", "(?<=索引号: )[^ ]+");
        regMap.put("ISBNISSN", "(?<=ISBN/ISSN: )[^ ]+");
        int i = 0;
        //因为网页中页数和价格,分类和索引在一个标签中
        int[] nums = {0, 1, 2, 2, 3, 3, 4};
        for (String key : regMap.keySet()) {
            map.put(key, CommonUtil.regMatch(elementsBookInfo.get(nums[i]).text(), regMap.get(key)).get(0));
            i++;
        }

        Elements elementsBookPosition = document.select(".weui_media_desc[style*=border-left:3px]");
        for (Element element : elementsBookPosition) {
            BookPlaceItem bookPlaceItem = new BookPlaceItem();
            String rawString = element.text();
            bookPlaceItem.setLibrary(CommonUtil.regMatch(rawString, "(?<=@)[^ ]+").get(0));
            bookPlaceItem.setCode(CommonUtil.regMatch(rawString, "(?<=条码)[^ ]+").get(0));
            bookPlaceItem.setPlace(CommonUtil.regMatch(rawString, "(?<= )[^ ]+(?=   )").get(0));
            if (CommonUtil.regMatch(rawString, "(?<=   )[^ ]+(?= )").get(0).equals("入藏")) {
                bookPlaceItem.setAvaliable(true);
            } else {
                bookPlaceItem.setAvaliable(false);
            }
            bookPositionList.add(bookPlaceItem);
        }
        map.put("results", bookPositionList);
        return map;
    }
}
