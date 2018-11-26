package com.lyj.util;

import com.lyj.entity.URL;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/10/17 20:34
 */

//包装分页数据
public class PageEntity<T> {


    private Long totalSize;

    private List<T> content;

    private Integer pageIndex;

    private Integer pageSize;

    public PageEntity(Page<T> page) {
        this.totalSize = page.getTotalElements();
        this.content = page.getContent();
        this.pageIndex = page.getPageable().getPageNumber()+1;
        this.pageSize = page.getPageable().getPageSize();
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
