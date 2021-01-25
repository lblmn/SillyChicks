package ${packagePath}.controller;


import ${packagePath}.entity.${modelClass}Query;
import ${packagePath}.entity.${modelClass};
import ${packagePath}.service.${modelClass}Service;
import ${packagePath}.util.ResultUtil;
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
@Api(tags = "问卷调查-${classComment}管理")
public class ${modelClass}Controller {

    @Autowired
    private ${serviceClass} ${serviceName};

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询调查问卷${classComment}列表", notes = "页码pager.pageNum和每页记录数pager.pageSize必填项", httpMethod = "POST")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "isPage", value = "是否分页", defaultValue = "true", dataType = "boolean")
    })
        public ResultUtil list(@RequestBody(required = false) ${modelClass}Query ${modelName}Query) {
        return ResultUtil.ok(${modelName}Service.list(${modelName}Query));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "增加或修改调查问卷${classComment}信息", notes = "传入一个调查问卷${classComment}对象进行存储，新增不带ID，修改带ID", httpMethod = "POST")
    public ResultUtil add(@RequestBody ${modelClass} ${modelName}) {
        ResultUtil result;
        ${modelClass} add = ${modelName}Service.add(${modelName});
        if (null != add) {
            result = ResultUtil.ok("调查问卷${classComment}信息添加或修改成功！");
        } else {
            result = ResultUtil.err("调查问卷${classComment}信息添加或修改失败！");
        }
        return result;
    }

    //
    @ApiOperation(value = "通过id删除调查问卷${classComment}", notes = "通过调查问卷${classComment}id删除调查问卷${classComment}", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public ResultUtil delete(@RequestBody String[] ids) {
        ResultUtil result;
        if (${modelName}Service.delete(ids)) {
            result = ResultUtil.ok("调查问卷${classComment}删除成功");
        } else {
            result = ResultUtil.err("调查问卷${classComment}删除失败");
        }
        return result;
    }

}
