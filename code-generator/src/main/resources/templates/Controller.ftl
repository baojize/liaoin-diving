package ${packageName}.controller;

import ${packageName}.common.PageBean;
import ${packageName}.common.Result;
import ${packageName}.entity.${modelName};
import ${packageName}.service.${modelName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/${fieldName}")
@Api(tags = "${fieldName}模块")
public class ${modelName}Controller {

    @Resource
    private ${modelName}Service ${fieldName}Service;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody ${modelName} ${fieldName}) {
        ${fieldName}Service.insert(${fieldName});
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody ${modelName} ${fieldName}) {
        ${fieldName}Service.update(${fieldName});
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody ${primaryKey.columnType}[] ${primaryKey.changeColumnName}s) {
        ${fieldName}Service.delete(${primaryKey.changeColumnName}s);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "${primaryKey.changeColumnName}", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam ${primaryKey.columnType} ${primaryKey.changeColumnName}) {
        ${modelName} ${fieldName} = ${fieldName}Service.findOne(${primaryKey.changeColumnName});
        if (${fieldName} == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", ${fieldName});
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody ${modelName} ${fieldName}) {
        PageBean<${modelName}> pageBean = ${fieldName}Service.pageQuery(pageable, ${fieldName});
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }
}

