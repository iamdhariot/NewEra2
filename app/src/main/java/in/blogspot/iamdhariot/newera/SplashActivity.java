package in.blogspot.iamdhariot.newera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class SplashActivity extends Activity {

    private ImageView logo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        logo = (ImageView)findViewById(R.id.logo);

        progressBar.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_bottom_up));
        logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_up_bottom));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // progressDialog.dismiss();

                startActivity(new Intent(SplashActivity.this,StartActivity.class));
                finish();

            }
        },3*1000);
    }


}
