package in.fusionbit.shreejeeseizingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageActivity extends BaseActivity {

    @BindView(R.id.wb_viewImage)
    WebView mWebView;

    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setTitle("View Image");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageName = getIntent().getStringExtra("image_name");

        if (imageName == null) {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        Toast.makeText(this, imageName, Toast.LENGTH_SHORT).show();

        showProgressDialog("Loading Image", "Please Wait...");

        loadImage();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadImage() {
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(App.APP_TAG, "page finished loading " + url);
                hideProgressDialog();
            }
        });

        // Generate an HTML document on the fly:
        /*String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);*/
        mWebView.clearHistory();
        mWebView.clearCache(true);
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        mWebView.getSettings().setUserAgentString(newUA);

        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);

        mWebView.loadUrl("http://shreejeecreditabr.com/images/shreejee_seizing/" + imageName);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
    }

    @Override
    int getLayoutResourceId() {
        return R.layout.activity_view_image;
    }

    public static void start(final Context context, final String imageName) {
        final Intent intent = new Intent(context, ViewImageActivity.class);
        intent.putExtra("image_name", imageName);
        context.startActivity(intent);
    }

}
