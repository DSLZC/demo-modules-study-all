package com.dslcode.demo.order.redis_queue.pojo;

import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;
import lombok.Data;

/**
 * Created by dongsilin on 2017/4/10.
 */
@Data
public class RedisOrderQueue {

    private Integer num;

    private Long goodsId;

    private Long userId;

    private String code;

    public RedisOrderQueue(Long userId, Long goodsId, Integer num) {
        this.num = num;
        this.goodsId = goodsId;
        this.userId = userId;
    }

    public RedisOrderQueue() {
    }

    private void initCode() {
        this.code = StringUtil.append2String(userId, "_", goodsId,"_", num, "_", RandomCode.getNumLetterLowerCode(4));
    }

    public String getRedisQueueStr(){
        if(NullUtil.isNull(code)) initCode();
        return this.code;
    }

    public static RedisOrderQueue parseStr(String s){
        if(NullUtil.isNull(s)) return null;
        String[] ss = s.split("_");
        RedisOrderQueue orderQueue = new RedisOrderQueue(Long.parseLong(ss[0]), Long.parseLong(ss[1]), Integer.parseInt(ss[2]));
        orderQueue.setCode(s);
        return orderQueue;
    }
}
