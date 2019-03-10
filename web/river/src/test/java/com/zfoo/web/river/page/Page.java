package com.zfoo.web.river.page;

import com.zfoo.util.AssertionUtils;

import java.util.List;

public class Page {

    // 总数量
    private int totalNum;

    // 每页大小
    private int pageSize;

    // 当前页码
    private int currentPage;

    // 当前页的元素
    private List<?> currentPageList;

    /**
     * @param list        需要分页的集合，不能为空
     * @param currentPage 需要展示的页，必须大于等于1
     * @param pageSize    每页的大小，必须大于等于1
     * @return 分页结果
     */
    public static Page valueOf(List<?> list, int currentPage, int pageSize) {
        AssertionUtils.notEmpty(list);

        AssertionUtils.ge1(currentPage);
        AssertionUtils.ge1(pageSize);

        Page page = new Page();
        page.totalNum = list.size();
        page.pageSize = pageSize;

        // 当前页合法检查
        int totalPage = page.totalPage();
        AssertionUtils.le(currentPage, totalPage, "max page is [{}], but curret page is [{}]", currentPage, totalPage);
        page.currentPage = currentPage;

        // 实际上endIndex应该减1才是真正的结束索引，但是
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = (totalPage == currentPage) ? (startIndex + page.totalNum - (totalPage - 1) * pageSize) : (startIndex + pageSize);
        page.currentPageList = list.subList(startIndex, endIndex);
        return page;
    }

    // 获取总页数
    public int totalPage() {
        return (totalNum % pageSize == 0) ? (totalNum / pageSize) : (totalNum / pageSize + 1);
    }

    public int getTotalNum() {
        return totalNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<?> getCurrentPageList() {
        return currentPageList;
    }
}
