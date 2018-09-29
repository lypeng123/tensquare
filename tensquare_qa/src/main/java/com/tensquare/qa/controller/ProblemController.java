package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest request;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加、发布问题(必须先登录才能发送)
	 * 普通用户登录可以发布问题
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){

		//从request中获取token
		Claims claims = (Claims) request.getAttribute("user_claims");
		//如果claims中有值
		if(claims==null){
			return new Result(false, StatusCode.LOGINERROR, "未登录或权限不足");
		}
		//已经登录
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询最新问题列表
	 */
	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result newlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size ){
		Page<Problem> newlist = problemService.findNewListByLabelId(labelid, page, size);
		//前端接收到的是pageResult格式的数据
		PageResult<Problem> pageResult = new PageResult<>(newlist.getTotalElements(), newlist.getContent());
		return new Result(true,StatusCode.OK,"查询成功",pageResult);
	}

	/**
	 * 查询最热回答列表
	 */
	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result hotlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size ){
		Page<Problem> newlist = problemService.findHotListByLabelId(labelid, page, size);
		//前端接收到的是pageResult格式的数据
		PageResult<Problem> pageResult = new PageResult<>(newlist.getTotalElements(), newlist.getContent());
		return new Result(true,StatusCode.OK,"查询成功",pageResult);
	}

	/**
	 * 查询等待回答问题列表
	 */
	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result waitlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size ){
		Page<Problem> newlist = problemService.finddWaitListByLabelId(labelid, page, size);
		//前端接收到的是pageResult格式的数据
		PageResult<Problem> pageResult = new PageResult<>(newlist.getTotalElements(), newlist.getContent());
		return new Result(true,StatusCode.OK,"查询成功",pageResult);
	}
}
