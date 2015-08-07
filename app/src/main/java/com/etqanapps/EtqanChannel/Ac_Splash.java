package com.etqanapps.EtqanChannel;

import com.yahia.libs.CustomViews.ImageFromUrl.ImagesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.MyChannel.R;
public class Ac_Splash extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        
        Handler handler = new Handler(); 
        
        
        ImagesHolder.init();
         
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
 
                // make sure we close the splash screen so the user won't come back when it presses back key
 
                finish();
                // start the home screen
 
                //Intent intent = new Intent(Ac_Splash.this, Ac_Start.class);
                Intent intent = new Intent(Ac_Splash.this, Ac_MainMenu.class);
                Ac_Splash.this.startActivity(intent);
  
            }
 
        }, 3000); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
 
    }

}
