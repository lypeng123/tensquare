package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 根据标签id查询最新问题列表
     * 分宜查询
     * SELECT * FROM tb_problem WHERE id IN(SELECT problemid FROM tb_pl WHERE labelid=1) ORDER BY createtime DESC;
     */
    @Query("SELECT p FROM Problem p WHERE id IN(SELECT problemid FROM Pl WHERE labelid=?1) ORDER BY replytime DESC")
    Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    /**
     * 热门问答列表，按回复数降序排序
     */
    @Query("SELECT p FROM Problem p WHERE id IN(SELECT problemid FROM Pl WHERE labelid=?1) ORDER BY reply DESC")
    Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);

    /**
     * 等待回答列表
     * 查询回答数为零的问题，降序排列
     * SELECT * FROM tb_problem WHERE id IN(SELECT problemid FROM tb_pl WHERE labelid=1) AND reply=0 ORDER BY createtime;
     */
    @Query("SELECT p FROM Problem p WHERE id IN(SELECT problemid FROM Pl WHERE labelid=?1) AND reply=0 ORDER BY createtime")
    Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);
}
