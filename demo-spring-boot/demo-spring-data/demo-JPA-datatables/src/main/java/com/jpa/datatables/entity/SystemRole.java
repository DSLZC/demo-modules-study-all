package com.jpa.datatables.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Created by dongsilin on 2016/12/18.
 */

@Slf4j
@Data
@Entity
@Table(name = "system_role")
public class SystemRole{

    @Id
    @GeneratedValue
    private Integer id;

    @Transient
    private String name;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "role_ordinal")
    @Enumerated
    private Role role;

    public String getName() {
        return role.alias;
    }
    public enum Role{
        /** 预留 */
        reserved("预留"),
        /** 超级管理员 */
        super_manager("超级管理员"),
        /** 管理员 */
        manager("管理员"),
        /** 系统用户 */
        system_user("系统用户"),
        /** 普通用户 */
        user("普通用户"),
        /** 访客 */
        visitor("访客");

        private String alias;
        Role(String alias){ this.alias = alias;}
        /** fastJson ordinal,默认为name */
        @JsonValue
        final String value() {
            return String.valueOf(this.ordinal());
        }
    }

}
