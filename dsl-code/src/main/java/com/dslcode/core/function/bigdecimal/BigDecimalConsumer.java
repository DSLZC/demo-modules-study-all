package com.dslcode.core.function.bigdecimal;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by dongsilin on 2017/4/13.
 */
@FunctionalInterface
public interface BigDecimalConsumer {

    void accept(BigDecimal value);

    default BigDecimalConsumer andThen(BigDecimalConsumer after) {
        Objects.requireNonNull(after);
        return (BigDecimal t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
