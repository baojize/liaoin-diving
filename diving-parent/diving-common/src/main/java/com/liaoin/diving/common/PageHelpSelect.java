package com.liaoin.diving.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageHelpSelect<T> {
    private Integer start;
    private Integer pageSize;
    private T data;

    public  PageHelpSelect(){
    }

    public PageHelpSelect(Integer start, Integer pageSize, T data) {
        this.start = start;
        this.pageSize = pageSize;
        this.data = data;
    }
}
