package com.example.test_kao.presenter.shop;

import com.example.test_kao.base.BasePresenter;
import com.example.test_kao.bean.shop.CheckCarBean;
import com.example.test_kao.bean.shop.address.DefaultAddr;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.shop.ICarInfo;
import com.example.test_kao.model.shop.CarInfoModel;

public class CarInfoPresenter extends BasePresenter<ICarInfo.View> implements ICarInfo.Presenter {
    ICarInfo.View view;
    ICarInfo.Model model;

    public CarInfoPresenter(ICarInfo.View view) {
        this.view = view;
        model = new CarInfoModel();
    }

    @Override
    public void getCheckCarList(int addId, int coupId) {
        model.getCheckCarList(addId, coupId, new Callback() {
            @Override
            public void Scuess(Object o) {
                if (view != null) {
                    view.getResult((CheckCarBean) o);
                }
            }

            @Override
            public void Faile(String msg) {
                if (view != null) {
                    view.showToast(msg);
                }
            }
        });
    }

    @Override
    public void getdefaultAddress() {
        model.getdefaultAddress(new Callback() {
            @Override
            public void Scuess(Object o) {
                if (view != null) {
                    view.getResult((DefaultAddr) o);
                }
            }

            @Override
            public void Faile(String msg) {
                if (view != null) {
                    view.showToast(msg);
                }
            }
        });
    }
}
