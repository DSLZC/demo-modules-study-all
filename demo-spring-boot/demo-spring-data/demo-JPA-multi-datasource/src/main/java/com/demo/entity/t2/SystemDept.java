package com.demo.entity.t2;

import com.demo.entity.t1.SystemUser;
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
@Table(name = "system_dept")
public class SystemDept implements Serializable{
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(name = "create_user_id")
    private Long createUserId;

    /** 分库不支持级联 */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "create_user_id", referencedColumnName = "id")
    @Transient
    private SystemUser createUser;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dept")
    @Transient
    private List<SystemUser> users;


}
