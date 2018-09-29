package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功");
    }

    @RequestMapping(value = "/article/{articleid}",method = RequestMethod.GET)
    public Result findByArticleId(@PathVariable String articleid){
        List<Comment> commentList = commentService.findByArticleid(articleid);
        return new Result(true, StatusCode.OK, "查找成功",commentList);
    }
}
