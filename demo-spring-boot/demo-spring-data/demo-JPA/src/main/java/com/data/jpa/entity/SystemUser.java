package com.data.jpa.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dongsilin on 2016/12/18.
 */

@Slf4j
@Data
@Entity
@Table(name = "system_user")
public class SystemUser implements Serializable{
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String name;

    @Column(name = "dept_id")
    private Integer deptId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "system_user_role", joinColumns = @JoinColumn(name = "system_user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<SystemRole> roles;

}
