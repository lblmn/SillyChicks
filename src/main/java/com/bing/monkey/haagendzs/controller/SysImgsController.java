package com.bing.monkey.haagendzs.controller;


import com.bing.monkey.common.entity.Result;
import com.bing.monkey.common.entity.ResultUtil;
import com.bing.monkey.haagendzs.entity.SysImgs;
import com.bing.monkey.haagendzs.entity.query.SysImgsQuery;
import com.bing.monkey.haagendzs.service.SysImgsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/sysImgs")
@Api(tags = "图片信息-管理")
public class SysImgsController {

    @Autowired
    private SysImgsService sysImgsService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询图片信息列表", notes = "页码pager.pageNum和每页记录数pager.pageSize必填项", httpMethod = "POST")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "isPage", value = "是否分页", defaultValue = "true", dataType = "boolean")
    })
        public Result list(@RequestBody(required = false) SysImgsQuery sysImgsQuery) {
        return ResultUtil.ok(sysImgsService.list(sysImgsQuery));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "增加或修改图片信息信息", notes = "传入一个图片信息对象进行存储，新增不带ID，修改带ID", httpMethod = "POST")
    public Result add(@RequestBody SysImgs sysImgs) {
        Result result;
        SysImgs add = sysImgsService.add(sysImgs);
        if (null != add) {
            result = ResultUtil.ok("图片信息信息添加或修改成功！");
        } else {
            result = ResultUtil.err("图片信息信息添加或修改失败！");
        }
        return result;
    }

    //
    @ApiOperation(value = "通过id删除图片信息", notes = "通过图片信息id删除图片信息", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody String[] ids) {
        Result result;
        if (sysImgsService.delete(ids)) {
            result = ResultUtil.ok("图片信息删除成功");
        } else {
            result = ResultUtil.err("图片信息删除失败");
        }
        return result;
    }

    @GetMapping("/getUrl")
    public SysImgs getUrl() {
        return sysImgsService.getRandomImg();
    }
}
