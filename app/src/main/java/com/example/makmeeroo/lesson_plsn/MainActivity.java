package com.example.makmeeroo.lesson_plsn;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String RESUME_STRING = "Resume";
    public static final String STOP_STRING = "Stop";
    public static final String LESSON_SHAPES = "shapes";
    public static final String LESSON_VEHICLES = "vehicles";
    public static final String LESSON_BODY_PARTS = "bodyParts";
    public static final String LESSON_BIRDS = "birds";
    public static final String LESSON_PETS = "pets";
    public static final String LESSON_WILD_ANIMALS = "wildAnimals";
    public static final String LESSON_FRUITS = "fruits";
    int stopCounter = 0;
    private Handler mHandler;
    int displaytime = 4;

    // Added on Juy 4 - for new UI//
    String[] masterlistofLessons = {"wildAnimals","fruits",LESSON_VEHICLES,"pets","birds","bodyParts",LESSON_SHAPES};
    String[] masterlistofTags = {"1000","1001","1002","1003","1004","1005","1006"};
    int [] selectedvalueofLessons = {0,0,0,0,0,0,0};
    String [] selectedlistofLessons = new String[3];
    int masternoofLessons = masterlistofLessons.length;
    int position;
    String[] fullList = new String[100];

    String[] wildAnimals = {"tiger","lion","elephant","cow","sheep","giraffe","zebra","snake","monkey","kangaroo"};
    String[] fruits = { "apple", "pear", "banana","papaya", "jackfruit",  "orange", "peach","strawberry","watermelon","grapes"};
    String[] vehicles = {"car","train","bus","van","plane","rocket","motorcycle","truck","ambulance"};
    String[] pets = {"dog","cat","pig","rabbit"};
    String[] birds = {"parrot","hen","ostrich","eagle","flamingo","sparrow","hawk"};
    String[] bodyParts = {"hands","nose","mouth","feet","eyes"};
    String[] shapes = {"square","circle","rectangle","triangle","diamond","hexagon"};

    String[] chosenLesson = pets;
    int chosenLesssonLength = chosenLesson.length -1;
    int nooflessons;

    MediaPlayer pronouncePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.e("flash_card", " arrived at onCreate");
        mHandler = new Handler();
        mHandler.post(mUpdate);
    }

    private Runnable mUpdate = new Runnable() {
        public void run() {
            Log.e("flash_card", " mUpdate called. stopCounter = " + stopCounter);
//            nextTextMessage(stopCounter);     ML changed this on 7/9
            nextImage(stopCounter);
            if (stopCounter<chosenLesssonLength) {
                mHandler.postDelayed(this, displaytime * 1000);
                stopCounter++;
            }
        }
    };

    public void nextTextMessage(int counter){
        String selectedWord= chosenLesson[counter];
        Log.e("flash_card ", " selectedWord " + selectedWord);
        if (!selectedWord.isEmpty()){
            TextView temp2 = (TextView) findViewById(R.id.textView);
            temp2.setText(selectedWord);
        }
    }

    public void nextImage(int counter) {
        String selectedPicture = "@drawable/" + chosenLesson[counter];
        int pic_id = getResources().getIdentifier(selectedPicture, null, getPackageName()); // get the location of where l1, l2, etc are stored
//        Drawable pic = getResources().getDrawable(pic_id);  // take picture from that location and save it in a drawable pic
        Drawable pic = ContextCompat.getDrawable(this, pic_id); // http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
        ImageView temp1 = (ImageView) findViewById(R.id.imageView1);
        temp1.setImageDrawable(pic);        //display pic in the image

        String voiceFile = chosenLesson[counter];
        int resID = this.getResources().getIdentifier(voiceFile, "raw", this.getPackageName());
        Log.e("flash_card ", "resID = " + resID);
        pronouncePlay = MediaPlayer.create(this, resID);
        pronouncePlay.start();
    }

    public void stopresume(View v) {

        Button mStartStop = (Button) findViewById(R.id.button2);
        String temp1 = mStartStop.getText().toString();
        Log.e("flash_card", "startstop = " + temp1);
        switch (temp1) {
            case STOP_STRING:
                mHandler.removeCallbacksAndMessages (null); // fix this to stop only mUpdate; also stop media player first (perhaps add a function for mHandler)
                mStartStop.setText(RESUME_STRING);
                break;
            case RESUME_STRING:
                mStartStop.setText(STOP_STRING);
                mHandler.post(mUpdate);
                break;
        }
    }

    public void changeselections(View v) {
        mHandler.removeCallbacksAndMessages(null);
//        setContentView(R.layout.adjustments); // TODO add a gear box instead of button; include a sound on/off option
        setContentView(R.layout.usersettings);
        nooflessons = 0;
    }

    public void selectSet(View v) {
        String clickedLesson =  (String) v.getTag();
        int tagValue = Integer.parseInt(clickedLesson) - 1000;
        Log.e("clciked Tag =", " "+tagValue);

        position ++;
        float alpha = v.getAlpha();
        if (alpha==1) {alpha = alpha / 2;} else {alpha = alpha * 2;}
        v.setAlpha(alpha);

        if (alpha!=1) {selectedvalueofLessons[tagValue] = position;} else{selectedvalueofLessons[tagValue] = 0;};
        Log.e("tag= " + tagValue +";a = " + alpha + ";pos= "+ position, "selectedvalue["+ tagValue + "] =" +selectedvalueofLessons[tagValue]);
    }

    public void selectionDone(View v) {
        int j =0;
        position =0;
        for (int i =0; i<masternoofLessons; i++) {
            Log.e("masterlistofLessons = "+ masterlistofLessons[i],"itemp2 = " + i);
            if (selectedvalueofLessons[i] >0)
            {
                System.arraycopy(masterlistofLessons, i, selectedlistofLessons, j, 1);
                Log.e("slctdlistofLe[" +j+ "] = "+selectedlistofLessons[j],"pos = " + j);
                j++;
            }
            Log.e("i= "+i,"j ="+j);
        }

        Log.e("started ", " hashtags");
        Map<String, String[]> strtoArrMaps = new HashMap<>();
        strtoArrMaps.put("fruits", fruits);
        strtoArrMaps.put("wildAnimals", wildAnimals);
        strtoArrMaps.put("pets", pets);
        Log.e("50% complete ", " hashtags");
        strtoArrMaps.put("birds", birds);
        strtoArrMaps.put("bodyParts", bodyParts);
        strtoArrMaps.put("vehicles", vehicles);
        strtoArrMaps.put("shapes", shapes);
        Log.e("completed ", " hashtags");

        int k =0;
        if (j == 0) {
            System.arraycopy(fruits,0,fullList,0,fruits.length);
            k= fruits.length;
        }   else
        {   for (int i = 0; i < j; i++) {
                if (i == 0) {
                    System.arraycopy(strtoArrMaps.get(selectedlistofLessons[i]), 0, fullList, 0, strtoArrMaps.get(selectedlistofLessons[i]).length);
                    k = k + strtoArrMaps.get(selectedlistofLessons[i]).length;
                } else {
                    System.arraycopy(strtoArrMaps.get(selectedlistofLessons[i]), 0, fullList, k, strtoArrMaps.get(selectedlistofLessons[i]).length);
                    k = k + strtoArrMaps.get(selectedlistofLessons[i]).length;
                }
            }
        }

        for (int l =0; l<k;l++) {Log.e("fulllist[" +l+ "] = ", fullList[l]);}

        displaytime = 3;
        setContentView(R.layout.activity_main);
        stopCounter = 0;

        chosenLesson = fullList;
        chosenLesssonLength = k -1;
        Log.e("chosen lesson length =", " "+ chosenLesssonLength);

        mHandler = new Handler();
        mHandler.post(mUpdate);
    }
}

