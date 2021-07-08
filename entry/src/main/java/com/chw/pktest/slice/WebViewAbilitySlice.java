package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.webengine.*;
import ohos.agp.utils.TextTool;
import ohos.app.Context;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;
import ohos.utils.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class WebViewAbilitySlice extends AbilitySlice {
    static final HiLogLabel TAG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_web_view);

        WebView webView = (WebView) findComponentById(ResourceTable.Id_webview);
        webView.getWebConfig().setJavaScriptPermit(true);
        webView.setWebAgent(new MyWebAgent());

        webView.load("https://www.baidu.com");

        Navigator navigator = webView.getNavigator();
        if (navigator.canGoBack()) {
            navigator.goBack();
        }

        webView.addJsCallback("jsCallbackToApp", new JsCallback() {
            @Override
            public String onCallback(String msg) {
                return "jsResult";
            }
        });

        webView.executeJs("javascript:callFuncInWeb()", new AsyncCallback<String>() {
            @Override
            public void onReceive(String msg) {
                // 在此确认返回结果
            }
        });

        webView.setBrowserAgent(new MyBrowserAgent(this));

        // 加载资源文件 resources/rawfile/example.html
        webView.load("https://example.com/rawfile/example.html");

        // 加载本地文件 /data/data/com.example.dataability/files/example.html
        webView.load("https://example.com/local/example.html");

        webView.getWebConfig().setDataAbilityPermit(true);
        // 加载资源文件 resources/rawfile/example.html
        webView.load("dataability://com.example.dataability/rawfile/example.html");

        // 加载本地文件 /data/data/com.example.dataability/files/example.html
        webView.load("dataability://com.example.dataability/local/example.html");
    }

    private class MyBrowserAgent extends BrowserAgent {
        public MyBrowserAgent(Context context) {
            super(context);
        }

        @Override
        public void onTitleUpdated(WebView webview, String title) {
            super.onTitleUpdated(webview, title);
            // 标题变更时自定义处理
        }

        @Override
        public void onProgressUpdated(WebView webview, int newProgress) {
            super.onProgressUpdated(webview, newProgress);
            // 加载进度变更时自定义处理
        }
    }

    private class MyWebAgent extends WebAgent {
        @Override
        public boolean isNeedLoadUrl(WebView webView, ResourceRequest request) {
            Uri uri = request.getRequestUrl();
            if ("www.chw.pktest".equals(uri.getDecodedHost())) {
                // 由WebView通过默认方式处理
                return false;
            }

            // 增加开发者自定义逻辑
            return super.isNeedLoadUrl(webView, request);
        }

        @Override
        public void onLoadingPage(WebView webView, String url, PixelMap icon) {
            super.onLoadingPage(webView, url, icon);
            // 页面开始加载时自定义处理
        }

        @Override
        public void onPageLoaded(WebView webView, String url) {
            super.onPageLoaded(webView, url);
            // 页面加载结束后自定义处理
        }

        @Override
        public void onLoadingContent(WebView webView, String url) {
            super.onLoadingContent(webView, url);
            // 加载资源时自定义处理
        }

        @Override
        public void onError(WebView webView, ResourceRequest request, ResourceError error) {
            super.onError(webView, request, error);
            // 发生错误时自定义处理
        }

        @Override
        public ResourceResponse processResourceRequest(WebView webview, ResourceRequest request) {
            final String authority = "example.com";
            final String rawFile = "/rawfile/";
            final String local = "/local/";

            Uri requestUri = request.getRequestUrl();
            if (authority.equals(requestUri.getDecodedAuthority())) {
                String path = requestUri.getDecodedPath();
                if (TextTool.isNullOrEmpty(path)) {
                    return super.processResourceRequest(webview, request);
                }

                if (path.startsWith(rawFile)) {
                    // 根据自定义规则访问资源文件
                    String rawFilePath = "entry/resources/rawfile/" + path.replace(rawFile, "");
                    String mimeType = URLConnection.guessContentTypeFromName(rawFilePath);
                    try {
                        Resource resource = getResourceManager().getRawFileEntry(rawFilePath).openRawFile();
                        ResourceResponse response = new ResourceResponse(mimeType, resource, null);
                        return response;
                    } catch (IOException e) {
                        HiLog.info(TAG, "open raw file failed");
                    }
                }

                if (path.startsWith(local)) {
                    // 根据自定义规则访问本地文件
                    String localFile = getContext().getFilesDir() + path.replace(local, "/");
                    HiLog.info(TAG, "open local file " + localFile);
                    File file = new File(localFile);
                    if (!file.exists()) {
                        HiLog.info(TAG, "file not exists");
                        return super.processResourceRequest(webview, request);
                    }
                    String mimeType = URLConnection.guessContentTypeFromName(localFile);
                    try {
                        InputStream inputStream = new FileInputStream(file);
                        ResourceResponse response = new ResourceResponse(mimeType, inputStream, null);
                        return response;
                    } catch (IOException e) {
                        HiLog.info(TAG, "open local file failed");
                    }
                }
            }
            return super.processResourceRequest(webview, request);
        }
    }
}
