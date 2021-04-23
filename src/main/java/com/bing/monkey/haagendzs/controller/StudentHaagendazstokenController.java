package com.bing.monkey.haagendzs.controller;


import com.bing.monkey.common.entity.Result;
import com.bing.monkey.common.entity.ResultUtil;
import com.bing.monkey.haagendzs.entity.Haatoken;
import com.bing.monkey.haagendzs.entity.query.HaatokenQuery;
import com.bing.monkey.haagendzs.service.StudentHaagendazstokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/studentHaagendazstoken")
@Api(tags = "问卷调查-管理")
public class StudentHaagendazstokenController {

    @Autowired
    private StudentHaagendazstokenService studentHaagendazstokenService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询调查问卷列表", notes = "页码pager.pageNum和每页记录数pager.pageSize必填项", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isPage", value = "是否分页", defaultValue = "true", dataType = "boolean")
    })
    public Result list(@RequestBody(required = false) HaatokenQuery haatokenQuery) {
        return ResultUtil.ok(studentHaagendazstokenService.list(haatokenQuery));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "增加或修改调查问卷信息", notes = "传入一个调查问卷对象进行存储，新增不带ID，修改带ID", httpMethod = "POST")
    public Result add(@RequestBody Haatoken haatoken) {
        Result result;
        Haatoken add = studentHaagendazstokenService.add(haatoken);
        if (null != add) {
            result = ResultUtil.ok("调查问卷信息添加或修改成功！");
        } else {
            result = ResultUtil.err("调查问卷信息添加或修改失败！");
        }
        return result;
    }

    //
    @ApiOperation(value = "通过id删除调查问卷", notes = "通过调查问卷id删除调查问卷", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody String[] ids) {
        Result result;
        if (studentHaagendazstokenService.delete(ids)) {
            result = ResultUtil.ok("调查问卷删除成功");
        } else {
            result = ResultUtil.err("调查问卷删除失败");
        }
        return result;
    }

}
