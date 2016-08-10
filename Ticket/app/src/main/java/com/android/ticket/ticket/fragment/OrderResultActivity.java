package com.android.ticket.ticket.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.ticket.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

public class OrderResultActivity extends Activity {
    private int trainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result);


        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("Username");
        String trainId = bundle.getString("TrainId");
        String fromMyCity = bundle.getString("FromMyCity");
        String toMyCity = bundle.getString("ToMyCity");

        System.out.println("二维码数据\n" + username + "\n" + trainId + "\n" + fromMyCity + "\n" + toMyCity);

        String contents = "BEGIN:TICKET\nVERSION:1.0\n" + "Name:" + username + "\nTrainId:" + trainId + "\nFromMyCity:" + fromMyCity + "\nToMyCity:" + toMyCity;
        try {
            Bitmap bm = qr_code(contents, BarcodeFormat.QR_CODE);
            ImageView img = (ImageView) findViewById(R.id.order_result_img);
            img.setImageBitmap(bm);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public Bitmap qr_code(String string, BarcodeFormat format)
            throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
        hst.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = writer.encode(string, format, 400, 400, hst);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
