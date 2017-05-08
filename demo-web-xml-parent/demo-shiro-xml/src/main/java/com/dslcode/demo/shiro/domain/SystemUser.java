package com.dslcode.demo.shiro.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongsilin on 2016/12/18.
 */
@Data
public class SystemUser implements Serializable{
    public static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String name;

    private List<SystemRole> roles;

    public SystemUser(Long id, String username, String password, String name, List<SystemRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }
}
