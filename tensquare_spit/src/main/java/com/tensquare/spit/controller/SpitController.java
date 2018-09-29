package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> spits = spitService.findAll();
        return new Result(true,StatusCode.OK,"查询成功",spits);
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        Spit spit = spitService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",spit);
    }

    /**
     * 增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public Result update(@RequestBody Spit spit,@PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result update(@PathVariable String id){
        spitService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级ID查询吐槽分页数据
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/comment/{parentId}/{page}/{size}",method=RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentId,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageList = spitService.findByParentId(parentId,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent() ) );
    }


}
