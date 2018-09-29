package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据id查询标签
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 添加标签
     */
    public void save(Label label) {
        label.setId(idWorker.nextId() + ""); //为标签设置id
        labelDao.save(label);
    }

    /**
     * 修改标签
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 分页条件查询
     */

    /**
     * 分页查询
     */
    public Page<Label> search(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1,size,Sort.by("asc") );
        Page<Label> labelPage = labelDao.findAll(pageable);
        return labelPage;
    }

    /**
     * 构建查询条件
     */
    private Specification<Label> createSpecification(Map searchMap) {
        return new Specification<Label>() {
            /**
             *
             * @param root //criteria中定义的根对象，类似于sql语句中的from之后的表
             * @param criteriaQuery //代表specification中的顶层查询对象，包含着查询的各个部分，如select、from、where等
             * @param criteriaBuilder //用来构建CriteriaQuery的构建器对象，生成具体查询语句
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if(searchMap.get("labelname")!=null && !"".equals(searchMap.get("labelname"))){
                    predicates.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%"+(String) searchMap.get("labelname")+"%"));
                }
                if(searchMap.get("state")!=null && !"".equals(searchMap.get("state"))){
                    predicates.add(criteriaBuilder.equal(root.get("state").as(String.class), (String)searchMap.get("state")));
                }
                if(searchMap.get("recommend")!=null && !"".equals(searchMap.get("recommend"))){
                    predicates.add(criteriaBuilder.equal(root.get("state").as(String.class), (String)searchMap.get("state")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    /**
     * 条件查询
     */
    public List<Label> findSearch(Map searchMap){
        Specification<Label> specification = createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    /**
     * 带条件的分页查询
     */
    public Page<Label> findSearch(Integer page,Integer size,Map searchMap){
        Specification<Label> specification = createSpecification(searchMap);
        Page<Label> labels = labelDao.findAll(specification, PageRequest.of(page-1, size));
        return labels;
    }

}
