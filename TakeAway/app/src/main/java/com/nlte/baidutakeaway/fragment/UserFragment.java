package com.nlte.baidutakeaway.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlte.baidutakeaway.R;
import com.nlte.baidutakeaway.adapter.UserCardAdapter;
import com.nlte.baidutakeaway.bean.UserCard;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    private RecyclerView mRvUserCard;
    private List<UserCard> mUserCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mUserCards = new ArrayList<>();

        //实现recycleView
        mRvUserCard = (RecyclerView) view.findViewById(R.id.rv_user_card);
        mRvUserCard.setLayoutManager(new GridLayoutManager(getActivity(),
                3, OrientationHelper.VERTICAL, false));//类似GridView
        initUserCard();
        mRvUserCard.setAdapter(new UserCardAdapter(mUserCards));


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUserCards.clear();
    }


    private void initUserCard(){
        int[] images = new int[]{};
        String[] names = getResources().getStringArray(R.array.userCard);

        for (int i = 0; i < names.length; i++) {
            UserCard card = new UserCard(R.mipmap.mypage_list_icon_location, names[i]);
            mUserCards.add(card);
        }
    }

}
