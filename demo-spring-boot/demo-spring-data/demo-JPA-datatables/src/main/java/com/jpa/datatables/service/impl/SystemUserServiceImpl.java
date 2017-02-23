package com.jpa.datatables.service.impl;

import com.dslcode.core.array.ArrayUtil;
import com.dslcode.core.util.NullUtil;
import com.jpa.datatables.entity.SystemRole;
import com.jpa.datatables.entity.SystemUser;
import com.jpa.datatables.general.datatables.DataTablesInputExtend;
import com.jpa.datatables.general.datatables.SearchParam;
import com.jpa.datatables.repository.SystemUserRepository;
import com.jpa.datatables.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsilin on 2016/12/30.
 */

@Service
@Transactional
@Slf4j
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserRepository systemUserRepository;
    @Override
    public List<SystemUser> findAll() {
        return this.systemUserRepository.findAll();
    }

    @Override
    public DataTablesOutput datatableList(DataTablesInputExtend inputExtend) {

        return this.systemUserRepository.findAll(inputExtend, (Root<SystemUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) -> {
            List<SearchParam> searchParams = inputExtend.getSearchParams();
            if(NullUtil.isNull(searchParams)) return null;
            List<Predicate> predicateList = new ArrayList<Predicate>(searchParams.size());
            searchParams.forEach(searchParam -> {
                if(NullUtil.isNotNullAll(searchParam.getName()) && searchParam.getName().equals("startTime") && NullUtil.isNotNullAll(searchParam.getValue())){
                    log.debug("startTime={}", searchParam.getValue());
                } else if(NullUtil.isNotNullAll(searchParam.getName()) && searchParam.getName().equals("endTime") && NullUtil.isNotNullAll(searchParam.getValue())){
                    log.debug("endTime={}", searchParam.getValue());
                } else if(NullUtil.isNotNullAll(searchParam.getName()) && searchParam.getName().equals("username") && NullUtil.isNotNullAll(searchParam.getValue())){
                    log.debug("username={}", searchParam.getValue());
                    predicateList.add(cb.like(root.get("username"), "%"+searchParam.getValue()+"%"));
                } else if(NullUtil.isNotNullAll(searchParam.getName()) && searchParam.getName().equals("roleId") && !searchParam.getValue().equals("-1")){
                    log.debug("roleId={}", searchParam.getValue());
                    predicateList.add(cb.equal(root.get("roles"), SystemRole.Role.values()[Integer.parseInt(searchParam.getValue())]));
                }
            });

            return NullUtil.isNotNullAll(predicateList)? cb.and(ArrayUtil.toArray(predicateList, Predicate.class)) : null;
        });
    }


}
