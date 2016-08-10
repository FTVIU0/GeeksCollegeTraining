package com.nlte.joke.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nlte.joke.R;
import com.nlte.joke.bean.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * NLTE on 2016/8/5 0005 09:25
 */
public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> mArticles = new ArrayList<>();

    private static final int TYPE_ITEM = 0;//item view
    private static final int TYPE_FOOTER = 1;//foot view
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    //点击回调接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);

            return new FootViewHolder(footerView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            //设置数据
            itemViewHolder.tvTitle.setText(mArticles.get(position).getPost_title());
            itemViewHolder.tvPostDate.setText(mArticles.get(position).getPost_date());
            itemViewHolder.tvPostContent.setText(mArticles.get(position).getPost_content());

            //添加点击事件
            if (mOnItemClickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.tvRefresh.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.tvRefresh.setText("正在加载更多数据...");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvPostDate;
        public TextView tvPostContent;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPostDate = (TextView) itemView.findViewById(R.id.tvPostDate);
            tvPostContent = (TextView) itemView.findViewById(R.id.tvPostContent);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public TextView tvRefresh;

        public FootViewHolder(View itemView) {
            super(itemView);
            tvRefresh = (TextView) itemView.findViewById(R.id.tvRefresh);
        }
    }

    public void addItem(List<Article> articles, boolean isPullDownRefresh) {

        //如果是下拉刷新，则先把之前的数据清空
        if (isPullDownRefresh) {
            mArticles.clear();
        }
        if (articles != null && articles.size() > 0) {

            for (Article item : articles) {
                mArticles.add(item);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status 加载状态
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
