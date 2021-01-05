package com.example.test_kao;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.test_kao.ui.home.HomeFrag;
import com.example.test_kao.ui.me.MeFrag;
import com.example.test_kao.ui.shop.ShopFrag;
import com.example.test_kao.ui.sort.SortFrag;
import com.example.test_kao.ui.topic.TopicFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView nav_view;
    private ViewPager viewPager;
    private ArrayList<Fragment> flist;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_topic, R.id.navigation_sort, R.id.navigation_shop, R.id.navigation_me)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(nav_view, navController);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),flist);
        viewPager.setAdapter(myFragmentPagerAdapter);

        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        menuItem.setIcon(R.mipmap.ic_menu_choice_pressed);
                        break;
                    case R.id.navigation_topic:
                        menuItem.setIcon(R.mipmap.ic_menu_topic_pressed);
                        break;
                    case R.id.navigation_sort:
                        menuItem.setIcon(R.mipmap.ic_menu_sort_pressed);
                        break;
                    case R.id.navigation_shop:
                        menuItem.setIcon(R.mipmap.ic_menu_shopping_pressed);

                        break;
                    case R.id.navigation_me:
                        menuItem.setIcon(R.mipmap.ic_menu_me_pressed);
                        break;
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nav_view.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        nav_view = (BottomNavigationView) findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPager);
        initFragment();
    }

    private void initFragment() {
        flist = new ArrayList<>();
        flist.add(new HomeFrag());
        flist.add(new TopicFrag());
        flist.add(new SortFrag());
        flist.add(new ShopFrag());
        flist.add(new MeFrag());
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> flist;
        public MyFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.flist = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return flist.get(position);
        }

        @Override
        public int getCount() {
            return flist.size();
        }
    }
}