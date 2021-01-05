package com.example.test_kao.interfaces.app;



import com.example.test_kao.bean.app.AppBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.IBaseModel;
import com.example.test_kao.interfaces.IBasePresenter;
import com.example.test_kao.interfaces.IBaseView;

public interface IApp {
    interface View extends IBaseView {
        void getAppInfoReturn(AppBean appBean);
    }

    interface Presenter extends IBasePresenter<View> {
        void getAppInfo();
    }


    interface Model extends IBaseModel {
        void getAppInfo(Callback callback);
    }
}
