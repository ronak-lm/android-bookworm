package com.ronakmanglani.bookworm.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.ronakmanglani.bookworm.BookWormApp;

import java.io.File;
import java.io.FileOutputStream;

public class BitmapUtil {
    
    private BitmapUtil() { }

    // Save Bitmap to file
    public static void saveImageToStorage(final String bookId, final Bitmap bitmapImage){
        // Save image in background thread
        Runnable imageSaveTask = new Runnable() {
            @Override
            public void run() {
                ContextWrapper cw = new ContextWrapper(BookWormApp.getAppContext());
                // Get path to /data/data/your_app_package/app_data/images
                File directory = cw.getDir("images", Context.MODE_PRIVATE);
                File myPath = new File(directory, bookId + ".png");
                // Save bitmap to storage
                try {
                    FileOutputStream fos = new FileOutputStream(myPath);
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(imageSaveTask).start();
    }

    // Get image file from book ID
    public static File loadImageFromStorage(String bookId) {
        ContextWrapper cw = new ContextWrapper(BookWormApp.getAppContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        return new File(directory, bookId + ".png");
    }
}
