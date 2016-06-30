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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import android.net.Uri;

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
//    int stopLimit = 9; // No. of cards in that lesson: needs to be a user input, or something that can be automatically counted
    private Handler mHandler;
    int displaytime = 4;

    String[] fruits = { "apple", "pear", "banana","papaya", "jackfruit",  "orange", "peach","strawberry","watermelon","grapes"};
    String[] wildAnimals = {"tiger","lion","elephant","cow","sheep","giraffe","zebra","snake","monkey","kangaroo"};
    String[] pets = {"dog","cat","pig","rabbit"};
    String[] birds = {"parrot","hen","ostrich","eagle","flamingo","sparrow","hawk"};
    String[] bodyParts = {"hands","nose","mouth","feet","eyes"};
    String[] vehicles = {"car","train","bus","van","plane","rocket","motorcycle","truck","ambulance"};
    String[] shapes = {"square","circle","rectangle","triangle","diamond","hexagon"};

    String[] chosenLesson = pets;
    int chosenLesssonLength = chosenLesson.length -1;
    int nooflessons;
    String[] lessonlist = new String[5];

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
            nextTextMessage(stopCounter);
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
        setContentView(R.layout.adjustments); // TODO add a gear box instead of button; include a sound on/off option
        nooflessons = 0;
    }

    public void selectLesson(View v) {
        Log.e("flash_card", "Came to selectLesson; curent # lessons is "+nooflessons);
        if (nooflessons <4) {
            String currentLesson = (String) v.getTag();
            Log.e("flash_card", "curent lesson is "+currentLesson+" ; is it empty? "+currentLesson.isEmpty());
            lessonlist[nooflessons] = currentLesson;
            EditText mlesson1 = (EditText) findViewById(R.id.lesson1); // TODO fix this with a list of buttons
            EditText mlesson2= (EditText) findViewById(R.id.lesson2);
            EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
            EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
            switch (nooflessons) {
                case 0:mlesson1.setText(currentLesson);break;
                case 1:mlesson2.setText(currentLesson);break;
                case 2:mlesson3.setText(currentLesson);break;
                case 3:mlesson4.setText(currentLesson);break;
            }
            nooflessons++;
        }
    }

    public void selectioncomplete(View v) {

        String[] arrbig = new String[100];
        int unitcounter =0;

        if (nooflessons == 0) {                 // in case the user didn't select anything
            nooflessons =1;
            lessonlist[0] = LESSON_FRUITS;
        }
        else {nooflessons = Math.min(4,nooflessons);}

        Log.e("flash_cards"," nooflessons = "+nooflessons);

        for (int ilooper = 0;  ilooper < nooflessons; ilooper++ ) {
            switch (lessonlist[ilooper]) {
                case LESSON_FRUITS:
                    Log.e("lessonlist = ",lessonlist[ilooper]);
                    if(ilooper ==0) {
                        System.arraycopy(fruits, 0, arrbig, 0, fruits.length);
                        unitcounter = unitcounter + fruits.length;
                    }
                    else {
                        System.arraycopy(fruits, 0, arrbig, unitcounter, fruits.length);
                        unitcounter = unitcounter + fruits.length;
                    }
                    break;
                case LESSON_WILD_ANIMALS:
                    if(ilooper ==0) {
                        System.arraycopy(wildAnimals, 0, arrbig, 0, wildAnimals.length);
                        unitcounter = unitcounter + wildAnimals.length;
                    }
                    else {
                        System.arraycopy(wildAnimals, 0, arrbig, unitcounter, wildAnimals.length);
                        unitcounter = unitcounter + wildAnimals.length;
                    }
                    break;
                case LESSON_PETS:
                    if(ilooper ==0) {
                        System.arraycopy(pets, 0, arrbig, 0, pets.length);
                        unitcounter = unitcounter + pets.length;}
                    else {
                        System.arraycopy(pets, 0, arrbig, unitcounter, pets.length);
                        unitcounter = unitcounter + pets.length;
                    }
                    break;
                case LESSON_BIRDS:
                    if(ilooper ==0) {
                        System.arraycopy(birds, 0, arrbig, 0, birds.length);
                        unitcounter = unitcounter + birds.length;}
                    else {
                        System.arraycopy(birds, 0, arrbig, unitcounter, birds.length);
                        unitcounter = unitcounter + birds.length;
                    }
                    break;
                case LESSON_BODY_PARTS:
                    if(ilooper ==0) {
                        System.arraycopy(bodyParts, 0, arrbig, 0, bodyParts.length);
                        unitcounter = unitcounter + bodyParts.length;
                    }
                    else {
                        System.arraycopy(bodyParts, 0, arrbig, unitcounter, bodyParts.length);
                        unitcounter = unitcounter + bodyParts.length;
                    }
                    break;
                case LESSON_VEHICLES:
                    if(ilooper ==0) {
                        System.arraycopy(vehicles, 0, arrbig, 0, vehicles.length);
                        unitcounter = unitcounter + vehicles.length;
                    }
                    else {
                        System.arraycopy(vehicles, 0, arrbig, unitcounter, vehicles.length);
                        unitcounter = unitcounter + vehicles.length;
                    }
                    break;
                case LESSON_SHAPES:
                    if(ilooper ==0) {
                        System.arraycopy(shapes, 0, arrbig, 0, shapes.length);
                        unitcounter = unitcounter + shapes.length;
                    }
                    else {
                        System.arraycopy(shapes, 0, arrbig, unitcounter, shapes.length);
                        unitcounter = unitcounter + shapes.length;
                    }
                    break;
                default:
                    if(ilooper ==0) {
                        System.arraycopy(fruits, 0, arrbig, 0, fruits.length);
                        unitcounter = unitcounter + fruits.length;}
                    else {
                        System.arraycopy(fruits, 0, arrbig, unitcounter, fruits.length);
                        unitcounter = unitcounter + fruits.length;
                    }
                    break;
            }
        }
            Log.e("bigaray10= ",arrbig[10]+"  length=  "+arrbig.length+ "unit counter =  "+ unitcounter);
        chosenLesson = arrbig;
        chosenLesssonLength = unitcounter -1;
            Log.e("chosenlesson= ", chosenLesson[10]+"length = "+ chosenLesson.length);

//        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
//        String temp1 = mlesson1.getText().toString();

        EditText mspeed = (EditText) findViewById(R.id.speed);
        String tempspeed = mspeed.getText().toString();  // Gets the text value from that pointer (user input)
        if ( !tempspeed.isEmpty()) {
            Log.e("flash_card", " displaytime =  "+displaytime + " input from user = "+ tempspeed);
            displaytime = Integer.parseInt(tempspeed);
        } else {
            // toast a warning about default?
            displaytime = 3;
        }
        Log.e("flash_card", " displaytime =  "+displaytime);
        if (displaytime == 0) {displaytime =3;} //default time to be 3 sec

        setContentView(R.layout.activity_main);
        stopCounter = 0;

        mHandler = new Handler();
        mHandler.post(mUpdate);
    }
}

