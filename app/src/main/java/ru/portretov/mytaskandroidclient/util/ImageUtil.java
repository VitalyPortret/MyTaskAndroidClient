package ru.portretov.mytaskandroidclient.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.portretov.mytaskandroidclient.R;
import ru.portretov.mytaskandroidclient.entity.enumirate.Alert;

/**
 * Created by adminvp on 12/7/17.
 */

public class ImageUtil {

    public static Bitmap createBitmapFromByteArray(byte[] image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }

    public static int getTaskAlertImageRes(Alert alert) {
        int idRes = 0;
        switch (alert) {
            case CLEANING:
                idRes = R.drawable.a_clean;
                break;
            case ASSEMBLY:
                idRes = R.drawable.a_asembly;
                break;
            case HANDYMAN:
                idRes = R.drawable.a_handyman;
                break;
            case DELIVERY:
                idRes = R.drawable.a_delivery;
                break;
            case GARDENING:
                idRes = R.drawable.a_gardening;
                break;
            case REMOVALISTS:
                idRes = R.drawable.a_removalists;
                break;
            case ADMIN:
                idRes = R.drawable.a_admin;
                break;
            case COMPUTER_IT:
                idRes = R.drawable.a_it_comp;
                break;
            case PHOTOGRAPHY:
                idRes = R.drawable.a_photo;
                break;
            case ANYTHING_ELSE:
                idRes = R.drawable.a_anyhing_else;
                break;
        }
        return idRes;
    }

    public static int getTaskStatusImageRes(String taskStatus) {
        int idRes = 0;
        switch (taskStatus) {
            case "OPEN":
                idRes = R.drawable.task_status_open;
                break;
            case "ASSIGNED":
                idRes = R.drawable.task_status_assigned;
                break;
        }
        return idRes;
    }
}
