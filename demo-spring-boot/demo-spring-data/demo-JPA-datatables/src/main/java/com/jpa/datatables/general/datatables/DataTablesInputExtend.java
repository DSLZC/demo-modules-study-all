package com.jpa.datatables.general.datatables;

import lombok.Data;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/1.
 */
@Data
public class DataTablesInputExtend extends DataTablesInput {
    private List<SearchParam> searchParams;

    public DataTablesInputExtend() {
        super();
        this.searchParams = new ArrayList<SearchParam>(0);
        this.getOrder().add(new Order(0, "desc"));
    }

}
