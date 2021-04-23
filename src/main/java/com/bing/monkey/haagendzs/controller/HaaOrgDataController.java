package com.bing.monkey.haagendzs.controller;


import com.bing.monkey.common.entity.Result;
import com.bing.monkey.common.entity.ResultUtil;
import com.bing.monkey.haagendzs.entity.HaaOrgData;
import com.bing.monkey.haagendzs.entity.query.HaaOrgDataQuery;
import com.bing.monkey.haagendzs.service.HaaOrgDataService;
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
@RequestMapping("/haaOrgData")
@Api(tags = "-原始信息表管理")
public class HaaOrgDataController {

    @Autowired
    private HaaOrgDataService haaOrgDataService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询原始信息表列表", notes = "页码pager.pageNum和每页记录数pager.pageSize必填项", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isPage", value = "是否分页", defaultValue = "true", dataType = "boolean")
    })
    public Result list(@RequestBody(required = false) HaaOrgDataQuery haaOrgDataQuery) {
        return ResultUtil.ok(haaOrgDataService.list(haaOrgDataQuery));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "增加或修改原始信息表信息", notes = "传入一个原始信息表对象进行存储，新增不带ID，修改带ID", httpMethod = "POST")
    public Result add(@RequestBody HaaOrgData haaOrgData) {
        Result result;
        HaaOrgData add = haaOrgDataService.add(haaOrgData);
        if (null != add) {
            result = ResultUtil.ok("原始信息表信息添加或修改成功！");
        } else {
            result = ResultUtil.err("原始信息表信息添加或修改失败！");
        }
        return result;
    }

    //
    @ApiOperation(value = "通过id删除原始信息表", notes = "通过原始信息表id删除原始信息表", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody String[] ids) {
        Result result;
        if (haaOrgDataService.delete(ids)) {
            result = ResultUtil.ok("原始信息表删除成功");
        } else {
            result = ResultUtil.err("原始信息表删除失败");
        }
        return result;
    }

}
