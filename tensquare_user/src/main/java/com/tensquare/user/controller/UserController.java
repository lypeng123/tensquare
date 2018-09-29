package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtils;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(HttpServletRequest request,@RequestBody User user, @PathVariable String id ){
		//从request域中取值
		Claims claims = (Claims) request.getAttribute("admin_claims");
		//如果取到值，证明是管理员
		if(claims==null){
			//没有登录或是权限不足
			return new Result(false, StatusCode.LOGINERROR, "权限不足或未登录");
		}
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	/**
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}*/

	/**
	 * 删除普通用户的方法
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(HttpServletRequest request,@PathVariable String id ){

		//从request域中取值
		Claims claims = (Claims) request.getAttribute("admin_claims");
		//如果取到值，证明是管理员
		if(claims==null){
			//没有登录或是权限不足
			return new Result(false, StatusCode.LOGINERROR, "权限不足或未登录");
		}
		//删除用户
		userService.deleteById(id);

		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 发送短信验证码
	 */
	@RequestMapping(value="/sendsms/{mobile}",method = RequestMethod.PUT)
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 * 用户注册
	 */
	@RequestMapping(value="/register/{code}",method = RequestMethod.POST)
	public Result register(@RequestBody User mobile,@PathVariable String code){
		userService.add(mobile,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		User u=userService.login(user);
		if(u==null){
			return new Result(false, StatusCode.LOGINERROR, "用户名或者密码错误");
		}
		//生成token字符串,admin管理员  role：普通用户
		String token = jwtUtils.createToken(u.getId(), u.getMobile(), "role");
		//响应
		Map<String,Object> map=new HashMap<>();
		map.put("token", token);
		map.put("name", u.getNickname()); //昵称
		map.put("avatar", u.getAvatar()); //头像
		return new Result(true, StatusCode.OK, "登录成功",map);

	}
	
}
