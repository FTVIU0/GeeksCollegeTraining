package com.nlte.numcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] nums = new String[20];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i+1+"";
        }


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        GridView gridView = new GridView(this);
        gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        gridView.setNumColumns(4);
        gridView.setHorizontalSpacing(10);
        gridView.setVerticalSpacing(10);

        linearLayout.addView(gridView);
        setContentView(linearLayout);


        ArrayAdapter<String> numAdapter = new ArrayAdapter<>(this,
                R.layout.item_num, nums);

        gridView.setAdapter(numAdapter);
    }
}
