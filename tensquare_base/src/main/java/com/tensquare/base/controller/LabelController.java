package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 查询所有标签
     */
    @RequestMapping(method=RequestMethod.GET)
    public Result findAll(){
        List<Label> labels = labelService.findAll();
        return new Result(true,StatusCode.OK,"查找成功",labels);
    }

    /**
     * 根据id查询标签
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public Result findById(@PathVariable(value = "id") String id){
        Label label = labelService.findById(id);
        return new Result(true,StatusCode.OK,"查找成功",label);
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Label label){
        //设置id
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id") String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 分页查询
     */
    @RequestMapping(value="/search/{page}/{size}", method = RequestMethod.POST)
    public Result search(@PathVariable(value = "page") Integer page,@PathVariable(value = "size") Integer size){
        Page<Label> pageResult = labelService.search(page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageResult.getTotalElements(),pageResult.getContent()));
    }

    /**
     * 条件查询
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result search(@PathVariable Map searchMap){
        List<Label> labels = labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",labels);
    }
}
