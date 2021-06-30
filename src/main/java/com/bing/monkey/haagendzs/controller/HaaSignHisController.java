package com.bing.monkey.haagendzs.controller;


import com.bing.monkey.common.entity.Result;
import com.bing.monkey.common.entity.ResultUtil;
import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import com.bing.monkey.haagendzs.entity.query.HaaSignHistoryQuery;
import com.bing.monkey.haagendzs.service.HaaSignHistoryService;
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
@RequestMapping("/haaSignHis")
@Api(tags = "签到记录-管理")
public class HaaSignHisController {

    @Autowired
    private HaaSignHistoryService haaSignHistoryService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询签到记录列表", notes = "页码pager.pageNum和每页记录数pager.pageSize必填项", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isPage", value = "是否分页", defaultValue = "true", dataType = "boolean")
    })
    public Result list(@RequestBody(required = false) HaaSignHistoryQuery haaSignHistoryQuery) {
        return ResultUtil.ok(haaSignHistoryService.list(haaSignHistoryQuery));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "增加或修改签到记录信息", notes = "传入一个签到记录对象进行存储，新增不带ID，修改带ID", httpMethod = "POST")
    public Result add(@RequestBody HaaSignHistory haaSignHistory) {
        Result result;
        HaaSignHistory add = haaSignHistoryService.add(haaSignHistory);
        if (null != add) {
            result = ResultUtil.ok("签到记录信息添加或修改成功！");
        } else {
            result = ResultUtil.err("签到记录信息添加或修改失败！");
        }
        return result;
    }

    //
    @ApiOperation(value = "通过id删除签到记录", notes = "通过签到记录id删除签到记录", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody String[] ids) {
        Result result;
        if (haaSignHistoryService.delete(ids)) {
            result = ResultUtil.ok("签到记录删除成功");
        } else {
            result = ResultUtil.err("签到记录删除失败");
        }
        return result;
    }

}
