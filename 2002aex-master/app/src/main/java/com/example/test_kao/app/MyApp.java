package com.example.test_kao.app;

import android.app.Application;

import com.example.test_kao.utils.SpUtils;
import com.live.app.MyApplication;

import java.util.HashMap;

public class MyApp extends Application {
    private static String[] modules = {"com.live.app.MyApplication"};

    public static MyApp app;
    private static HashMap<String, Object> map;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        SpUtils.getInstance().setValue("image",true);
        map = new HashMap<>();
        getMap();
        initMoudles();
    }

    public static HashMap<String, Object> getMap() {
        return map;
    }
    private void initMoudles() {
        for (String moduleImpl : modules){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof MyApplication){
                    ((MyApplication) obj).initApp(app);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }


}

