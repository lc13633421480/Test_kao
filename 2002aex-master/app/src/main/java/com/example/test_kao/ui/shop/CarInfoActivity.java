package com.example.test_kao.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_kao.R;
import com.example.test_kao.adapter.shop.CarInfoAdapter;
import com.example.test_kao.app.MyApp;
import com.example.test_kao.base.BaseActivity;
import com.example.test_kao.bean.shop.CheckCarBean;
import com.example.test_kao.bean.shop.ShopBean;
import com.example.test_kao.bean.shop.address.DefaultAddr;
import com.example.test_kao.interfaces.shop.ICarInfo;
import com.example.test_kao.presenter.shop.CarInfoPresenter;

import java.util.List;

import butterknife.BindView;

public class CarInfoActivity extends BaseActivity<CarInfoPresenter> implements ICarInfo.View {
    @BindView(R.id.rlv_CarInfo)
    RecyclerView rlvCarInfo;
    @BindView(R.id.add_name)
    TextView addName;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.add_right)
    ImageView addRight;
    @BindView(R.id.favourable_right)
    ImageView favourableRight;
    @BindView(R.id.allPrice)
    TextView allPrice;
    @BindView(R.id.payment)
    TextView payment;
    private List<ShopBean.DataBean.CartListBean> listss;

    @Override
    protected int getLayout() {
        return R.layout.activity_car_info;
    }

    @Override
    protected CarInfoPresenter createPrenter() {
        return new CarInfoPresenter(this);
    }

    @Override
    protected void initView() {
        listss = (List<ShopBean.DataBean.CartListBean>) MyApp.getMap().get("lists");
        //跳转到收货地址的列表
        addRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarInfoActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getCheckCarList(1, 1);
    }

    @Override
    public void getResult(CheckCarBean checkCarBean) {
        List<CheckCarBean.DataBean.CheckedGoodsListBean> checkedGoodsList =
                checkCarBean.getData().getCheckedGoodsList();
        rlvCarInfo.setLayoutManager(new LinearLayoutManager(this));
        rlvCarInfo.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        CarInfoAdapter carInfoAdapter = new CarInfoAdapter(this, listss);
        rlvCarInfo.setAdapter(carInfoAdapter);
        int sum = 0;
        for (int i = 0; i < listss.size(); i++) {
            double price = (int)listss.get(i).getRetail_price();
            int number = listss.get(i).getNumber();
            sum += price * number;
        }
        allPrice.setText("￥" + sum);
        payment.setText("实付：￥"+sum);
        carInfoAdapter.notifyDataSetChanged();

    }

    @Override
    public void getResult(DefaultAddr defaultAddr) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listss.clear();
    }
}
