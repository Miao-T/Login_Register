package com.example.login_register.FloatView;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.login_register.BLEActivity;
import com.example.login_register.MainActivity;
import com.example.login_register.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWindowManager {

    /**
     * 小悬浮窗View的实例
     */
    private static FloatWindowSmallView smallWindow;

    /**
     * 大悬浮窗View的实例
     */
    private static FloatWindowBigView bigWindow;

    /**
     * 小悬浮窗View的参数
     */
    private static WindowManager.LayoutParams smallWindowParams;

    /**
     * 大悬浮窗View的参数
     */
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

//    /**
//     * 用于获取手机可用内存
//     */
//    private static ActivityManager mActivityManager;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context){
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if(smallWindow == null){
            smallWindow = new FloatWindowSmallView(context);
            if(smallWindowParams == null){
                smallWindowParams = new WindowManager.LayoutParams();
                if(Build.VERSION.SDK_INT >= 26){
                    smallWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }else{
                    smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }


    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (bigWindow == null) {
            bigWindow = new FloatWindowBigView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                if(Build.VERSION.SDK_INT >= 26){
                    bigWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }else{
                    bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;
            }
            windowManager.addView(bigWindow, bigWindowParams);
        }
    }


    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (bigWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigWindow);
            bigWindow = null;
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null || bigWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     *            必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


    /**
     * 更新小悬浮窗的TextView上的数据，显示时间
     *
     * @param context
     *            可传入应用程序上下文。
     */
    public static void updateTime(Context context) {
        if (smallWindow != null) {
            TextView timeView = smallWindow.findViewById(R.id.time);
            timeView.setText(MainActivity.dataBle);

        }
    }


    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return  simpleDateFormat.format((date));
    }
}
