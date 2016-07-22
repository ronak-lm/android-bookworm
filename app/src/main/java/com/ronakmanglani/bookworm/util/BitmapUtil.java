package com.ronakmanglani.bookworm.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.ronakmanglani.bookworm.BookWormApp;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

public class BitmapUtil {
    
    private BitmapUtil() { }

    // Save Bitmap to file
    public static void saveImageToStorage(final String bookId, final String imageUrl){
        // Save image in background thread
        Picasso.with(BookWormApp.getAppContext())
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ContextWrapper cw = new ContextWrapper(BookWormApp.getAppContext());
                                // Get path to /data/data/your_app_package/app_data/images
                                File directory = cw.getDir("images", Context.MODE_PRIVATE);
                                File myPath = new File(directory, bookId + ".png");
                                // Save bitmap to storage
                                try {
                                    FileOutputStream fos = new FileOutputStream(myPath);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    fos.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) { }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) { }
                });
    }

    // Get image file from book ID
    public static File loadImageFromStorage(String bookId) {
        ContextWrapper cw = new ContextWrapper(BookWormApp.getAppContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        return new File(directory, bookId + ".png");
    }
}
