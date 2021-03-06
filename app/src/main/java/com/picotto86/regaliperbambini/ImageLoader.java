package com.picotto86.regaliperbambini;

/**
 * Created by picot on 01/12/2015.
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.leansoft.nano.log.ALog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ImageView;

/**
 * Realizes an background image loader backed by a two-level FIFO cache. If the image to be loaded
 * is present in the cache, it is set immediately on the given view. Otherwise, a thread from a
 * thread pool will be used to download the image in the background and set the image on the view as
 * soon as it completes.
 *
 * @author Matthias Kaeppler
 */
public class ImageLoader implements Runnable {

    public static final int HANDLER_MESSAGE_ID = 0;
    public static final String BITMAP_EXTRA = "droidfu:extra_bitmap";
    public static final String IMAGE_URL_EXTRA = "droidfu:extra_image_url";

    private static final String LOG_TAG = "Droid-Fu/ImageLoader";
    // the default thread pool size
    private static final int DEFAULT_POOL_SIZE = 3;

    private static final int DEFAULT_RETRY_HANDLER_SLEEP_TIME = 1000;
    private static final int DEFAULT_NUM_RETRIES = 3;

    private static ThreadPoolExecutor executor;
    private static int numRetries = DEFAULT_NUM_RETRIES;

    /**
     * @param numThreads
     *            the maximum number of threads that will be started to download images in parallel
     */
    public static void setThreadPoolSize(int numThreads) {
        executor.setMaximumPoolSize(numThreads);
    }

    /**
     * @param numAttempts
     *            how often the image loader should retry the image download if network connection
     *            fails
     */
    public static void setMaxDownloadAttempts(int numAttempts) {
        ImageLoader.numRetries = numAttempts;
    }

    /**
     * This method must be called before any other method is invoked on this class. Please note that
     * when using ImageLoader as part of {@link WebImageView} or {@link WebGalleryAdapter}, then
     * there is no need to call this method, since those classes will already do that for you. This
     * method is idempotent. You may call it multiple times without any side effects.
     *
     * @param context
     *            the current context
     */
    public static synchronized void initialize(Context context) {
        if (executor == null) {
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
        }
    }

    public static synchronized void initialize(Context context, long expirationInMinutes) {
        initialize(context);
    }


    private String imageUrl;

    private ImageLoaderHandler handler;

    private ImageLoader(String imageUrl, ImageLoaderHandler handler) {
        this.imageUrl = imageUrl;
        this.handler = handler;
    }

    /**
     * Triggers the image loader for the given image and view. The image loading will be performed
     * concurrently to the UI main thread, using a fixed size thread pool. The loaded image will be
     * posted back to the given ImageView upon completion.
     *
     * @param imageUrl
     *            the URL of the image to download
     * @param imageView
     *            the ImageView which should be updated with the new image
     */
    public static void start(String imageUrl, ImageView imageView) {
        start(imageUrl, imageView, new ImageLoaderHandler(imageView, imageUrl), null, null);
    }

    /**
     * Triggers the image loader for the given image and view and sets a dummy image while waiting
     * for the download to finish. The image loading will be performed concurrently to the UI main
     * thread, using a fixed size thread pool. The loaded image will be posted back to the given
     * ImageView upon completion.
     *
     * @param imageUrl
     *            the URL of the image to download
     * @param imageView
     *            the ImageView which should be updated with the new image
     * @param dummyDrawable
     *            the Drawable set to the ImageView while waiting for the image to be downloaded
     * @param errorDrawable
     *            the Drawable set to the ImageView if a download error occurs
     */
    public static void start(String imageUrl, ImageView imageView, Drawable dummyDrawable,
                             Drawable errorDrawable) {
        start(imageUrl, imageView, new ImageLoaderHandler(imageView, imageUrl,
                        errorDrawable),
                dummyDrawable, errorDrawable);
    }

