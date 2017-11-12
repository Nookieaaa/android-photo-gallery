package com.photogallery.app.presentation.util;

import android.content.Context;
import android.content.Intent;

import com.photogallery.app.R;
import com.photogallery.app.data.model.Photo;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

public class ShareUtil {

    private static final String TYPE = "text/plain";

    public static void shareImage(final Context context,  final BottomSheetLayout bottomSheet, Photo photo) {

        final Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType(TYPE);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, photo.getBigImageUrl());
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, photo.getPhotoName());

        bottomSheet.showWithSheetView(new IntentPickerSheetView(context,
                sharingIntent,
                context.getString(R.string.sharing_image_with),
                activityInfo -> {
                    bottomSheet.dismissSheet();
                    context.startActivity(activityInfo.getConcreteIntent(sharingIntent));
                }));
    }
}
