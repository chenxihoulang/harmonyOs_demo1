package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.net.*;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static ohos.data.search.schema.PhotoItem.TAG;

public class NetworkAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_network);

        NetManager netManager = NetManager.getInstance(this);
        boolean hasDefaultNet = netManager.hasDefaultNet();
        if (hasDefaultNet) {
            NetHandle defaultNet = netManager.getDefaultNet();

            try {
                URL uRL = new URL("https://www.baidu.com");
                defaultNet.openConnection(uRL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //应用绑定该数据网络,null取消绑定
        netManager.setAppNet(null);


        if (!netManager.hasDefaultNet()) {
            return;
        }
        NetHandle netHandle = netManager.getDefaultNet();

        // 可以获取网络状态的变化
        NetStatusCallback callback = new NetStatusCallback() {
            // 重写需要获取的网络状态变化的override函数
            @Override
            public void onAvailable(NetHandle handle) {
                super.onAvailable(handle);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
            }
        };
        netManager.addDefaultNetStatusCallback(callback);

        // 通过openConnection来获取URLConnection
        HttpURLConnection connection = null;
        try {
            String urlString = "https://www.baidu.com";
            URL url = new URL(urlString);
            URLConnection urlConnection = netHandle.openConnection(url, java.net.Proxy.NO_PROXY);
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }
            connection.setRequestMethod("GET");
            connection.connect();
            // 之后可进行url的其他操作
        } catch (IOException e) {
            LogUtils.error(TAG, "exception happened.");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        NetSpecifier netSpecifier = new NetSpecifier.Builder().addBearer(NetCapabilities.BEARER_WIFI).build();
        netManager.addNetStatusCallback(netSpecifier, callback);
        netManager.removeNetStatusCallback(callback);


        MmsCallback callback1 = new MmsCallback(netManager);

        // 配置一个彩信类型的蜂窝网络
        NetSpecifier req = new NetSpecifier.Builder()
                .addCapability(NetCapabilities.NET_CAPABILITY_MMS)
                .addBearer(NetCapabilities.BEARER_CELLULAR)
                .build();

        // 建立数据网络，通过callback获取网络变更状态
        netManager.setupSpecificNet(req, callback1);


        File dir=new File("fileDirPath");
        // 初始化时设置缓存目录dir及最大缓存空间
        try {
             HttpResponseCache.install(dir, 10 * 1024 * 1024);
            // 访问URL

            // 为确保缓存保存到文件系统可以执行flush操作
            HttpResponseCache.getInstalled().flush();

            // 结束时关闭缓存
            HttpResponseCache.getInstalled().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MmsCallback extends NetStatusCallback {
        private NetManager mNetManager;

        public MmsCallback(NetManager netManager) {
            mNetManager = netManager;
        }

        @Override
        public void onAvailable(NetHandle handle) {
            // 通过setAppNet把后续应用所有的请求都通过该网络进行发送
            mNetManager.setAppNet(handle);
            HttpURLConnection connection = null;
            try {
                String urlString = "";
                URL url = new URL(urlString);
                URLConnection urlConnection = handle.openConnection(url, java.net.Proxy.NO_PROXY);
                if (urlConnection instanceof HttpURLConnection) {
                    connection = (HttpURLConnection) urlConnection;
                }
                connection.setRequestMethod("GET");
                connection.connect();
                // 之后可进行url的其他操作
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            // 如果业务执行完毕，可以停止获取
            mNetManager.removeNetStatusCallback(this);
        }
    }
}
