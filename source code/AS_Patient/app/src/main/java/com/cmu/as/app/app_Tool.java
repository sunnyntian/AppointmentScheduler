package com.cmu.as.app;

import android.os.Environment;

/**
 * Created by Tina on 14-5-1.
 */
public class app_Tool {
    //check if SD card exist
    public static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}
