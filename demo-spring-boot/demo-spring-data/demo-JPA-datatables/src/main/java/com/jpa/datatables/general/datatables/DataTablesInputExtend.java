package com.jpa.datatables.general.datatables;

import com.dslcode.core.util.NullUtil;
import lombok.Data;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/1.
 */
@Data
public class DataTablesInputExtend extends DataTablesInput {
    private List<SearchParam> searchParams;

    public DataTablesInputExtend() {
        super();
//        this.getOrder().add(new Order(0, "desc"));
    }

    /**
     * 忽略某些搜索参数
     * @param names 参数名称
     */
    public void ignoreSearchParam(String... names){
        if(NullUtil.isNull(this.getSearchParams()) || NullUtil.isNull(names)) return;
        Iterator<SearchParam> iterator =  searchParams.iterator();
        while (iterator.hasNext()){
            String pName = iterator.next().getName();
            if(NullUtil.isNotNullAll(pName)){
                for(String name : names){
                    if(NullUtil.isNotNullAll(name) && pName.equals(name)){
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

    /**
     * 忽略某些搜索参数
     * @param ignoreParams 指定忽略的搜索参数
     */
    public void ignoreSearchParam(SearchParam... ignoreParams){
        if(NullUtil.isNull(this.getSearchParams()) || NullUtil.isNull(ignoreParams)) return;
        Iterator<SearchParam> iterator =  searchParams.iterator();
        while (iterator.hasNext()){
            SearchParam sParam = iterator.next();
            if(NullUtil.isNotNullAll(sParam.getName())){
                for(SearchParam ignoreParam : ignoreParams){
                    if(sParam.equals(ignoreParam)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

}
