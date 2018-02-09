package in.blogspot.iamdhariot.newera;

import android.speech.tts.TextToSpeech;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener,View.OnClickListener {

    /***
     * UI stuffs
     */

    private Button btnSpeech;
    private EditText textView;

    /**
     *  TTS stuffs
     * */
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *  UI Stuffs reference
         * */
        btnSpeech = (Button)findViewById(R.id.btnSpeech);
        textView = (EditText)findViewById(R.id.textView);
        /**
         *  TTS Stuffs reference
         * */
        textToSpeech = new TextToSpeech(this,this);

    }

    @Override
    public void onInit(int status) {

    }

    @Override
    public void onClick(View v) {

    }

    // never forget to shutdown tts
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
