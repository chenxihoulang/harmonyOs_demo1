package com.chw.pktest.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

import java.io.*;

/**
 * 分布式文件服务
 */
public class DistributeFileAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);

        File distDir = getDistributedDir();
        String filePath = distDir + File.separator + "hello.txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write("Hello World");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            char[] buffer = new char[1024];
            fileReader.read(buffer);
            fileReader.close();
            System.out.println(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
