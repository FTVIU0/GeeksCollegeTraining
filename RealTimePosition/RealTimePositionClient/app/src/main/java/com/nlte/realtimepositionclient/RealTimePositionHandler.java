package com.nlte.realtimepositionclient;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * NLTE on 2016/8/9 0009 15:02
 */
public class RealTimePositionHandler extends IoHandlerAdapter {
    private int i = 0;

    private BaiduMap mBaiduMap;
    private Marker mMarker;
    private BDLocation sendLocation;//发送的位置
    private BDLocation receiveLocation;//接受到的位置
    //添加标志物
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);

    public void setLocation(BDLocation location) {
        sendLocation = location;
    }

    public RealTimePositionHandler(BaiduMap baiduMap) {
        mBaiduMap = baiduMap;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        session.write(sendLocation);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        //在地图上标出位置
        if (message != null) {
            receiveLocation = (BDLocation) message;
            LatLng latLng = new LatLng(receiveLocation.getLatitude(), receiveLocation.getLongitude());
            if (mMarker == null) {
                MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bdA)
                        .zIndex(9).draggable(true);
                mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
            }
            mMarker.setPosition(latLng);
        }
    }
}
