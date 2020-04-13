package com.example.login_register;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.login_register.Fragments.MainFragment;
import com.example.login_register.Fragments.MineFragment;
import com.example.login_register.Service.FloatWindowService;
import com.example.login_register.Utils.ActivityCollector;
import com.example.login_register.Utils.BaseActivity;
import com.example.login_register.Utils.DoubleClickToExitUtil;
import com.example.login_register.Utils.ToastUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends BaseActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String TAG = "DB_tag";
    private BottomBar mBottomBar;
    private long mExitTime;
    private MainFragment mainFragment;
    private MineFragment mineFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        String LoginName = mSharedPreferences.getString("RememberName","");
        Log.d(TAG,"activity" + LoginName);

        /**
         * activity-fragment传数据
        * */
//        Bundle bundle = new Bundle();
//        bundle.putString("LoginName", LoginName);
//        //首先有一个Fragment对象 调用这个对象的setArguments(bundle)传递数据
//        mineFragment.setArguments(bundle);

        //        Bundle bundle = getArguments();
//        LoginName = bundle.getString("LoginName");
//        Log.d(TAG,"fragment" + LoginName);

        mainFragment = new MainFragment();
        mineFragment = new MineFragment();
        mBottomBar = findViewById(R.id.bottomBar);

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Object object = null;
                switch (tabId){
                    case R.id.tab1:
                        object = mainFragment;
                        ToastUtil.showMsg(MainActivity.this,"tag1");
                        break;
                    case R.id.tab2:
                        object = mineFragment;
                        ToastUtil.showMsg(MainActivity.this,"tag2");
                        break;
                    case R.id.tab3:
                        ToastUtil.showMsg(MainActivity.this,"tag3");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,(Fragment)object).commit();
            }
        });

    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer,fragment);
        transaction.commit();
    }

    private void hideFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer,fragment);
        transaction.hide(fragment);
    }

    private void switchFragment(Fragment target){
        if(mCurrentFragment == target)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mCurrentFragment != null){
            transaction.hide(mCurrentFragment);
        }
        if(target.isAdded()){
            transaction.show(target);
        }else{
            transaction.add(R.id.contentContainer,target,target.getClass().getName());
        }
        transaction.commit();
        mCurrentFragment = target;
    }

//    @Override
//    public void onBackPressed() {
//        if(mCurrentFragment != mainFragment){
//            mBottomBar.selectTabWithId(R.id.tab1);
//            return;
//        }
//        moveTaskToBack(true);
//        super.onBackPressed();
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isLongPress()) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
