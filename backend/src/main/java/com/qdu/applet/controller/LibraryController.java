package com.qdu.applet.controller;

import com.qdu.applet.service.LibraryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/library/")
public class LibraryController {
    private final Log logger = LogFactory.getLog(JwController.class);

    @Autowired
    private LibraryService libraryService;

    @ResponseBody
    @RequestMapping("search")
    public Map<String, Object> search(@RequestBody Map<String, String> request) {
        String type = request.get("type");
        String text = request.get("text");
        Integer page = Integer.valueOf(request.get("page"));
        return libraryService.searchBook(type, text, page);
    }

    @ResponseBody
    @RequestMapping("detail")
    public Map<String, Object> detail(@RequestBody Map<String, String> request) {
        Integer id = Integer.valueOf(request.get("id"));
        return libraryService.getBookDetail(id);
    }
}
