package com.nlte.playmusicandlrc;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nlte.playmusicandlrc.bean.LrcContent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mMediaPlayer;
    private TextView tvLrcShow;
    private List<LrcContent> mLrcContentList;
    private Button btnStart;
    private Button btnStop;

    //更新界面UI
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("msg" + msg.what);
            tvLrcShow.setText(mLrcContentList.get(msg.what).getContent());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLrcShow = (TextView) findViewById(R.id.tvLrcShow);

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getLrcContent();

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.chuxuezhe);
        }

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);


        //设置监听，当歌曲播放完成后，重新播放
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (!mMediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            //释放资源
            mMediaPlayer.release();
            mMediaPlayer = null;

        }
    }

    /**
     * 获取LRC的内容
     */
    private void getLrcContent() {
        mLrcContentList = new ArrayList<>();

        //创建输入流
        InputStream inputStream = getResources().openRawResource(R.raw.chuxuezhe_lrc);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = "";
        try {
            while ((str = bufferedReader.readLine()) != null) {
                LrcContent lrcContent = new LrcContent();
                str = str.replace("[", "");

                //分离时间和内容
                String splitLrcData[] = str.split("]");

                if (splitLrcData.length > 1) {
                    lrcContent.setContent(splitLrcData[1]);
                    lrcContent.setTime(getTime(splitLrcData[0]));

                    mLrcContentList.add(lrcContent);
                }
            }

            //关闭流
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将歌词文件的时间换算为毫秒
     *
     * @param time 歌词文件中的时间
     * @return 换算后的时间
     */
    private int getTime(String time) {
        //分离出分、秒、毫秒
        time = time.replace(".", ":");
        String[] splitTime = time.split(":");

        int minute = Integer.parseInt(splitTime[0]);//分钟
        int second = Integer.parseInt(splitTime[1]);//秒  1秒 = 100分秒
        int minutesecond = Integer.parseInt(splitTime[2]);//分秒

        //换算成毫秒
        int currentTime = (minute * 60 + second) * 1000 + minutesecond * 10;

        return currentTime;
    }

    /**
     * 根据时间获取歌词显示的索引值
     *
     * @return
     */
    public void playLrc() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mMediaPlayer != null) {
                    while (mMediaPlayer.isPlaying()) {
                        int index = getIndex();
                        Message message = new Message();
                        message.what = index;
                        mHandler.sendMessage(message);
                    }
                }
            }
        }.start();
    }

    private int getIndex() {
        int currentTime = mMediaPlayer.getCurrentPosition();
        int duration = mMediaPlayer.getDuration();
        int index = 0;

        if (currentTime < duration) {
            for (int i = 0; i < mLrcContentList.size(); i++) {
                if (i < mLrcContentList.size() - 1) {
                    if (currentTime < mLrcContentList.get(i).getTime() && i == 0) {
                        index = 0;
                    }
                    if (currentTime > mLrcContentList.get(i).getTime()
                            && currentTime < mLrcContentList.get(i + 1).getTime()) {
                        index = i;
                    }
                }
                if (i == mLrcContentList.size() - 1
                        && currentTime > mLrcContentList.get(i).getTime()) {
                    index = mLrcContentList.size() - 1;
                }
            }
        }
        return index;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                    playLrc();
                }
                break;
            case R.id.btnStop:
                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                }
            default:
                break;
        }
    }
}
