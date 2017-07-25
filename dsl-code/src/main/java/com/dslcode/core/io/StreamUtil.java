package com.dslcode.core.io;

import com.dslcode.core.thread.ThreadPoolUtil;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by dongsilin on 2017/5/25.
 * Java 输入、输出流util
 */
public final class StreamUtil {

    /**
     * 关闭流
     * @param cs
     */
    public static void close(Closeable... cs){
        ThreadPoolUtil.run(() -> {
            try {
                if(null != cs) {
                    for (Closeable c : cs) if(null != c) c.close();
                }
            } catch (IOException e) {
            }
        });

    }


}
