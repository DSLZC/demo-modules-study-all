package com.dslcode.demo.order.repository;

import com.dslcode.demo.order.domain.SubmitOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongsilin on 2017/4/7.
 */
public interface SubmitOrderItemRepository extends JpaRepository<SubmitOrderItem, Long>, JpaSpecificationExecutor<SubmitOrderItem> {
    SubmitOrderItem findByCode(String code);
//    @Query(value = "call p_insert_submit_order_item(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
//    int callProcedure(Long goodsId, Long userId, Integer num, String s, int row_num);
}
