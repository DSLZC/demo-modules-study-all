package com.dslcode.core.function.bigdecimal;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2017/4/13.
 */
public class BigDecimalSummaryStatistics implements BigDecimalConsumer {

    private long count;
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal sumCompensation = BigDecimal.ZERO; // Low order bits of sum
    private BigDecimal simpleSum = BigDecimal.ZERO; // Used to compute right sum for non-finite inputs
    private BigDecimal min;
    private BigDecimal max;


    public BigDecimalSummaryStatistics() { }


    @Override
    public void accept(BigDecimal value) {
        ++count;
        simpleSum = simpleSum.add(value);
        sumWithCompensation(value);
        min = null == min? value : min.compareTo(value)==1? value : min;
        max = null == max? value : max.compareTo(value)==-1? value : max;
    }


    public void combine(BigDecimalSummaryStatistics other) {
        count += other.count;
        simpleSum = simpleSum.add(other.simpleSum);
        sumWithCompensation(other.sum);
        sumWithCompensation(other.sumCompensation);
        min = null == min? other.min : min.compareTo(other.min)==1? other.min : min;
        max = null == max? other.max : max.compareTo(other.max)==-1? other.max : max;
    }


    private void sumWithCompensation(BigDecimal value) {
        BigDecimal tmp = value.subtract(sumCompensation);
        BigDecimal velvel = sum.add(tmp); // Little wolf of rounding error
        sumCompensation = velvel.subtract(sum).subtract(tmp);
        sum = velvel;
    }

    public final long getCount() {
        return count;
    }

    public final BigDecimal getSum() {
        // Better error bounds to add both terms as the final sum
        BigDecimal tmp =  sum.add(sumCompensation);
//        if (BigDecimal.isNaN(tmp) && BigDecimal.isInfinite(simpleSum))
//            // If the compensated sum is spuriously NaN from
//            // accumulating one or more same-signed infinite values,
//            // return the correctly-signed infinity stored in
//            // simpleSum.
//            return simpleSum;
//        else
            return tmp;
    }


    public final BigDecimal getMin() {
        return min;
    }

    public final BigDecimal getMax() {
        return max;
    }

    public final BigDecimal getAverage() {
        return getCount() > 0 ? getSum().divide(new BigDecimal(getCount()), 4) : BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{count=%d, sum=%f, min=%f, average=%f, max=%f}",
                this.getClass().getSimpleName(),
                getCount(),
                getSum(),
                getMin(),
                getAverage(),
                getMax());
    }

}
