package ${packagePath}.controller;


import ${packagePath}.entity.${modelClass}Query;
import ${packagePath}.entity.${modelClass};
import ${packagePath}.service.${modelClass}Service;
import ${packagePath}.utils.Result;
import ${packagePath}.utils.ResultUtil;
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
@RequestMapping("/${modelName}")
@Api(tags = "${classComment}管理")
public class ${modelClass}Controller {

    @Autowired
    private ${serviceClass} ${serviceName};

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询${classComment}列表", notes = "页码pager.pageNum和每页记录数pager.pageSize必填项", httpMethod = "POST")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "isPage", value = "是否分页", defaultValue = "true", dataType = "boolean")
    })
        public Result list(@RequestBody(required = false) ${modelClass}Query ${modelName}Query) {
        return ResultUtil.ok(${modelName}Service.list(${modelName}Query));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "增加或修改${classComment}对象", notes = "传入一个${classComment}对象进行存储，新增不带ID，修改带ID", httpMethod = "POST")
    public Result add(@RequestBody ${modelClass} ${modelName}) {
        Result result;
        ${modelClass} add = ${modelName}Service.add(${modelName});
        if (null != add) {
            result = ResultUtil.ok("${classComment}添加或修改成功！");
        } else {
            result = ResultUtil.err("${classComment}添加或修改失败！");
        }
        return result;
    }

    //
    @ApiOperation(value = "通过id删除${classComment}", notes = "通过${classComment}id删除${classComment}", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody String[] ids) {
        Result result;
        if (${modelName}Service.delete(ids)) {
            result = ResultUtil.ok("${classComment}删除成功");
        } else {
            result = ResultUtil.err("${classComment}删除失败");
        }
        return result;
    }

}
