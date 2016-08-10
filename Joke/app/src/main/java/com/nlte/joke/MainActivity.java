package com.nlte.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nlte.joke.adapter.ArticlesAdapter;
import com.nlte.joke.bean.Article;
import com.nlte.joke.utils.FetchDataFromDbTask;
import com.nlte.joke.utils.FetchDataFromInternetTask;
import com.nlte.joke.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRvArticles;

    private ArticlesAdapter mArticlesAdapter;

    private int mPage = 1;//页码

    private boolean isSetAdapter = true;//判断是否设置了adapter
    private boolean isGotData = true;//判断是否获取到数据

    private boolean isPullDownRefresh;//判断是否是下拉刷新
    private int mLastVisibleItemPosition;

    FetchDataFromDbTask mFetchDataFromDbTask;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化View
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        isSetAdapter = false;
        mFetchDataFromDbTask = new FetchDataFromDbTask(MainActivity.this);

        mArticlesAdapter = new ArticlesAdapter();

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_rl);

        mRvArticles = (RecyclerView) findViewById(R.id.rvArticles);

        final RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);//设置布局管理器
        mRvArticles.setLayoutManager(linearLayoutManager);

        //设置刷新时动画的颜色，设置4个
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        //自动刷新监听
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                if (Utils.isNetworkAvailable(MainActivity.this)) {

                    getDateWithPageOnInternet(1, false);
                } else {
                    getDateOnDb();
                    mRefreshLayout.setRefreshing(false);
                    Utils.showToast(MainActivity.this, getString(R.string.network_is_unavailable));
                }
                isPullDownRefresh = false;
            }
        });

        //下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkAvailable(MainActivity.this)) {

                    getDateWithPageOnInternet(1, true);
                } else {
                    mRefreshLayout.setRefreshing(false);
                    Utils.showToast(MainActivity.this, getString(R.string.network_is_unavailable));
                }
                mPage = 1;
                isGotData = true;
                isPullDownRefresh = true;
            }
        });


        mRvArticles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                if (Utils.isNetworkAvailable(MainActivity.this)) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    if (layoutManager instanceof LinearLayoutManager) {

                        if (newState == RecyclerView.SCROLL_STATE_IDLE
                                && mLastVisibleItemPosition + 1 == adapter.getItemCount()) {

                            //刷新
                            Refreshing(adapter);
                        }
                    }
                } else {
                    adapter.notifyItemRemoved(adapter.getItemCount());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                //获取可见界面的最后一项的position
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
        });

        mArticlesAdapter.setOnItemClickListener(new ArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                String postTitle = ((TextView)view.findViewById(R.id.tvTitle)).getText().toString();
                String postDate = ((TextView)view.findViewById(R.id.tvPostDate)).getText().toString();
                String postContent = ((TextView)view.findViewById(R.id.tvPostContent)).getText().toString();

                intent.putExtra("postTitle", postTitle);
                intent.putExtra("postDate", postDate);
                intent.putExtra("postContent", postContent);

                startActivity(intent);
            }
        });

    }

    private void getDateOnDb() {
//        mArticlesAdapter = new ArticlesAdapter();
        FetchDataFromDbTask fetchDataFromDbTask = new FetchDataFromDbTask(MainActivity.this);
        List<Article> dataFromDb = fetchDataFromDbTask.getDataFromDb();

        mArticlesAdapter.addItem(dataFromDb, false);

        if (!isSetAdapter) {
            mRvArticles.setAdapter(mArticlesAdapter);
            isSetAdapter = true;
        }else {
            isSetAdapter = false;
        }
    }

    //刷新加载数据
    private void Refreshing(RecyclerView.Adapter adapter) {
        //判断是否有数据
        if (!isGotData) {
            Utils.showToast(MainActivity.this, getString(R.string.toast_content));

            adapter.notifyItemRemoved(adapter.getItemCount());
        } else {
            if (isPullDownRefresh) {
                mPage = 2;
            } else {
                mPage++;
            }
            getDateWithPageOnInternet(mPage, false);
            isPullDownRefresh = false;
        }
    }

    /**
     * 从网络获取数据
     *
     * @param page 哪一页
     */

    private void getDateWithPageOnInternet(int page, final boolean isPullDR) {

        FetchDataFromInternetTask fetchDataFromInternetTask = new FetchDataFromInternetTask();

        //获取每页的数据
        fetchDataFromInternetTask.execute(Utils.getUrl(page));

        fetchDataFromInternetTask.setOnAsyncTaskCallback(
                new FetchDataFromInternetTask.OnAsyncTaskCompleteListener() {
                    @Override
                    public void asyncFinishCallback(List<Article> articles) {
                        mArticlesAdapter.addItem(articles, isPullDR);

                        mFetchDataFromDbTask.addToDb(articles);

                        if (articles.size() != 0) {

                            if (!isSetAdapter) {
                                mRvArticles.setAdapter(mArticlesAdapter);
                                isSetAdapter = true;
                            }
                            mRefreshLayout.setRefreshing(false);
                        } else {
                            isGotData = false;
                        }
                    }
                });
    }
}
