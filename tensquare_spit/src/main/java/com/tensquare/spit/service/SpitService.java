package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    //利用MongoTemplate对象操作Mongo数据库，提高效率
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *  查询全部记录
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据id查询吐槽
     */
    public Spit findById(String spitId){
        return spitDao.findById(spitId).get();
    }

    /**
     * 增加,发布吐槽
     */
    public void add(Spit spit){
        //添加主键
        spit.set_id(idWorker.nextId()+"");
        //封装属性，吐槽数据是存在mongodb数据库中的,content内容是前台封装好的
        spit.setNickname("");
        spit.setPublishtime(new Date());//创建时间
        spit.setShare(0);//分享数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        spit.setThumbup(0);//点赞数
        spit.setUserid("1");//用户id，后期会做处理
        spit.setVisits(0);//浏览量
        //如果该评论存在父id，父id的评论数量+1
        if(null!=spit.getParentid()&&!"".equals(spit.getParentid())){
            Spit parentSpit = spitDao.findById(spit.getParentid()).get();
            parentSpit.setComment(parentSpit.getComment()+1);
            spitDao.save(parentSpit);
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     */
    public void delete(String spitId){
        spitDao.deleteById(spitId);
    }

    /**
     * 根据父id查询吐槽列表
     */
    public Page<Spit> findByParentId(String parentId,int page,int size){
        PageRequest pageable = PageRequest.of(page-1, size);
        Page<Spit> spitPage = spitDao.findByParentid(parentId, pageable);
        return spitPage;
    }

    /**
     * 吐槽点赞
     */
    public void updateThumbup(String spitId){
        /** 传统方式实现
            Spit spit = spitDao.findById(spitId).get();
            spit.setThumbup(spit.getThumbup()+1);
            spitDao.save(spit);
         */
        //利用MongoTemplate实现
    }

}
