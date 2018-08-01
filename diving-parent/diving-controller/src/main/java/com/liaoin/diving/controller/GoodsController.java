package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.OrderOption;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.GoodsService;
import com.liaoin.diving.service.UserService;

import com.liaoin.diving.view.RecommendGoodsView;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/goods")
@Api(tags = "商品模块", value = "商品模块")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @PostMapping("/insert")
    @ApiOperation("新增商品，传入goods对象，无需id，自增")
    public Result save(@RequestBody Goods goods) {
        goodsService.insert(goods);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改商品信息，传入带id的goods对象")
    public Result update(@RequestBody Goods goods) {
        goodsService.update(goods);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiImplicitParam(name = "ids", value = "", dataType = "Integer", paramType = "query", required = true)
    @ApiOperation("根据多ID删除，ids：id数组")
    public Result delete(@RequestBody Integer[] ids) {
        goodsService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Goods goods = goodsService.findOne(id);
        System.out.println(goods.getCreateTime());
        if (goods == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", goods);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询，>>>进行页面商品展示<<<")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小，0则显示所有并且当前页码必须为0", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Goods goods) {
        PageBean<Goods> pageBean = goodsService.pageQuery(pageable, goods);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    /*@PostMapping("/findAllByFrontPage")
    @ApiOperation("获取商城商品，根据条件挑选，默认条件：未删除、上架，可选条件(有且仅有):是否首页，是否为周边，是否为推荐，折扣<=X,如0.95即为特卖")
    public Result findAll(@PageableDefault Pageable pageable, @RequestBody Goods goods) {
        //List<Goods> requiredGoods = goodsService.findAll(goods);
        PageBean<Goods> pageBean = goodsService.pageQuery(pageable, goods);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }*/

    @PostMapping("/findByCategory")
    @ApiOperation("商城分类过滤器查询商品，传入分类ID数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小，0则显示所有并且当前页码必须为0", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result findByCategory(@PageableDefault Pageable pageable, @RequestBody List<Integer> ids) {
//        PageBean<Goods> pageBean = goodsService.findByCategory(ids,pageable);
        PageBean<Goods> pageBean = goodsService.findByCategory(pageable, ids);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    @GetMapping("/findRecommendGoods")
    @ApiOperation("获取推荐商品信息，按照创建时间排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    public Result findRecommendGoods(Integer start, Integer pageSize) {
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<RecommendGoodsView> goodsList = goodsService.findRecommendOrderByCreateTime(pageHelp);
        if (goodsList.isEmpty()) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(goodsList));
    }

    @PostMapping("setGoodsBelongTo")
    @ApiOperation("设置商品归属,传入id数组、归属和设置/取消标示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = "设置/取消归属       归属：1删除/2首页/3推荐/4上架5周边       如：是否推荐/是否首页...", required = true),
            @ApiImplicitParam(name = "symbol", value = "设置/取消标示 标示：1设置/0取消", required = true)
    })
    public Result setRecommendGoods(@RequestBody Integer[] ids, Integer mode, Integer symbol) {
        Integer sign = goodsService.setRecommendGoods(ids, mode, symbol);
        if (sign == 0) {
            return new Result(500, "设置失败", null);
        }
        return new Result(200, "设置成功", null);
    }
}