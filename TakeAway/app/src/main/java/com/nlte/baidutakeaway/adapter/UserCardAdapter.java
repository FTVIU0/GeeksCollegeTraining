package com.nlte.baidutakeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlte.baidutakeaway.R;
import com.nlte.baidutakeaway.bean.UserCard;

import java.util.List;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/13 0013 15:47
 */
public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.CardViewHolder> {

    private List<UserCard> mUserCards;

    public UserCardAdapter(List<UserCard> userCards) {
        mUserCards = userCards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        holder.tv.setText(mUserCards.get(position).getName());
        holder.img.setImageResource(mUserCards.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return mUserCards.size();
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        public CardViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_user_card_img);
            tv = (TextView) itemView.findViewById(R.id.tv_user_card_text);
        }
    }
}
