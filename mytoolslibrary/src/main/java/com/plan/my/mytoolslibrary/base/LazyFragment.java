package com.plan.my.mytoolslibrary.base;

/**
 * Fragment延迟加载的抽象
 * 
 *  滑动预加载的时候的顺序是 quick_Load======onCreateView======lazyLoad 
 * 
 *  直接点击加载的时候的顺序是 quick_Load======lazyLoad======onCreateView  
 *  
 *  界面只要切走就会再次执行quick_Load
 *  
 *  不要问我怎么知道的，实际测试结果！！！
 * @author wudl
 *
 *2015年7月17日下午4:09:14
 */
public abstract class LazyFragment extends BaseFragment{
	protected boolean is_Visible;

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
	
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
        	is_Visible = true;
            onVisible();

        } else {
        	is_Visible = false;
            onInvisible();

        }

    }

    protected void onVisible(){
        lazyLoad();

    }

    protected void onInvisible(){
        quick_Load();

    }
    
    /**
     * 延时加载的东西放在这里
     */
    protected abstract void lazyLoad();
    
    /**
     * 直接加载的东西放在这里
     */
    protected abstract void quick_Load();
}
