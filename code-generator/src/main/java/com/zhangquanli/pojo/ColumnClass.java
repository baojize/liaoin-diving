package com.zhangquanli.pojo;

/**
 * 数据库字段封装类
 */
public class ColumnClass {

    /**
     * 数据库字段名称
     */
    private String columnName;
    /**
     * 数据库字段类型
     */
    private String columnType;
    /**
     * 数据库字段首字母小写且去掉下划线字符串
     */
    private String changeColumnName;
    /**
     * 数据库字段注释
     */
    private String columnComment;

    /**
     * 是否主键
     */
    private boolean primaryKey;

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getChangeColumnName() {
        return changeColumnName;
    }

    public void setChangeColumnName(String changeColumnName) {
        this.changeColumnName = changeColumnName;
    }

    @Override
    public String toString() {
        return "com.zhangquanli.pojo.ColumnClass{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", changeColumnName='" + changeColumnName + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", primaryKey=" + primaryKey +
                '}';
    }
}