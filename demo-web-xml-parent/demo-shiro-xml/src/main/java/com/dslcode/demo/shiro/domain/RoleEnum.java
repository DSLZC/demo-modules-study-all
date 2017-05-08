package com.dslcode.demo.shiro.domain;

public enum RoleEnum{
        /** 预留 */
        reserved("预留"),
        /** 超级管理员 */
        admin("超级管理员"),
        /** 管理员 */
        manager("管理员"),
        /** 系统用户 */
        system_user("系统用户"),
        /** 普通用户 */
        user("普通用户"),
        /** 访客 */
        visitor("访客");

        public String chName;

        public String getChName() {
            return chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }

        RoleEnum(String chName) {
            this.chName = chName;
        }

    }