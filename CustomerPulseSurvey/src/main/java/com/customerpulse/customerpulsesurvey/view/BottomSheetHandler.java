package com.customerpulse.customerpulsesurvey.view;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.customerpulse.customerpulsesurvey.R;
import com.customerpulse.customerpulsesurvey.utils.Utils;

/**
 * Class to show bottom sheet with a web view containing CustomerPulse web page
 */
public class BottomSheetHandler {

    /**
     * load url to web view inside a bottom sheet dialog
     *
     * @param context     context of the current opened activity
     * @param url         the url to load to webView
     * @param dismissible boolean to decide if the bottom sheet should be dismissible or no
     * @param closingDelayInMs   time to wait before closing the survey after finish milli seconds
     */
    public static void show(Context context, String url, boolean dismissible, int closingDelayInMs) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
        WebView webView = bottomSheetDialog.findViewById(R.id.bottom_web_view);
        Button closeBtn = bottomSheetDialog.findViewById(R.id.close_button);
        assert closeBtn != null;
        if (!dismissible)
            closeBtn.setVisibility(View.INVISIBLE);
        closeBtn.setOnClickListener(view -> bottomSheetDialog.hide());
        assert webView != null;
        Utils.loadUrl(webView, url, context, bottomSheetDialog, closingDelayInMs);
    }


}
