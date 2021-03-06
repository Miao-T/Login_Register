package com.example.login_register.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.login_register.AlterUserInfoActivity;
import com.example.login_register.Calorie.CountCalorie;
import com.example.login_register.CloudSQL.DBConnection;
import com.example.login_register.DeviceActivity;
import com.example.login_register.LoginActivity;
import com.example.login_register.R;
import com.example.login_register.RegisterUserInfoActivity;
import com.example.login_register.SettingActivity;
import com.example.login_register.Utils.ActivityCollector;
import com.example.login_register.Utils.ReadData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;

import static android.content.Context.MODE_PRIVATE;

public class MineFragment extends Fragment implements View.OnClickListener {
    private TextView mTvMine,mTvStep,mTvTotalDay;
    private Button mBtnMyInfo,mBtnDeviceInfo,mBtnSetting,mBtnOffline;
    private static final String TAG = "DB_tag" ;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String loginName,registerDate,nowDate;
    private int date;

//    private int userId,sexInt,height,targetStep;
//    private double weight;
//    private String sex,registerTime,phoneNumber,birthday,location;

    //fragment loginName,registerTime,targetStep
    //alterActivity sex,height,weight,birthday,location

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSharedPreferences = getActivity().getSharedPreferences("User",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        loginName = mSharedPreferences.getString("RememberName","");
        registerDate = mSharedPreferences.getString("registerData","") + " 00:00:00";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        nowDate = simpleDateFormat.format(date);
        Long expendTime = CountCalorie.getTimeExpend(registerDate,nowDate);
        long longDay = expendTime/(24*3600*1000);
//        date = Math.ceil((Date.parse(nowDate)-Date.parse(registerDate))/(24*3600*1000));
        Log.d(TAG,"fragment" + loginName);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DBConnection.DriverConnection();
//                ReadData readData = new DBConnection();
//                EnumMap<ReadData.UserInfoData,Object> userInfo = readData.ReadCloudData(loginName,"");
//                userInfo.entrySet().iterator();
//                registerTime = String.valueOf(userInfo.get(ReadData.UserInfoData.registerTime));
//                targetStep = Integer.parseInt(String.valueOf(userInfo.get(ReadData.UserInfoData.targetStep)));
//
////                sexInt = Integer.parseInt(String.valueOf(userInfo.get(ReadData.UserInfoData.sex)));
////                if(sexInt == 1){sex = "女";}else{sex = "男";}
////                height = Integer.parseInt(String.valueOf(userInfo.get(ReadData.UserInfoData.height)));
////                weight = Double.parseDouble(String.valueOf(userInfo.get(ReadData.UserInfoData.weight)));
////                birthday = String.valueOf(userInfo.get(ReadData.UserInfoData.birthday));
////                location = String.valueOf(userInfo.get(ReadData.UserInfoData.location));
//            }
//        }).start();


        mTvMine = view.findViewById(R.id.tv_userName);
        mTvStep = view.findViewById(R.id.tv_step);
        mTvTotalDay = view.findViewById(R.id.tv_totalDay);
        mBtnMyInfo = view.findViewById(R.id.btn_myInfo);
        mBtnDeviceInfo = view.findViewById(R.id.btn_deviceInfo);
        mBtnSetting = view.findViewById(R.id.btn_setting);
        mBtnOffline = view.findViewById(R.id.btn_offline);

        mBtnMyInfo.setOnClickListener(this);
        mBtnDeviceInfo.setOnClickListener(this);
        mBtnSetting.setOnClickListener(this);
        mBtnOffline.setOnClickListener(this);

        mTvMine.setText(loginName);
        mTvTotalDay.setText("Sloth已陪伴您" + longDay + "天啦");
        //mTvTotalDay.setText("您已使用" + "i" + "天，达标" + "j" + "天");
        //mTvStep.setText("目标步数" + targetStep);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_myInfo:
                intent = new Intent(getActivity(), RegisterUserInfoActivity.class);
                intent.putExtra("flag","2");
                startActivity(intent);
                break;
            case R.id.btn_deviceInfo:
                intent = new Intent(getActivity(), DeviceActivity.class);
                startActivity(intent);
                break;
            case  R.id.btn_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_offline:
//                intent = new Intent("com.example.login.FORCE_OFFLINE");
//                getActivity().sendBroadcast(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定要退出账号嘛？");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                        mEditor.clear();
                        mEditor.apply();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
        }
    }
}
