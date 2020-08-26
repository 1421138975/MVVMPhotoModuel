package com.dfzt.bitmapmodule;

import android.util.Log;

public class Test {

    public static void main(String[] args) {
        getName();
        getName();


    }

    public static synchronized void getName(){
        try {
            Thread.sleep(3000);
            System.out.println("获取名字");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
