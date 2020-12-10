package com.zmh.coupon.util.reslut;

import java.util.ArrayList;

/**
 * Created by liaoxiaoli on 2018/5/25.
 */
public class PageResult {
    private long totalCount;
    private int pageSize;
    private Object data;

    public PageResult(int pageSize, long totalCount) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = new ArrayList<Object>();
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPage() {
        if (totalCount == 0) {
            return 0L;
        }
        return totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Object getData() {
        return data;
    }

    public PageResult setData(Object data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        return this;
    }
}
