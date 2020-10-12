package com.github.lzyzsd.jsbridge;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class BridgeWebChromeClient extends WebChromeClient {
    private BridgeWebView webView;
    private WebChromeClient webChromeClient;

    public BridgeWebChromeClient(BridgeWebView webView) {
        this.webView = webView;
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.webChromeClient = webChromeClient;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(webChromeClient!=null){
            webChromeClient.onProgressChanged(view,newProgress);
        }

        if (newProgress >= 100) {//onpagefinish

            if (BridgeWebView.toLoadJs != null) {
                BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
            }

            //
            if (webView.getStartupMessage() != null) {
                for (Message m : webView.getStartupMessage()) {
                    webView.dispatchMessage(m);
                }
                webView.setStartupMessage(null);
            }
        }
    }


    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if(webChromeClient!=null){
            webChromeClient.onReceivedTitle(view,title);
        }

    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if(webChromeClient!=null){
          return  webChromeClient.onJsAlert(view,url,message,result);
        }

        return super.onJsAlert(view, url, message, result);
    }

}
