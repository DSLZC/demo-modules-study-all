package com.jpa.datatables.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongsilin on 2017/1/11.
 */
@Data
@XmlRootElement
public class SystemRoleSmallDTO {

    private Integer id;

    private String name;

    private Boolean enabled = true;
}
