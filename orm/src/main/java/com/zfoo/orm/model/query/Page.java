package com.zfoo.orm.model.query;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-15 11:30
 */
public class Page {

    private int page;// 分页数
    private int size;// 页容量

    public int firstResult() {
        return size * page - size;
    }

    private Page() {
    }

    public static Page valueOf(int page, int size) {
        if (page <= 0) {
            FormattingTuple message = MessageFormatter.format("分页数必须大于0，[page:{}]", page);
            throw new IllegalArgumentException(message.getMessage());
        }
        if (size <= 0) {
            FormattingTuple message = MessageFormatter.format("页容量必须大于0，[size:{}]", size);
            throw new IllegalArgumentException(message.getMessage());
        }
        Page p = new Page();
        p.setPage(page);
        p.setSize(size);
        return p;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
