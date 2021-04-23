package com.bing.monkey.common.util;

import com.bing.monkey.common.entity.Pager;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Log4j2
public class PageUtil {
    public static Pageable toPageable(Pager pager, String property, String sortType) {
        int page = pager.getPageNum() - 1, size = pager.getPageSize();
        Sort sort = null;
        if (StringUtils.isEmpty(property) || StringUtils.isEmpty(sortType)) {
            log.error("toPageable  构造方法参数为空");
            return null;
        }
        return getPageable(property, sortType, page, size);
    }

    @NotNull
    private static Pageable getPageable(String property, String sortType, int page, int size) {
        Sort sort = null;
        if (sortType.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, property);
        } else if (sortType.equalsIgnoreCase("asc")) {
            sort = Sort.by(Sort.Direction.ASC, property);
        } else {
            log.error("排序类型错误");
        }
        return PageRequest.of(page, size, sort);
    }

    public static Pageable toPageable(Pager pager) {
        if (null == pager) {
            pager = new Pager();
        }
        int page = pager.getPageNum() - 1, size = pager.getPageSize();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return PageRequest.of(page, size, sort);
    }

    public static Pageable newPageable() {
        Pager pager = new Pager();
        int page = pager.getPageNum() - 1, size = pager.getPageSize();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return PageRequest.of(page, size, sort);
    }

    public static Pageable newPageable(String property, String sortType) {
        Pager pager = new Pager();
        int page = pager.getPageNum() - 1, size = pager.getPageSize();
        if (StringUtils.isEmpty(property) || StringUtils.isEmpty(sortType)) {
            log.error("toPageable构造方法参数property或sortType为空");
            return null;
        }
        return getPageable(property, sortType, page, size);
    }
}
