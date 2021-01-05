package com.example.test_kao.presenter.app;


import com.example.test_kao.base.BasePresenter;
import com.example.test_kao.bean.app.AppBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.app.IApp;
import com.example.test_kao.model.app.AppModel;


public class AppPresenter extends BasePresenter<IApp.View> implements IApp.Presenter {

    IApp.Model model;
    IApp.View view;
    public AppPresenter(IApp.View view){
        this.view = view;
        model = new AppModel();
    }

    @Override
    public void getAppInfo() {
        model.getAppInfo(new Callback<AppBean>() {
            @Override
            public void Scuess(AppBean appBean) {
                if(mView != null){
                    mView.getAppInfoReturn(appBean);
                }
            }

            @Override
            public void Faile(String msg) {

            }
        });
    }
}
