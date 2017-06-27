package com.plan.my.mytoolslibrary.base;

import android.support.v7.app.AppCompatActivity;

import com.plan.my.mytoolslibrary.R;

import java.util.Random;

/**
 * Activity基类
 * Created by wudl on 2016/9/8 16:33
 * <p/>
 * 邮箱 770616344@qq.com
 */
public class BaseActivity extends AppCompatActivity {

    /*界面show,finish时的动画*/
    public enum OutStyle{
        /*左进右出*/
        LEFT_IN_RIGHT_OUT,
        /*右进左出*/
        RIGHT_IN_LEFT_OUT,
        /*旋转*/
        ROTATE,
        /*从上向下关闭页面*/
        PUSH_DOWN_OUT,
        /*从下向上打开页面*/
        PUSH_DOWN_IN,
        /*随机*/
        RANDOM
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onFinishPressed(OutStyle.RANDOM);
    }

    public void onFinishPressed(OutStyle outstyle) {
        switch (outstyle){
            case LEFT_IN_RIGHT_OUT:
                overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
                break;
            case RIGHT_IN_LEFT_OUT:
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                break;
            case ROTATE:
                overridePendingTransition(R.anim.view_rotate, R.anim.view_rotate);
                break;
            case RANDOM:
                onRandomStyle();
                break;
        }

    }

    private void onRandomStyle(){
        Random ran = new Random();
        int ii = ran.nextInt(10);
        if(ii<5){
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
        }else if(ii<10){
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        }else{
            overridePendingTransition(R.anim.view_rotate, R.anim.view_rotate);//先留着

        }
    }
    /**
     * 界面弹出切换动画
     */
    public void onStartPending(OutStyle outstyle) {
        switch (outstyle){
            case LEFT_IN_RIGHT_OUT:
                overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
                break;
            case RIGHT_IN_LEFT_OUT:
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                break;
            case ROTATE:
                overridePendingTransition(R.anim.view_rotate, R.anim.view_rotate);
                break;
            case RANDOM:
                onRandomStyle();
                break;
        }
    }


    /**从上向下关闭页面**/
    public void onFinishDown() {

        /**
         * int enterAnim, int exitAnim
         */
        overridePendingTransition(0, R.anim.push_down_out);

    }

    /**从下向上打开页面**/
    public void onStartUp() {

        /**
         * int enterAnim, int exitAnim
         */
        overridePendingTransition(R.anim.push_down_in, 0);

    }
}
