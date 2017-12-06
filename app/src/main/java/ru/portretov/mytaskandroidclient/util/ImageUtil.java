package ru.portretov.mytaskandroidclient.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by adminvp on 12/7/17.
 */

public class ImageUtil {

    public static Bitmap createBitmapFromByteArray(byte[] image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }
}
