package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ResultUtils;
import com.abing.constant.SearchConstant;
import com.abing.model.domain.Picture;
import com.abing.model.dto.search.PictureQueryRequest;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2023/8/20 11:36
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/picture")
    public BaseResponse<List<Picture>> doSearchPicture(PictureQueryRequest pictureQueryRequest) throws IOException {
        Document doc = Jsoup.connect(String.format(SearchConstant.BING_SEARCH_URL,pictureQueryRequest.getSearchText())).get();
        log.info(doc.title());
        Elements newsHeadlines = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : newsHeadlines) {
            Picture picture = new Picture();
            String urlMap = element.select(".iusc").get(0).attr("m");
            Gson gson = new Gson();
            Map<String,Object> map = gson.fromJson(urlMap, HashMap.class);
            String mUrl = (String)map.get("murl");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            picture.setUrl(mUrl);
            picture.setTitle(title);
            pictureList.add(picture);
        }
        return ResultUtils.success(pictureList);
    }

    @GetMapping("/video")
    public BaseResponse<List<Picture>> doSearchVideo(PictureQueryRequest pictureQueryRequest) throws IOException {
        // TODO 更改地址
        Document doc = Jsoup.connect(String.format(SearchConstant.BING_SEARCH_URL,pictureQueryRequest.getSearchText())).get();
        log.info(doc.title());
        Elements newsHeadlines = doc.select("#mc_vtvc_video_1");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : newsHeadlines) {
            element.select(".mc_vtvc_con_rc").get(0).attr("ourl");
        }
        return ResultUtils.success(pictureList);
    }

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(String.format("https://cn.bing.com/videos/search?q=%s","篮球")).get();
        log.info(doc.title());
        Elements newsHeadlines = doc.select(".dg_u");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : newsHeadlines) {
            String alt = element.getElementsByTag("a").attr("aria-label");
            String ourl = element.getElementsByTag("a").attr("href");
            String title = element.select(".mc_vtvc_meta").get(0).attr("title");
            System.out.println(title);
            System.out.println(alt);
            System.out.println(ourl);
        }
    }
}
