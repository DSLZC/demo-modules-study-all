package com.dslcode.demo.order.repository;

import com.dslcode.demo.order.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongsilin on 2017/4/7.
 */
public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

    @Modifying
    @Query(value = "update Goods set stock = stock-?1, version = version + 1 where id=?2 and  version=?3")
    Integer updateStock(Integer num, Long id, Long version);
}
