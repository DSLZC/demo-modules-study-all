package com.dslcode.core.function.stream;

import com.dslcode.core.function.bigdecimal.BigDecimalSummaryStatistics;
import com.dslcode.core.function.bigdecimal.ToBigDecimalFunction;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Created by dongsilin on 2017/4/13.
 */
public class CollectorsDefined {

    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

    public static <T> Collector<T, ?, BigDecimalSummaryStatistics> summarizing(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorDefinedImpl<T, BigDecimalSummaryStatistics, BigDecimalSummaryStatistics>(
                BigDecimalSummaryStatistics::new,
                (r, t) -> r.accept(mapper.applyAsBigDecimal(t)),
                (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

}
