package com.nlte.clearmemory;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityManager mActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager
                .getRunningAppProcesses();

        long beforeMem = getAvalMemory();
        int count = 0;//计算被清除的进程数

        if (runningAppProcessesList != null) {
            for (int i = 0; i < runningAppProcessesList.size(); i++) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo
                        = runningAppProcessesList.get(i);
                if (runningAppProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = runningAppProcessInfo.pkgList;
                    for (String aPkgList : pkgList) {
                        mActivityManager.killBackgroundProcesses(aPkgList);
                        count++;
                    }
                }
            }
        }

        long afterMem = getAvalMemory();
        Toast.makeText(this,
                String.format(getResources().getString(R.string.toast), count, (afterMem - beforeMem)),
                Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 获取可用内存
     *
     * @return
     */
    private long getAvalMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / (1024 * 1024);
    }
}
