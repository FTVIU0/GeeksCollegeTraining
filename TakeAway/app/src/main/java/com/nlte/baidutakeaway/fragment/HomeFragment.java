package com.nlte.baidutakeaway.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.nlte.baidutakeaway.R;
import com.nlte.baidutakeaway.adapter.HomeMenuAdapter;
import com.nlte.baidutakeaway.adapter.ShopItemContainerAdapter;
import com.nlte.baidutakeaway.bean.UserCard;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SliderLayout sliderShow;
    private RecyclerView mRvHomeMenu;;
    private List<UserCard> mUserCards = new ArrayList<>();

    private RecyclerView rvShopItemContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderShow = (SliderLayout) view.findViewById(R.id.slider_image);



        mRvHomeMenu = (RecyclerView) view.findViewById(R.id.rv_home_menu);
        mRvHomeMenu.setLayoutManager(new GridLayoutManager(getActivity(),2,
                OrientationHelper.HORIZONTAL, false));
        initHomeMenuDate();
        mRvHomeMenu.setAdapter(new HomeMenuAdapter(mUserCards));



        rvShopItemContainer = (RecyclerView) view.findViewById(R.id.rv_shop_item_container);
        rvShopItemContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvShopItemContainer.setAdapter(new ShopItemContainerAdapter());

        //初始化首页的滚动区域
        initSliderShow();


        return view;
    }

    @Override
    public void onDestroyView() {
        sliderShow.stopAutoCycle();
        super.onDestroyView();
    }
    private void initSliderShow() {
        DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
        defaultSliderView.image(R.mipmap.slider);

        sliderShow.addSlider(defaultSliderView);

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        sliderShow.setDuration(3000);
    }

    private void initHomeMenuDate(){
        int[] img = new int[]{};
        String[] names = getResources().getStringArray(R.array.home_menu);

        for (int i = 0; i < names.length; i++) {
            UserCard card = new UserCard(R.mipmap.home_defualt, names[i]);
            mUserCards.add(card);
        }
    }



}
