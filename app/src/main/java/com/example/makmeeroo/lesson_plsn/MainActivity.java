package com.example.makmeeroo.lesson_plsn;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.lang.*;

public class MainActivity extends AppCompatActivity {

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

    String[] chosenLesson = fruits;
    int chosenLesssonLength = chosenLesson.length -1;
    int nooflessons;
    String[] lessonlist = new String[5];

    MediaPlayer pronouncePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("t11", "t1");
        mHandler = new Handler();
        mHandler.post(mUpdate);
    }

    private Runnable mUpdate = new Runnable() {
        public void run() {
            Log.e("error", "check stopCounter " + stopCounter);
            nextTextMessage(stopCounter);
            nextImage(stopCounter);
            if (stopCounter<chosenLesssonLength)
                mHandler.postDelayed(this, displaytime*1000);
            stopCounter ++;
        }
    };

    public void nextTextMessage(int counter){
        String selectedWord= chosenLesson[counter];
            Log.e("selectedWord","  "+ selectedWord);
        TextView temp2 = (TextView) findViewById(R.id.textView);
        temp2.setText(selectedWord);
    }

    public void nextImage(int counter) {
        String selectedPicture = "@drawable/" + chosenLesson[counter];
        int pic_id = getResources().getIdentifier(selectedPicture, null, getPackageName()); // get the location of where l1, l2, etc are stored
        Drawable pic = getResources().getDrawable(pic_id);  // take picture from that location and save it in a drawable pic
        ImageView temp1 = (ImageView) findViewById(R.id.imageView1);
        temp1.setImageDrawable(pic);        //display pic in the image

        String voiceFile = chosenLesson[counter];
        int resID = this.getResources().getIdentifier(voiceFile, "raw", this.getPackageName());
            Log.e("resid", "resid = " + resID);
        pronouncePlay = MediaPlayer.create(this, resID);
        pronouncePlay.start();
    }

    public void stopresume(View v) {

        Button mStartStop = (Button) findViewById(R.id.button2);
        String temp1 = mStartStop.getText().toString();
        Log.e("startstop = ", temp1);
        switch (temp1) {
            case "Stop":
                mHandler.removeCallbacksAndMessages (null);
                mStartStop.setText("Resume");
                break;
            case "Resume":
                mStartStop.setText("Stop");
                mHandler.post(mUpdate);
                break;
        }
    }

    public void changeselections(View v) {
        mHandler.removeCallbacksAndMessages(null);
        setContentView(R.layout.adjustments);
        nooflessons = 0;
    }

    public void selectFruits(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2= (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);

        if (nooflessons <4) {
            lessonlist[nooflessons] = "fruits";
            switch (nooflessons) {
                case 0: mlesson1.setText("fruits");break;
                case 1: mlesson2.setText("fruits");break;
                case 2: mlesson3.setText("fruits");break;
                case 3: mlesson4.setText("fruits");break;
            }
            nooflessons++;
            Log.e("fruits = ", " "+nooflessons);
        };
    }

    public void selectwildAnimals(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2= (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);

        if (nooflessons <4) {
            lessonlist[nooflessons] = "wildAnimals";
            switch (nooflessons) {
                case 0:mlesson1.setText("wildAnimals");break;
                case 1:mlesson2.setText("wildAnimals");break;
                case 2:mlesson3.setText("wildAnimals");break;
                case 3:mlesson4.setText("wildAnimals");break;
            }
            nooflessons++;
            Log.e("wildanimals = ", " "+nooflessons);
        }
    }

    public void selectpets(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2= (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);
        if (nooflessons <4) {
            lessonlist[nooflessons] = "pets";
            switch (nooflessons) {
                case 0:mlesson1.setText("pets");break;
                case 1:mlesson2.setText("pets");break;
                case 2:mlesson3.setText("pets");break;
                case 3:mlesson4.setText("pets");break;
            }
            nooflessons++;
        }
    }

    public void selectbirds(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2 = (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);
        if (nooflessons <4) {
            lessonlist[nooflessons] = "birds";
            switch (nooflessons) {
                case 0:mlesson1.setText("birds");break;
                case 1:mlesson2.setText("birds");break;
                case 2:mlesson3.setText("birds");break;
                case 3:mlesson4.setText("birds");break;
            }
            nooflessons++;
        }
    }

    public void selectbodyParts(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2= (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);
        if (nooflessons <4) {
            lessonlist[nooflessons] = "bodyParts";
            switch (nooflessons) {
                case 0:mlesson1.setText("bodyParts");break;
                case 1:mlesson2.setText("bodyParts");break;
                case 2:mlesson3.setText("bodyParts");break;
                case 3:mlesson4.setText("bodyParts");break;
            }
            nooflessons++;
        }
    }

    public void selectvehicles(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2= (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);
        if (nooflessons <4) {
            lessonlist[nooflessons] = "vehicles";
            switch (nooflessons) {
                case 0:mlesson1.setText("vehicles");break;
                case 1:mlesson2.setText("vehicles");break;
                case 2:mlesson3.setText("vehicles");break;
                case 3:mlesson4.setText("vehicles");break;
            }
            nooflessons++;
        }
    }

    public void selectshapes(View v) {
        EditText mlesson1 = (EditText) findViewById(R.id.lesson1);
        EditText mlesson2= (EditText) findViewById(R.id.lesson2);
        EditText mlesson3 = (EditText) findViewById(R.id.lesson3);
        EditText mlesson4 = (EditText) findViewById(R.id.lesson4);
        EditText mlesson5 = (EditText) findViewById(R.id.lesson5);
        if (nooflessons <4) {
            lessonlist[nooflessons] = "shapes";
            switch (nooflessons) {
                case 0:mlesson1.setText("shapes");break;
                case 1:mlesson2.setText("shapes");break;
                case 2:mlesson3.setText("shapes");break;
                case 3:mlesson4.setText("shapes");break;
            }
            nooflessons++;
        }
    }

    public void selectioncomplete(View v) {

        String[] arrbig = new String[100];
        int unitcounter =0;

        if (nooflessons == 0) {                 // in case the user didn't select anything
            nooflessons =1;
            lessonlist[0] = "fruits";
        }
        else {nooflessons = Math.min(4,nooflessons);}

        Log.e("nooflessons = "," "+nooflessons);

        for (int ilooper = 0;  ilooper < nooflessons; ilooper++ ) {
            switch (lessonlist[ilooper]) {
                case "fruits":
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
                case "wildAnimals":
                    if(ilooper ==0) {
                        System.arraycopy(wildAnimals, 0, arrbig, 0, wildAnimals.length);
                        unitcounter = unitcounter + wildAnimals.length;
                    }
                    else {
                        System.arraycopy(wildAnimals, 0, arrbig, unitcounter, wildAnimals.length);
                        unitcounter = unitcounter + wildAnimals.length;
                    }
                    break;
                case "pets":
                    if(ilooper ==0) {
                        System.arraycopy(pets, 0, arrbig, 0, pets.length);
                        unitcounter = unitcounter + pets.length;}
                    else {
                        System.arraycopy(pets, 0, arrbig, unitcounter, pets.length);
                        unitcounter = unitcounter + pets.length;
                    }
                    break;
                case "birds":
                    if(ilooper ==0) {
                        System.arraycopy(birds, 0, arrbig, 0, birds.length);
                        unitcounter = unitcounter + birds.length;}
                    else {
                        System.arraycopy(birds, 0, arrbig, unitcounter, birds.length);
                        unitcounter = unitcounter + birds.length;
                    }
                    break;
                case "bodyParts":
                    if(ilooper ==0) {
                        System.arraycopy(bodyParts, 0, arrbig, 0, bodyParts.length);
                        unitcounter = unitcounter + bodyParts.length;
                    }
                    else {
                        System.arraycopy(bodyParts, 0, arrbig, unitcounter, bodyParts.length);
                        unitcounter = unitcounter + bodyParts.length;
                    }
                    break;
                case "vehicles":
                    if(ilooper ==0) {
                        System.arraycopy(vehicles, 0, arrbig, 0, vehicles.length);
                        unitcounter = unitcounter + vehicles.length;
                    }
                    else {
                        System.arraycopy(vehicles, 0, arrbig, unitcounter, vehicles.length);
                        unitcounter = unitcounter + vehicles.length;
                    }
                    break;
                case "shapes":
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
        displaytime = Integer.parseInt(tempspeed);
        Log.e("displaytime = ", "  "+displaytime);
        if (displaytime == 0) {displaytime =3;} //default time to be 3 sec

        setContentView(R.layout.activity_main);
        stopCounter = 0;

        mHandler = new Handler();
        mHandler.post(mUpdate);
    }
}

