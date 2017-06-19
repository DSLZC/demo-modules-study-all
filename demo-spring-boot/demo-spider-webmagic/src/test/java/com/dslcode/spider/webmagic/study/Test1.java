package com.dslcode.spider.webmagic.study;

import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;

/**
 * Created by dongsilin on 2017/5/21.
 */
@Slf4j
public class Test1 {

    public static void main(String[] args) {
        try {
            String url = URLDecoder.decode("list.jd.com/list.html?cat=1672,2599,1440&ev=1107_9913%7C%7C85450%7C%7C9915%7C%7C9917%7C%7C9914%7C%7C9918%7C%7C9916%7C%7C33710%7C%7C89239", "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
