package com.AssassinAndroid.Tools;

import android.graphics.*;
import android.graphics.drawable.shapes.RoundRectShape;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 12:25 AM
 */
public class CircleBitmapDisplayer implements BitmapDisplayer {

    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageBitmap(getRoundedCornerBitmap(bitmap));
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        final Paint paint = new Paint();
        float radius = bitmap.getWidth() / 2f < bitmap.getHeight() / 2f ? bitmap.getWidth() / 2f : bitmap.getHeight() / 2f;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(Math.round(radius * 2f), Math.round(radius * 2f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        RoundRectShape rrs = new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setAntiAlias(true);
        paint.setColor(0xFF000000);
        rrs.resize(radius * 2f, radius * 2f);
        rrs.draw(canvas, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
