package com.github.lzyzsd.jsbridge;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class BridgeWebChromeClient extends WebChromeClient {
    private BridgeWebView webView;

    public BridgeWebChromeClient(BridgeWebView webView) {
        this.webView = webView;
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);

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

}
