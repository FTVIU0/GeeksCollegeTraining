package com.nlte.timeservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class TimeService extends Service {

    private boolean running = false;//控制标志
    private int numIndex = 0;//传输的数据

    private RemoteCallbackList<TimeServiceCallback> mCallbackList = new RemoteCallbackList<>();

    public TimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ITimeRemoteService.Stub() {

            @Override
            public void registService(TimeServiceCallback callback) throws RemoteException {
                mCallbackList.register(callback);
            }

            @Override
            public void unRegistService(TimeServiceCallback callback) throws RemoteException {
                mCallbackList.unregister(callback);
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                super.run();

                running = true;

                for (numIndex = 0; running; numIndex++) {
                    System.out.println(numIndex);

                    //Returns the number of callbacks in the broadcast
                    int count = mCallbackList.beginBroadcast();

                    while (count-- >0){
                        try {
                            mCallbackList.getBroadcastItem(count).onTimer(numIndex);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    mCallbackList.finishBroadcast();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }
}
