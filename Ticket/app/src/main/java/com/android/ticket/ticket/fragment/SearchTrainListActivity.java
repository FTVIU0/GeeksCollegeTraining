package com.android.ticket.ticket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.ticket.R;
import com.android.ticket.adapter.SearchListAdapter;
import com.android.ticket.bean.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTrainListActivity extends AppCompatActivity {

    private ListView lvFindItem;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_train_list);
        initUI();
    }

    private void initUI() {

        lvFindItem = (ListView) findViewById(R.id.lvFind_item);


        final List<Train> trainlist = (List<Train>) getIntent().getExtras().getSerializable("trainlist");

        lvFindItem.setAdapter(new SearchListAdapter(this, trainlist));
        lvFindItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入购买车票
                Intent intent = new Intent(SearchTrainListActivity.this,BuyTicketActivity.class);
                intent.putExtra("train",trainlist.get(i));
                startActivity(intent);
            }
        });
    }

}
