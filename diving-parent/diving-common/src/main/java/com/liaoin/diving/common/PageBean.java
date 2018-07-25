package com.liaoin.diving.common;

import java.io.Serializable;
import java.util.List;

/**
 * 返回分页数据的实体类
 */
public class PageBean<T> implements Serializable {
    private Long total;
    private List<T> rows;

    public PageBean() {
    }

    public PageBean(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
