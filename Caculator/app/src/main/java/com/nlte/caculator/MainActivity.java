package com.nlte.caculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] numItem = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "C", "0", "=", "/"};
    private GridView mGvNumber;
    private TextView mTvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGvNumber = (GridView) findViewById(R.id.gv_number);
        mTvAnswer = (TextView) findViewById(R.id.tv_answer);

        ArrayAdapter<String> numAdapter = new ArrayAdapter<>(this, R.layout.number_item, numItem);
        mGvNumber.setAdapter(numAdapter);

        mGvNumber.setOnItemClickListener(this);
    }

    StringBuffer stringBuffer = new StringBuffer();
    float firNum = 0, secNum = 0;
    float answer = 0;
    String flag;//c存储被点击的运算符

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = numItem[position];

        switch (item) {
            case "+":
            case "-":
            case "*":
            case "/":
                if (stringBuffer.length() != 0) {
                    firNum = Integer.valueOf(stringBuffer.toString());
                }
                stringBuffer.setLength(0);
                stringBuffer.append("0");
                flag = item;
                break;
            case "C":
                stringBuffer.setLength(0);
                stringBuffer.append("0");
                firNum = 0;
                secNum = 0;
                answer = 0;
                break;
            case "=":
                if (stringBuffer.length() != 0) {
                    secNum = Integer.valueOf(stringBuffer.toString());
                }
                stringBuffer.setLength(0);

                switch (flag) {
                    case "+":
                        answer = firNum + secNum;
                        break;
                    case "-":
                        answer = firNum - secNum;
                        break;
                    case "*":
                        answer = firNum * secNum;
                        break;
                    case "/":
                        if (secNum != 0) {
                            answer = firNum / secNum;
                        } else {
                            answer = 0;
                        }
                        break;
                }
                //将结果再进行运算
                mTvAnswer.setText(String.valueOf(answer));
                firNum = answer;
                secNum = 0;
                break;
            default:
                stringBuffer.append(item);
                break;
        }
        if (stringBuffer.length() != 0) {
            //去除前导0
            mTvAnswer.setText(String.valueOf(Integer.valueOf(stringBuffer.toString())));
        }
        //System.out.println(firNum + "       " + secNum + "       " +answer);
    }
}
