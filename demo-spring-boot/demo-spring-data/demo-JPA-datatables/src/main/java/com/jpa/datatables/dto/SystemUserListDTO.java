package com.jpa.datatables.dto;

import lombok.Data;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dongsilin on 2017/1/11.
 */
@Data
@XmlRootElement
public class SystemUserListDTO {

    private Long id;

    private String username;

    private String password;

    private String name;

    private Integer deptId;
    @Mapping("dept")
    private SystemDeptSmallDTO dept;
    @Mapping("roles")
    private List<SystemRoleSmallDTO> roles;
}
