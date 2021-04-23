package com.bing.monkey.common.entity;


import com.bing.monkey.common.util.ToolkitUtil;

/**
 * 数据表字段信息类
 */
public class ColumnInfo {

    /**
     * *****************主要字段********************
     */

    /**
     * 数据库字段名称
     **/
    private String columnName;

    /**
     * 数据库字段类型
     **/
    private String columnType;

    /**
     * 字段数据长度
     */
    private String columnLength;

    /**
     * 数据库字段注释
     **/
    private String columnComment;

    /**
     * 字段默认值
     */
    private String columnDefValue;

    /**
     * *****************辅助字段********************
     */

    /**
     * 数据库字段首字母小写且去掉下划线字符串
     * 该字段不提供set方法，只提供get方法
     **/
    private String camelColumnName;

    public ColumnInfo() {
    }

    public ColumnInfo(String columnName, String columnType, String columnLength, String columnComment, String columnDefValue) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnLength = columnLength;
        this.columnComment = columnComment;
        this.columnDefValue = columnDefValue;
        this.camelColumnName = ToolkitUtil.underline2Camel(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.camelColumnName = ToolkitUtil.underline2Camel(columnName);
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnDefValue() {
        return columnDefValue;
    }

    public void setColumnDefValue(String columnDefValue) {
        this.columnDefValue = columnDefValue;
    }

    public String getCamelColumnName() {
        return camelColumnName;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnLength='" + columnLength + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", columnDefValue='" + columnDefValue + '\'' +
                ", camelColumnName='" + camelColumnName + '\'' +
                '}';
    }
}
