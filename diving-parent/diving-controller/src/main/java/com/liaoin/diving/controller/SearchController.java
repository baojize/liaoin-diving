package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.service.ContentSearchService;
import com.liaoin.diving.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "搜索模块",value = "搜索模块")
public class SearchController {

    @Resource
    private ContentService contentService;
    @Resource
    private ContentSearchService contentSearchService;

    @GetMapping("/search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "key", value = "搜索关键词", dataType = "String", paramType = "query", required = true),
    })
    @ApiOperation("搜索")
    public Result search(@PageableDefault Pageable pageable, @RequestParam String key) {
        PageBean<Content> pageBean = contentSearchService.search(pageable, key);
        return new Result(200, "查询成功", pageBean);
    }

    @GetMapping("/batchImport")
    @ApiOperation("批量导入")
    public Result batchImport() {
        int size = 100;
        
        Pageable pageable = new PageRequest(0, size);
        PageBean<Content> pageBean = contentService.pageQuery(pageable, null);
        contentSearchService.save(pageBean.getRows());

        Long total = pageBean.getTotal();
        Long page = (total + size - 1) / size;
        if (page > 1) {
            for (int i = 1; i < page; i++) {
                pageable = new PageRequest(i, size);
                pageBean = contentService.pageQuery(pageable, null);
                contentSearchService.save(pageBean.getRows());
            }
        }
        return new Result(200, "导入成功", null);
    }
}
