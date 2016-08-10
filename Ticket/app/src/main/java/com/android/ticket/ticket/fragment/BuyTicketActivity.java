package com.android.ticket.ticket.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.ticket.R;
import com.android.ticket.bean.Train;
import com.android.ticket.utils.BuyTicketTask;

public class BuyTicketActivity extends Activity {
    private TextView from_mycity_txtView;
    private TextView trainNum_txtView;
    private TextView to_mycity_txtView;
    private Button submitBtn;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);

        final Train train = (Train) getIntent().getExtras().getSerializable("train");

        sp = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        final String username = sp.getString("username", "");


        final String trainId = String.valueOf(train.getId());
        from_mycity_txtView = (TextView) findViewById(R.id.from_mycity_txt_id);
        trainNum_txtView = (TextView) findViewById(R.id.train_num_txt_id);
        to_mycity_txtView = (TextView) findViewById(R.id.to_mycity_txt_id);
        from_mycity_txtView.setText(train.getFrom_city());
        trainNum_txtView.setText(train.getNum());
        to_mycity_txtView.setText(train.getTo_city());

        submitBtn = (Button) findViewById(R.id.submit_order_btn_id);
        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyTicketTask task = new BuyTicketTask(BuyTicketActivity.this);
                task.execute(username, train.getNum(), train.getFrom_city(), train.getTo_city());
                System.out.println("异步\n"+username+"\n"+train.getNum()+"\n"+train.getFrom_city()+"\n"+train.getTo_city());
            }
        });
    }
}
