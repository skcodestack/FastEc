package com.skcodestack.stack.ui.refresh;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class PageBean {
    private int mCurrentPage = 0;
    private int mTotal = 0;
    private int mPageSize = 0;

    private int mCurrentCount = 0;
    private int mDelayed = 0;


    public int getCurrentPage() {
        return mCurrentPage;
    }

    public PageBean setCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
        return this;
    }

    public int getTotal() {
        return mTotal;
    }

    public PageBean setTotal(int mTotal) {
        this.mTotal = mTotal;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PageBean setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PageBean setCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PageBean setDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this;
    }

    public PageBean addIndex() {
        mCurrentPage++;
        return this;
    }
}