    /**
     * Triggers the image loader for the given image and handler. The image loading will be
     * performed concurrently to the UI main thread, using a fixed size thread pool. The loaded
     * image will not be automatically posted to an ImageView; instead, you can pass a custom
     * {@link ImageLoaderHandler} and handle the loaded image yourself (e.g. cache it for later
     * use).
     *
     * @param imageUrl
     *            the URL of the image to download
     * @param handler
     *            the handler which is used to handle the downloaded image
     */
    public static void start(String imageUrl, ImageLoaderHandler handler) {
        start(imageUrl, handler.getImageView(), handler, null, null);
    }

    /**
     * Triggers the image loader for the given image and handler. The image loading will be
     * performed concurrently to the UI main thread, using a fixed size thread pool. The loaded
     * image will not be automatically posted to an ImageView; instead, you can pass a custom
     * {@link ImageLoaderHandler} and handle the loaded image yourself (e.g. cache it for later
     * use).
     *
     * @param imageUrl
     *            the URL of the image to download
     * @param handler
     *            the handler which is used to handle the downloaded image
     * @param dummyDrawable
     *            the Drawable set to the ImageView while waiting for the image to be downloaded
     * @param errorDrawable
     *            the Drawable set to the ImageView if a download error occurs
     */
    public static void start(String imageUrl, ImageLoaderHandler handler, Drawable dummyDrawable,
                             Drawable errorDrawable) {
        start(imageUrl, handler.getImageView(), handler, dummyDrawable, errorDrawable);
    }

    private static void start(String imageUrl, ImageView imageView, ImageLoaderHandler handler,
                              Drawable dummyDrawable, Drawable errorDrawable) {
        if (imageView != null) {
            if (imageUrl == null) {
                // In a ListView views are reused, so we must be sure to remove the tag that could
                // have been set to the ImageView to prevent that the wrong image is set.
                imageView.setTag(null);
                imageView.setImageDrawable(dummyDrawable);
                return;
            }
            String oldImageUrl = (String) imageView.getTag();
            if (imageUrl.equals(oldImageUrl)) {
                // nothing to do
                return;
            } else {
                // Set the dummy image while waiting for the actual image to be downloaded.
                imageView.setImageDrawable(dummyDrawable);
                imageView.setTag(imageUrl);
            }
        }

        executor.execute(new ImageLoader(imageUrl, handler));
    }

    /**
     * The job method run on a worker thread. It will first query the image cache, and on a miss,
     * download the image from the Web.
     */
    public void run() {
        Bitmap bitmap = downloadImage();

        // TODO: gracefully handle this case.
        notifyImageLoaded(imageUrl, bitmap);

    }

    // TODO: we could probably improve performance by re-using connections instead of closing them
    // after each and every download
    protected Bitmap downloadImage() {
        int timesTried = 1;

        while (timesTried <= numRetries) {
            try {
                byte[] imageData = retrieveImageData();

                if (imageData == null) {
                    break;
                }

                return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            } catch (Throwable e) {
                ALog.w(LOG_TAG, "download for " + imageUrl + " failed (attempt " + timesTried + ")");
                e.printStackTrace();
                SystemClock.sleep(DEFAULT_RETRY_HANDLER_SLEEP_TIME);
                timesTried++;
            }
        }

        return null;
    }

    protected byte[] retrieveImageData() throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // determine the image size and allocate a buffer
        int fileSize = connection.getContentLength();
        if (fileSize < 0) {
            return null;
        }
        byte[] imageData = new byte[fileSize];

        // download the file
        ALog.d(LOG_TAG, "fetching image " + imageUrl + " (" + fileSize + ")");
        BufferedInputStream istream = new BufferedInputStream(connection.getInputStream());
        int bytesRead = 0;
        int offset = 0;
        while (bytesRead != -1 && offset < fileSize) {
            bytesRead = istream.read(imageData, offset, fileSize - offset);
            offset += bytesRead;
        }

        // clean up
        istream.close();
        connection.disconnect();

        return imageData;
    }

    public void notifyImageLoaded(String url, Bitmap bitmap) {
        Message message = new Message();
        message.what = HANDLER_MESSAGE_ID;
        Bundle data = new Bundle();
        data.putString(IMAGE_URL_EXTRA, url);
        Bitmap image = bitmap;
        data.putParcelable(BITMAP_EXTRA, image);
        message.setData(data);

        handler.sendMessage(message);
    }
}