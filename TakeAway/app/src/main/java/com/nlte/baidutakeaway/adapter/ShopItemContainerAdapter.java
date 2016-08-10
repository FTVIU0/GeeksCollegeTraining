package com.nlte.baidutakeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlte.baidutakeaway.R;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/13 0013 0:10
 */
public class ShopItemContainerAdapter extends RecyclerView.Adapter<ShopItemContainerAdapter.ShopViewHolder> {


    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ShopViewHolder extends RecyclerView.ViewHolder{

        public ShopViewHolder(View itemView) {
            super(itemView);
        }
    }
}
