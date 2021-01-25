package com.bing.haagendzs.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页对象")
public class Pager {

    @ApiModelProperty("是否分页")
    private boolean isPage = true;

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean page) {
        isPage = page;
    }

    @ApiModelProperty("页码")
    private Integer pageNum = 1;
    @ApiModelProperty("每页行数")
    private Integer pageSize = 15;

    public Pager() {
    }

    public Integer getPageNum() {
        if (this.pageNum <= 0) {
            this.pageNum = 1;
        }

        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (this.pageSize <= 0) {
            this.pageSize = 15;
        }

        if (this.pageSize > 1000) {
            this.pageSize = 1000;
        }

        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "isPage=" + isPage +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
