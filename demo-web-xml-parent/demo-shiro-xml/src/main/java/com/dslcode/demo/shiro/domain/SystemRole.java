package com.dslcode.demo.shiro.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2016/12/18.
 */
@Data
public class SystemRole implements Serializable {
    public static final long serialVersionUID = 1L;

    private Integer roleId;

    private String roleName;

    private String roleChName;

    private Boolean enabled = true;

    public SystemRole(RoleEnum role) {
        this.roleId = role.ordinal();
        this.roleChName = role.getChName();
        this.roleName = role.name();
    }

    public static List<SystemRole> generateRoles(RoleEnum... roles){
        return Arrays.stream(roles).map(SystemRole::new).collect(Collectors.toCollection(ArrayList::new));
    }

}
