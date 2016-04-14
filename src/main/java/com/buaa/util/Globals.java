package com.buaa.util;

import android.app.Activity;

import com.buaa.activity.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
public class Globals {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static Map<String, Integer> imgs;

    public static void init(Activity a) {
        SCREEN_WIDTH = a.getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT = a.getWindowManager().getDefaultDisplay().getHeight();

        imgs = new HashMap<>();
        imgs.put("close_dir", R.drawable.close_dir);
        imgs.put("open_dir", R.drawable.open_dir);
        imgs.put("mp3", R.drawable.mp3_file);
        imgs.put("mp4", R.drawable.mp4_file);
        imgs.put("txt", R.drawable.txt_file);
        imgs.put("jpg", R.drawable.image_file);
        imgs.put("png", R.drawable.image_file);
        imgs.put("gif", R.drawable.image_file);
        imgs.put("bmp", R.drawable.image_file);


    }
}
