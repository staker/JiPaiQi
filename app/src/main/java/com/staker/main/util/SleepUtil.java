package com.staker.main.util;

import android.content.Context;
import android.util.Log;

import com.staker.main.interfaces.Action;

public class SleepUtil {
    public static void sleepTime(long time){
        try {
            Thread.sleep(time);
        }catch (InterruptedException e){
        }
    }
    public static void waitTime(Object object) {
        try {
            object.wait();
        }catch (InterruptedException e){
        }
    }
    public static void notifyTime(Object object){
      try {
          synchronized (object){
              Logg.e("--------唤醒所有线程="+object.toString());
              object.notifyAll();
          }
      }catch (Exception e){

      }
    }
}
