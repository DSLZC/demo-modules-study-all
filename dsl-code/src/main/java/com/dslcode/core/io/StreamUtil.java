package com.dslcode.core.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by dongsilin on 2017/5/25.
 */
public final class StreamUtil {

    public static void close(Closeable stream){
        try {
            if(null != stream) stream.close();
        } catch (IOException e) {}
    }


}
