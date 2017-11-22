package ruolan.com.cntaxi;

import android.app.Application;

/**
 * Created by wuyinlei on 2017/11/20.
 *
 *
 * @function
 */

public class CNApplication extends Application {


    private static CNApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    //获取一个实例
    public static CNApplication getInstance() {
        return INSTANCE;
    }
}
