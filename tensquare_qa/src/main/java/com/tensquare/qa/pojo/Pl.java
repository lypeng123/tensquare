package com.tensquare.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 问答中间表对应实体类
 */
@Entity
@Table(name = "tb_pl")
public class Pl implements Serializable {
    //联合主键
    @Id
    private String problemid;

    @Id
    private String lableid;

    public Pl() {
    }

    public Pl(String problemid, String lableid) {
        this.problemid = problemid;
        this.lableid = lableid;
    }

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLableid() {
        return lableid;
    }

    public void setLableid(String lableid) {
        this.lableid = lableid;
    }
}
