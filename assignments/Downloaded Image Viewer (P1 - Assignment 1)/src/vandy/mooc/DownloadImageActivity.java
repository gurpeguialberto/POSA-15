package vandy.mooc;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
    
    /**
     * background Thread used to make the downloading work 
     */
    private Thread mThread;
    
    /**
     * Reference to UI Thread
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());
    
    /**
     * Uri. Path of image file stored 
     */
    private Uri urlImage;
    
    /**
     * Uri. Image resource to download from Internet
     */
    private Uri urlToDownload;
    
    /**
     * Intent to give back the path to the dowloaded file 
     */
    Intent mIntentToReturnUrl;
    
    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
    	super.onCreate(savedInstanceState);
    	
        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
    	
    	urlToDownload = getIntent().getData();
    	
        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.

        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.
    	 
    	 Runnable downloadRunnable = new Runnable() {
            
             @Override
             public void run() {
            	 
            	 urlImage = DownloadUtils.downloadImage(getApplicationContext(), urlToDownload);
            	 
            	 mIntentToReturnUrl = new Intent();
            	 mIntentToReturnUrl.setData(urlImage);
                 setResult(RESULT_OK, mIntentToReturnUrl);   
            	 
                 // finish this Activity from UI Thread
            	 mHandler.post(new Runnable() {

 					@Override
 					public void run() {
 						Log.d(TAG, "This Thread is running...:" + Thread.currentThread().getName());
 						DownloadImageActivity.this.finish();
 					}
 				});                              	 
             }             
         };
    	
         mThread = new Thread(downloadRunnable);
         mThread.start();
         
    }   
}
