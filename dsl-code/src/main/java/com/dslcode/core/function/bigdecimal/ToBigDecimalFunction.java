package com.dslcode.core.function.bigdecimal;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2017/4/13.
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);
}
