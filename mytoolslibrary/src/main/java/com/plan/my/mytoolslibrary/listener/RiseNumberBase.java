package com.plan.my.mytoolslibrary.listener;

import com.plan.my.mytoolslibrary.view.RiseNumberTextView;

/**
 * Created by wudl on 2016/9/12 16:16
 * <p/>
 * 邮箱 770616344@qq.com
 */
public interface RiseNumberBase {

    public void start();
    public RiseNumberTextView withNumber(float number);
    public RiseNumberTextView withNumber(int number);
    public RiseNumberTextView setDuration(long duration);
    public void setOnEnd(RiseNumberTextView.EndListener callback);

}
