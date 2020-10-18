package edu.temple.abrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {

    private WebView webView;
    private PageViewerInterface browserActivity;

    public PageViewerFragment() {
    }

    // Save reference to parent
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageViewerInterface) {
            browserActivity = (PageViewerInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View l = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = l.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                // Inform parent activity that URL is changing
                browserActivity.updateUrl(url);
            }
        });

        return l;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Restore WebView settings
        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Store URL and previous/back in case fragment is restarted
        webView.saveState(outState);
    }


    /**
     * Load provided URL in webview
     * @param url to load
     */
    public void go (String url) {
        webView.loadUrl(url);
    }

    /**
     * Go to previous page
     */
    public void back () {
        webView.goBack();
    }

    /**
     * Go to next page
     */
    public void forward () {
        webView.goForward();
    }

    interface PageViewerInterface {
        void updateUrl(String url);
    }
}