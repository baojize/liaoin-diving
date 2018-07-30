package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Commodity;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.service.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/commodity")
@Api(tags = "积分商品",value = "积分商品")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Commodity commodity) {
        commodityService.insert(commodity);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Commodity commodity) {
        commodityService.update(commodity);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        commodityService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Commodity commodity = commodityService.findOne(id);
        if (commodity == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", commodity);
    }

    @GetMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    public Result pageQuery(@RequestParam Integer start,@RequestParam Integer pageSize) {
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Commodity> commodityList = commodityService.findList(pageHelp);
        if (Objects.isNull(commodityList) || commodityList.size() == 0){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(commodityList));
    }
}
