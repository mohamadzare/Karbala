package com.zare.karbala;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Locale;


import dm.audiostreamer.CurrentSessionCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class a_playe_voice extends Activity {

    private MediaPlayer mediaPlayer;

    private SeekBar seekBar;

    TextView coverText;

    public String audioFile, text_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.a_playe_voice);


        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();


        audioFile = getIntent().getStringExtra("voice").toString().trim();
        text_voice = getIntent().getStringExtra("voice_text").toString().trim();


        //   final String coverImage = intent.getStringExtra(a_playe_voice.IMG_URL);


        // create a media player
        mediaPlayer = new MediaPlayer();

        try {

            // give data to mediaPlayer
            mediaPlayer.setDataSource(audioFile);
            // media player asynchronous preparation
            mediaPlayer.prepareAsync();

            // create a progress dialog (waiting media player preparation)
            final ProgressDialog dialog = new ProgressDialog(a_playe_voice.this);

            // set message of the dialog
            dialog.setMessage("در حال آماده سازی");

            // prevent dialog to be canceled by back button press
            dialog.setCancelable(false);

            // show dialog at the bottom
            dialog.getWindow().setGravity(Gravity.CENTER);

            // show dialog
            dialog.show();


            // inflate layout
            setContentView(R.layout.a_playe_voice);

            // display title
            ((TextView) findViewById(R.id.now_playing_text)).setText(audioFile);


            coverText = (TextView) findViewById(R.id.coverText);

            coverText.setMovementMethod(new ScrollingMovementMethod());

            coverText.setText(text_voice);

            coverText.setText(new SpannableString(coverText.getText()));

          //  TextJustification.justify(coverText);




//            ssb.setSpan(fcsGreen, 16, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ssb.setSpan(bcsYellow, 27, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            /// Load cover image (we use Picasso Library)

            // Get image view
//            ImageView mImageView = (ImageView) findViewById(R.id.coverImage);
//
//            // Image url
//            String image_url = coverImage;
//
//
//            Picasso.with(getApplicationContext()).load(image_url).into(mImageView);

            ///


            // execute this code at the end of asynchronous media player preparation
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(final MediaPlayer mp) {


                    //start media player
                    mp.start();

                    // link seekbar to bar view
                    seekBar = (SeekBar) findViewById(R.id.seekBar);

                    //update seekbar
                    mRunnable.run();

                    //dismiss dialog
                    dialog.dismiss();
                }
            });


        } catch (IOException e) {
            Activity a = this;
            a.finish();
            // Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
        }


    }


    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if (mediaPlayer != null) {

                //set max value
                int mDuration = mediaPlayer.getDuration();
                seekBar.setMax(mDuration);

//                SpannableString ss = new SpannableString(text_voice);
//
//                SpannableStringBuilder ssb = new SpannableStringBuilder(text_voice);
//
//                int i = coverText.getLineCount();
//
//                ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
//                ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.GREEN);
//                BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.YELLOW);
//
//
//                for (int j = 0; j < 80; j++) {
//                    ssb.setSpan(fcsRed, 0, j, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    ssb.append(" ");
//                    coverText.setText(ssb);
//                }
                //update total time text view
                TextView totalTime = (TextView) findViewById(R.id.totalTime);
                totalTime.setText(getTimeString(mDuration));

                //set progress to current position
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);

                //update current time text view
                TextView currentTime = (TextView) findViewById(R.id.currentTime);
                currentTime.setText(getTimeString(mCurrentPosition));

                //handle drag on seekbar
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mediaPlayer != null && fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }
                });


            }

            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void play(View view) {

        mediaPlayer.start();
    }


    public void pause(View view) {

        mediaPlayer.pause();

    }

    public void stop(View view) {

        mediaPlayer.seekTo(0);
        mediaPlayer.pause();

    }


    public void seekForward(View view) {

        //set seek time
        int seekForwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekForward time is lesser than song duration
        if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
            // forward song
            mediaPlayer.seekTo(currentPosition + seekForwardTime);
        } else {
            // forward to end position
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }

    }

    public void seekBackward(View view) {

        //set seek time
        int seekBackwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if (currentPosition - seekBackwardTime >= 0) {
            // forward song
            mediaPlayer.seekTo(currentPosition - seekBackwardTime);
        } else {
            // backward to starting position
            mediaPlayer.seekTo(0);
        }

    }


    public void onBackPressed() {

        super.onBackPressed();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        startActivity(new Intent(a_playe_voice.this, a_detialz_qoran.class));
        finish();
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}