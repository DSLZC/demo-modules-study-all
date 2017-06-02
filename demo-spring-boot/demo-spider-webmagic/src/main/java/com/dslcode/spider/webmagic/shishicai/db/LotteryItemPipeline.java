package com.dslcode.spider.webmagic.shishicai.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
@Service
@Transactional
public class LotteryItemPipeline implements Pipeline {

    @Autowired
    private LotteryItemRepository lotteryItemRepository;


    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.get("url");
        String date = resultItems.get("date");
        List<List<String>> lotteryItems = resultItems.get("lotteryItems");

        List<LotteryItem> lotteryItemList =  lotteryItems.stream()
                .filter(item -> item.get(1).length() == 5)
                .map(item -> new LotteryItem(date, item.get(0), item.get(1), item.get(2), item.get(3), item.get(4)))
                .collect(Collectors.toList());

        lotteryItemRepository.save(lotteryItemList);
    }

}
