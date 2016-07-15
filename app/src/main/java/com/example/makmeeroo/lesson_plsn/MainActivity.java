package com.example.makmeeroo.lesson_plsn;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// TODO http://www.tutorialspoint.com/java/
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
    public static final String LESSON_HOME = "home";
    public static final String LESSON_VEGETABLES = "vegetables";
    public static final String LESSON_DINNER = "dinner";
    public static final String LESSON_PARK = "park";
    int stopCounter = 0;
    private Handler mHandler;
    int displaytime = 4;

    // Added on Juy 4 - for new UI//
    String[] masterlistofLessons = {LESSON_WILD_ANIMALS,LESSON_FRUITS,LESSON_VEHICLES,LESSON_PETS,LESSON_BIRDS,LESSON_BODY_PARTS,
            LESSON_SHAPES,LESSON_HOME,LESSON_VEGETABLES,LESSON_DINNER, LESSON_PARK};
    int [] selectedvalueofLessons = {0,0,0,0,0,0,0,0,0,0,0};
    String [] selectedlistofLessons = new String[3];
    int masternoofLessons = masterlistofLessons.length;
    int position;
    String[] fullList = new String[100];

    String[] wildAnimals = {"bear","bison","cheetah","crocodile","deer","elephant","fox","giraffe","hippo","kangaroo","koala","lion","monkey","orangutan","panda","rhinoceros","sheep","snake","tiger","wolf","zebra"};
    String[] fruits = { "apple","apricot","banana","grapes","jackfruit","orange","papaya","peach","pear","plum","strawberry","watermelon",};
    String[] vehicles = {"ambulance","bus","car","motorcycle","plane","rocket","train","truck","van"};
    String[] pets = {"cat","cow","dog","pig","rabbit"};
    String[] birds = {"eagle","flamingo","hawk","hen","ostrich","parrot" ,"sparrow"};
    String[] bodyParts = {"eyes","nose","mouth","teeth","tongue","chin","ear","head","hair","fingers","hands","feet","shoulders"};
    String[] shapes = {"circle","diamond","hexagon","rectangle","square","triangle"};
    String[] home = {"carpet","chair","clock","curtains","fireplace","lamp","sofa","speaker","table","tv"};
    String[] vegetables ={"carrot","cauliflower","eggplant","greenbeans","lemon","onion","peas","pepper","potato","pumpkin","tomato"};
    String[] dinner = {"table","chair","spoon","bowl","bib","pasta","milk"};
    String [] park = {"slide","swing","spinner"};

    String[] chosenLesson = pets;
    int chosenLesssonLength = chosenLesson.length -1;
    int nooflessons;

    MediaPlayer pronouncePlay;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.e("flash_card", " arrived at onCreate");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status== TextToSpeech.SUCCESS) {
                    Log.e("entered"," tts object");
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate(1.0f);
 //                   t1.setPitch(1.0f);
                }
            }
        });

        mHandler = new Handler();
        mHandler.post(mUpdate);
    }

    private Runnable mUpdate = new Runnable() {
        public void run() {
            Log.e("flash_card", " mUpdate called. stopCounter = " + stopCounter);
            nextTextMessage(stopCounter);     //ML changed this on 7/9
            nextImage(stopCounter);
            if (stopCounter<chosenLesssonLength) {
                mHandler.postDelayed(this, displaytime * 1000);
                stopCounter++;
                if (stopCounter == chosenLesssonLength) {stopCounter =0;} // ML added for continuous looping
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

//        String voiceFile = chosenLesson[counter];
//        int resID = this.getResources().getIdentifier(voiceFile, "raw", this.getPackageName());
//        Log.e("flash_card ", "resID = " + resID);
//        pronouncePlay = MediaPlayer.create(this, resID);
//        pronouncePlay.start();
// TODO debug silent cat problem
// TODO check voice file length, and set time based on that

        // TODO use voice if available, if not use text to speech
        t1.speak(chosenLesson[counter], TextToSpeech.QUEUE_FLUSH, null);

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
        setContentView(R.layout.usersettings);
        for (int i=0;i<selectedvalueofLessons.length;i++) {selectedvalueofLessons[i] = 0;}
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

        if (alpha!=1) {
            selectedvalueofLessons[tagValue] = position;// TODO use the saved position
        } else{
            selectedvalueofLessons[tagValue] = 0;
        };
        Log.e("tag= " + tagValue +";a = " + alpha + ";pos= "+ position, "selectedvalue["+ tagValue + "] =" +selectedvalueofLessons[tagValue]);
    }

    public void selectionReset(View v) {
        int[] picIds = new int[] {R.id.image1000,R.id.image1001,R.id.image1002,R.id.image1003,R.id.image1004,
                R.id.image1005, R.id.image1006, R.id.image1007,R.id.image1008,R.id.image1009,R.id.image1010,
                R.id.image1011,R.id.image1012,R.id.image1013,R.id.image1014,R.id.image1015,R.id.image1016,
                R.id.image1017,R.id.image1018,R.id.image1019,R.id.image1020};
        for (int i =0; i<21; i++) {
            ImageView temp1 = (ImageView) findViewById(picIds[i]);
            temp1.setAlpha(1.0f);
        }
        for (int i=0;i<selectedvalueofLessons.length;i++) {selectedvalueofLessons[i] = 0;}
    }

    public void selectionDone(View v) {
        int j =0;
        position =0;
        for (int i =0; i<masternoofLessons; i++) {
            Log.e("masterlistofLessons = "+ masterlistofLessons[i],"itemp2 = " + i);
            if (selectedvalueofLessons[i] >0)
            {// TODO use position here
                System.arraycopy(masterlistofLessons, i, selectedlistofLessons, j, 1);
                Log.e("slctdlistofLe[" +j+ "] = "+selectedlistofLessons[j],"pos = " + j);
                j++;
            }
            Log.e("i= "+i,"j ="+j);
        }

        Log.e("started ", " hashtags");
        Map<String, String[]> strtoArrMaps = new HashMap<>();
        strtoArrMaps.put(LESSON_FRUITS, fruits);
        strtoArrMaps.put(LESSON_WILD_ANIMALS, wildAnimals);
        strtoArrMaps.put(LESSON_PETS, pets);
        strtoArrMaps.put(LESSON_BIRDS, birds);
        strtoArrMaps.put(LESSON_BODY_PARTS, bodyParts);
        strtoArrMaps.put(LESSON_VEHICLES, vehicles);
        strtoArrMaps.put(LESSON_SHAPES, shapes);
        strtoArrMaps.put(LESSON_HOME, home);
        strtoArrMaps.put(LESSON_VEGETABLES,vegetables);
        strtoArrMaps.put(LESSON_DINNER,dinner);
        strtoArrMaps.put(LESSON_PARK,park);

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

