package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Commodity;
import com.liaoin.diving.entity.CommodityOrder;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.CommodityOrderService;
import com.liaoin.diving.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/commodityOrder")
@Api(tags = "积分商品订单",value = "积分商品订单")
public class CommodityOrderController {

    @Autowired
    private CommodityOrderService commodityOrderService;
    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(HttpSession session,@RequestBody CommodityOrder commodityOrder) {
        // 1. 校验登录状态
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        // 获取最新数据
        loginUser = userService.findOne(loginUser.getId());

        commodityOrderService.insert(loginUser,commodityOrder);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody CommodityOrder commodityOrder) {
        commodityOrderService.update(commodityOrder);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        commodityOrderService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        CommodityOrder commodityOrder = commodityOrderService.findOne(id);
        if (commodityOrder == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", commodityOrder);
    }

    @GetMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    public Result pageQuery(@RequestParam Integer start, @RequestParam Integer pageSize,
                            @ApiParam(value = "用户ID，不传查询所有",required = false) @RequestParam(required = false) Integer userId) {
        PageHelp pageHelp = new PageHelp(start, pageSize, userId);
        List<CommodityOrder> commodityOrderList = commodityOrderService.findList(pageHelp);
        if (Objects.isNull(commodityOrderList) || commodityOrderList.size() == 0){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(commodityOrderList));
    }

}
