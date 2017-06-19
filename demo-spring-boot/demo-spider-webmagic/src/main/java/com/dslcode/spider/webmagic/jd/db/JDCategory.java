package com.dslcode.spider.webmagic.jd.db;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dongsilin on 2017/6/8.
 */
@Data
@Entity
@Table(name = "jd_category")
public class JDCategory {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstPageUrl;

    private String name;

    private String cat;

    private String ev;

    @Column(name = "parent_id")
    private Integer parentId;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private JDCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<JDCategory> childrens;

    public JDCategory(){}

    public JDCategory(String name, String firstPageUrl){
        this.name = name;
        this.firstPageUrl = firstPageUrl;
    }

    public JDCategory(String name, String firstPageUrl, Integer parentId){
        this.name = name;
        this.firstPageUrl = firstPageUrl;
        this.parentId = parentId;
    }

}
