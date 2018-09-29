package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 查询状态为2并以创建日期降序排序，查询前4条记录
     * SELECT * FROM tb_recruit WHERE state=2 ORDER BY createtime LIMIT 0,4;
     */
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     * 查询状态不为零并且以创建日期降序排序，查询前12条记录
     */
    List<Recruit> findTop12ByStateNotOrderByCreatetime(String state);
}


