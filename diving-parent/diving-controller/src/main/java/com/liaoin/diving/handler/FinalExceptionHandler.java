package com.liaoin.diving.handler;

import com.liaoin.diving.common.Result;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理响应状态码为404的异常处理器
 *
 * @author 张权立
 * @date 2018/06/08
 */
@RestController
public class FinalExceptionHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.OK)
    public Result handleError(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取request域中的状态码
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        // 2.根据响应状态，返回对应json数据
        switch (statusCode) {
            case 404:
                return new Result(statusCode, "请求地址未找到", null);
            default:
                return new Result(statusCode, "服务器内部发生异常", null);
        }
    }
}
