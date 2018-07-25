package com.liaoin.diving.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Getter
@Setter
public class PageHelp {
    private Integer start;
    private Integer pageSize;
    private Object data;

    public PageHelp(Integer start, Integer pageSize, Object data) {
        this.start = start;
        this.pageSize = pageSize;
        this.data = data;
    }
}
