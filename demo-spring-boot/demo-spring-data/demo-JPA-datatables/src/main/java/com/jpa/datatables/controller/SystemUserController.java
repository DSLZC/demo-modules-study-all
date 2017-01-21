package com.jpa.datatables.controller;

import com.dslcode.core.property.PropertyUtil;
import com.dslcode.web.response.WebResponse;
import com.jpa.datatables.dto.SystemUserListDTO;
import com.jpa.datatables.general.datatables.DataTablesInputExtend;
import com.jpa.datatables.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dongsilin on 2017/1/4.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @GetMapping("/all")
    public WebResponse allUser(){
        return WebResponse.buildSuccessData(this.systemUserService.findAll());
    }

    @PostMapping("/datatable")
    @ResponseBody
    public DataTablesOutput datatableList(@RequestBody DataTablesInputExtend inputExtend){
        log.debug("user datatables..");
        DataTablesOutput output = this.systemUserService.datatableList(inputExtend);
        output.setData((List) PropertyUtil.dozerListMapper(output.getData(), SystemUserListDTO.class));
        log.debug("output = {}", output);
        return output;
    }

}
