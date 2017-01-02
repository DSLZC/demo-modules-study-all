package com.data.jpa.entity;

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

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "role_ordinal")
    @Enumerated
    private Role role;

    public enum Role{
        /** 预留 */
        reserved,
        /** 预留超级管理员 */
        super_manager,
        /** 管理员 */
        manager,
        /** 系统用户 */
        system_user,
        /** 普通用户 */
        user,
        /** 访客 */
        visitor;

        /** fastJson ordinal,默认为name */
        @JsonValue
        final String value() {
            return String.valueOf(this.ordinal());
        }
    }

}
