package com.liujianjian.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.knowin.iot.IControlDeviceAidlInterface;
import com.knowin.iot.IControlDeviceCompleteAidlInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FragmentManager mFragmentManager;
    private RoomFragmentAdapter mRoomAdapter;
    private List<String> tabTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    public static String TAG = MainActivity.class.getSimpleName();

    private IControlDeviceAidlInterface controlDeviceAidlInterface = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected="+service);
            controlDeviceAidlInterface = IControlDeviceAidlInterface.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected.controlDeviceAidlInterface="+controlDeviceAidlInterface);

            try {
                controlDeviceAidlInterface.registerControlDeviceListener(new IControlDeviceCompleteAidlInterface.Stub() {
                    @Override
                    public void onControlDeviceComplete(String category, boolean open, int causeCode) throws RemoteException {
                        Log.e(TAG, "onControlDeviceComplete.category="+category+";open="+open+";causeCode="+causeCode);
                    }
                });
            } catch (RemoteException e) {
                Log.e(TAG, "Exception2="+e.getMessage());
            }

            try {
                controlDeviceAidlInterface.controlRoomDevice("light", false);
            } catch (RemoteException e) {
                Log.e(TAG, "Exception="+e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected="+name);
            controlDeviceAidlInterface = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        LinearLayout linearLayout ;

        /*((TextView)findViewById(R.id.second)).setText("MainActivity");
        findViewById(R.id.second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAidl();

                //getRoomList(getApplicationContext());
                //startActivity(new Intent(MainActivity.this, MainActivity2.class));

                //controlDeviceByContentProvider(getApplicationContext(), "0xA4C138542F752D36", "on", true);
            }
        });*/

        handler.sendEmptyMessage(0);
        Looper.getMainLooper().getThread();
        Looper.myLooper();

        ThreadLocal threadLocal = new ThreadLocal();

        //getRoomList(getApplicationContext());

        String insightDeviceId = getIOTDeviceId();
        Log.d(TAG, "onCreate.insightDeviceId="+insightDeviceId);

        getDeviceSpec(getApplicationContext(), "urn:knowin-spec:device:lightbulb:00000005:know:kldq02:1");

        tabLayout = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.tab_pager);

        mFragmentManager = getSupportFragmentManager();

        tabTitleList.add("首页");
        tabTitleList.add("其他");

        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(OtherFragment.newInstance());

        if (mRoomAdapter == null) {
            mRoomAdapter = new RoomFragmentAdapter(mFragmentManager);
            viewPager.setAdapter(mRoomAdapter);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            mRoomAdapter.notifyDataSetChanged();
        }
    }

    private class RoomFragmentAdapter extends FragmentPagerAdapter {

        public RoomFragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(@NonNull @NotNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public long getItemId(int position) {
            if (mFragmentList != null && position < mFragmentList.size()) {
                return mFragmentList.get(position).hashCode();
            }
            return position;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String tabTitle = "";
            if (tabTitleList != null && position < tabTitleList.size()) {
                tabTitle = tabTitleList.get(position);
            }

            return tabTitle;
        }
    }

    public void testAidl() {
        Intent intent = new Intent();
        intent.setPackage("com.knowin.launcher");
        intent.setAction("ControlDeviceAidlService");
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    /**
     *
     * @param context
     * @param category light/window-covering/air-conditioner
     * @return
     */
    private String getRoomList(Context context,String category) {
        Log.d(TAG, "getRoomList");
        String token = "";
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://com.knowin.launcher.provider/room_device_list");
            cursor = context.getContentResolver().query(uri, null, category, null, null);
            Log.d(TAG, "getRoomList.cursor="+cursor);
            if (cursor == null) {
                return token;
            }
            while (cursor.moveToNext()) {
                token = cursor.getString(cursor.getColumnIndex("room_device_list"));
                Log.d(TAG, "getRoomList.cursor.token="+token);
            }

            Log.d(TAG, "getRoomList="+token);


        } catch (Exception e) {
            Log.d(TAG, "getRoomList.Exception="+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return token;
    }

    /**
     *
     * @param context
     * @param urn
     * @return
     */
    private String getDeviceSpec(Context context,String urn) {
        Log.d(TAG, "getDeviceSpec");
        String deviceSpecJson = "";
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://com.knowin.launcher.provider/device_spec");
            cursor = context.getContentResolver().query(uri, null, urn, null, null);
            Log.d(TAG, "getDeviceSpec.cursor="+cursor);
            if (cursor == null) {
                return deviceSpecJson;
            }
            while (cursor.moveToNext()) {
                deviceSpecJson = cursor.getString(cursor.getColumnIndex("device_spec"));
                Log.d(TAG, "getDeviceSpec.cursor.specJson="+deviceSpecJson);
            }

            Log.d(TAG, "getDeviceSpec="+deviceSpecJson);
        } catch (Exception e) {
            Log.d(TAG, "getDeviceSpec.Exception="+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return deviceSpecJson;
    }

    private String getDeviceShadow(Context context,String deviceId) {
        Log.d(TAG, "getDeviceShadow");
        String deviceShadowJson = "";
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://com.knowin.launcher.provider/device_shadow");
            cursor = context.getContentResolver().query(uri, null, deviceId, null, null);
            Log.d(TAG, "getDeviceShadow.cursor="+cursor);
            if (cursor == null) {
                return deviceShadowJson;
            }
            while (cursor.moveToNext()) {
                deviceShadowJson = cursor.getString(cursor.getColumnIndex("device_shadow"));
                Log.d(TAG, "getDeviceShadow.cursor.specJson="+deviceShadowJson);
            }

            Log.d(TAG, "getDeviceShadow="+deviceShadowJson);
        } catch (Exception e) {
            Log.d(TAG, "getDeviceShadow.Exception="+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return deviceShadowJson;
    }

    private String controlDeviceByContentProvider(Context context, String deviceId, String propertyName, boolean propertyValue) {
        Log.d(TAG, "controlDeviceByContentProvider");
        String deviceSpecJson = "";
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://com.knowin.launcher.provider/control_device");
            ContentValues contentValues = new ContentValues();
            contentValues.put("param_device_id", deviceId);
            contentValues.put("param_property_name", propertyName);
            contentValues.put("param_property_value", propertyValue);
            context.getContentResolver().insert(uri, contentValues);
        } catch (Exception e) {
            Log.d(TAG, "controlDeviceByContentProvider.Exception="+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return deviceSpecJson;
    }

    public String getIOTDeviceId() {
        String device_id = "";
        Cursor cursor = null;
        Log.d(TAG, "getIOTDeviceId");
        try {
            Uri uri = Uri.parse("content://com.knowin.launcher.provider/device_id");
            cursor = getContentResolver().query(uri, null, null, null, null);
            Log.d(TAG, "getIOTDeviceId.cursor="+cursor);
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    device_id = cursor.getString(cursor.getColumnIndex("device_id"));
                    Log.d(TAG, "getIOTDeviceId.device_id="+device_id);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "getIOTDeviceId.Exception="+e.getMessage());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return device_id;
    }
}