package com.nlte.baidutakeaway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.nlte.baidutakeaway.adapter.ContainerPagerAdapter;
import com.nlte.baidutakeaway.bean.Tab;
import com.nlte.baidutakeaway.fragment.EatFragment;
import com.nlte.baidutakeaway.fragment.HomeFragment;
import com.nlte.baidutakeaway.fragment.OrderFragment;
import com.nlte.baidutakeaway.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener,
        ViewPager.OnPageChangeListener {

    private ViewPager mVpContainer;
    private FragmentTabHost mTabHost;

    private List<Tab> mTabs = new ArrayList<>(4);

    private List<Fragment> mFragmentList = new ArrayList<>(4);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面
        initView();
    }

    private void initView() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mVpContainer = (ViewPager) findViewById(R.id.vp_container);

        //初始化底部菜单栏
        initTab();

        //初始化viewpager
        initPager();
    }

    /**
     * 给Viewpager初始化
     */
    private void initPager() {

        mVpContainer.addOnPageChangeListener(this);

        HomeFragment homeFragment = new HomeFragment();
        EatFragment eatFragment = new EatFragment();
        OrderFragment orderFragment = new OrderFragment();
        UserFragment userFragment = new UserFragment();

        mFragmentList.add(homeFragment);
        mFragmentList.add(eatFragment);
        mFragmentList.add(orderFragment);
        mFragmentList.add(userFragment);

        mVpContainer.setAdapter(new ContainerPagerAdapter(getSupportFragmentManager(), mFragmentList));

    }


    /**
     * 初始化底部菜单项
     */
    private void initTab() {

        mTabHost.setup(this, getSupportFragmentManager(), R.id.vp_container);
        mTabHost.setOnTabChangedListener(this);

        Tab tabHome = new Tab(R.string.home, R.drawable.selector_home, HomeFragment.class);
        Tab tabEat = new Tab(R.string.eat, R.drawable.selector_eat, EatFragment.class);
        Tab tabOrder = new Tab(R.string.order, R.drawable.selector_order, OrderFragment.class);
        Tab tabUser = new Tab(R.string.user, R.drawable.selector_user, UserFragment.class);

        mTabs.add(tabHome);
        mTabs.add(tabEat);
        mTabs.add(tabOrder);
        mTabs.add(tabUser);

        //去除分割线
        //ps:必须在添加完子菜单后才能起作用
        mTabHost.getTabWidget().setDividerDrawable(null);


        int i = 0;
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));

            tabSpec.setIndicator(buildIndicator(tab));

            mTabHost.addTab(tabSpec, tab.getFragment(), null);

            mTabHost.setTag(i++);
        }
    }

    private View buildIndicator(Tab tab) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.tv_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view;
    }

    @Override
    public void onTabChanged(String s) {
        int position = mTabHost.getCurrentTab();
        mVpContainer.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget tabWidget = mTabHost.getTabWidget();
        int oldFocusability = tabWidget.getDescendantFocusability();
        tabWidget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(position);
        tabWidget.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
