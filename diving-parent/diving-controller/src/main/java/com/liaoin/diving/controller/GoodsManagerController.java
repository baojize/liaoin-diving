package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.GoodsClassify;
import com.liaoin.diving.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/31 11:23
 */
@RestController
@RequestMapping("/goodsManager")
@Api(tags = "[后台]商品模块", value = "[后台]商品模块")
public class GoodsManagerController {
    @Resource
    private GoodsService goodsService;

    @PostMapping
    @ApiOperation("设置商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "传入一个商品id", required = true)
    })
    public Result addOneGoodsClassify(Integer goodsId, @RequestBody GoodsClassify goodsClassify) {
        Integer sign = goodsService.addOneGoodsClassify(goodsId, goodsClassify);
        if (sign == 0) {
            return new Result(500, "设置失败", sign);
        }
        return new Result(200, "设置成功", sign);
    }
}
