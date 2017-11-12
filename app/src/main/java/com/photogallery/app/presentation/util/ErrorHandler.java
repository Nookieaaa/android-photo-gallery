package com.photogallery.app.presentation.util;

import android.content.Context;
import android.content.res.Resources;

import com.photogallery.app.R;
import com.photogallery.app.PhotoGalleryApplication;


public class ErrorHandler {

    public static String handleError() {
        Context context = PhotoGalleryApplication.getInstance();
        Resources resources = context.getResources();
        if (!ConnectionUtils.isNetworkAvailable(context))
            return resources.getString(R.string.error_internet_connection);
        else
            return resources.getString(R.string.error_something_bad_happened);
    }
}
