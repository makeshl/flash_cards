package com.example.makmeeroo.lesson_plsn;

//android:src="@drawable/art"

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
    public static final String LESSON_CLOTHES = "clothes";
    public static final String LESSON_BEACH = "beach";
    int stopCounter = 0;
    private Handler mHandler;
    long displayMinTime = 2000; // milliseconds

    String[] masterlistofLessons = {LESSON_WILD_ANIMALS, LESSON_FRUITS, LESSON_VEHICLES, LESSON_PETS, LESSON_BIRDS, LESSON_BODY_PARTS,
            LESSON_SHAPES, LESSON_HOME, LESSON_VEGETABLES, LESSON_DINNER, LESSON_PARK, LESSON_CLOTHES,LESSON_BEACH};
    int[] selectedvalueofLessons = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] selectedlistofLessons = new String[3];
    int masternoofLessons = masterlistofLessons.length;
    int position;
    String[] fullList = new String[100];

    String[] wildAnimals = {"bear", "bison", "butterfly", "cheetah", "crab", "crocodile", "deer", "dolphin", "elephant", "fox", "giraffe", "hippo",
            "horse", "kangaroo", "koala", "lion", "monkey", "octopus", "orangutan", "panda", "rhinoceros", "shark", "sheep", "snake", "tiger",
            "turtle", "wolf", "zebra"};
    String[] fruits = {"apple", "apricot", "banana", "grapes", "jack_fruit", "orange", "papaya", "peach", "pear", "plum", "strawberry", "watermelon",};
    String[] vehicles = {"ambulance", "boat", "bus", "car", "fire_engine", "helicopter", "motorcycle", "plane", "rocket", "tractor", "train", "truck", "van"};
    String[] pets = {"cat", "cow", "dog", "pig", "rabbit"};
    String[] birds = {"butterfly","duck","eagle", "flamingo", "hawk", "hen", "ostrich","owl", "parrot","sea_gull", "sparrow"};
    String[] bodyParts = {"eyes", "nose", "mouth", "teeth", "tongue", "chin", "ear", "head", "hair", "fingers", "hands", "feet", "shoulders"};
    String[] shapes = {"circle", "diamond", "hexagon", "rectangle", "square", "triangle"};
    String[] home = {"carpet", "chair", "clock", "curtains","cycle", "fireplace", "lamp", "potty_seat", "sofa", "speaker", "table", "tv"};
    String[] vegetables = {"carrot", "cauliflower", "eggplant", "green_beans", "lemon", "onion", "peas", "pepper", "potato", "pumpkin", "tomato"};
    String[] dinner = {"table", "chair", "spoon", "fork", "bowl", "bib", "pasta", "milk", "high_chair"};
    String[] park = {"bench","kite", "slide", "swing", "spinner", "roller_slide", "sand_pit","tree" };
    String[] clothes = {"diaper","shorts","shirt","bib","shoes","socks","jacket","towel","bag"};
    String[] beach = {"fish","pail","crab","shell","umbrella","beach_towel","sea_gull","surfboard","star_fish",};

    String[] chosenLesson = pets;
    int chosenLesssonLength = chosenLesson.length;
    int nooflessons;

    // added for memory game
    int memoryGame =1;
    String[] last_x = new String[20];
    int count_last_x = 0;
    Random r1 = new Random();
    int card1, card2, card3, badvariable;
    int gameFrequency =5;

    MediaPlayer pronouncePlay;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.e("flash_card", " arrived at onCreate");

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Log.e("entered", " tts object");
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(0.75f);
                    t1.setPitch(1.0f);
                }
            }
        });

        mHandler = new Handler();
        mHandler.post(mUpdate);
    }

    private Runnable mUpdate = new Runnable() {
        public void run() {
            Log.e("flash_card", " mUpdate called. stopCounter = " + stopCounter);
            setContentView(R.layout.activity_main);
            nextTextMessage(stopCounter);
            nextImage(stopCounter);
            double soundLength = nextSound(stopCounter);
            long currentDisplayTime = (long) (soundLength + 500); // add 0.5 seconds margin
            if (currentDisplayTime<displayMinTime) // too short
                currentDisplayTime= displayMinTime;

            if (stopCounter < chosenLesssonLength ) {
                mHandler.postDelayed(this, currentDisplayTime);
                Log.e("Request made for ", chosenLesson[stopCounter]);
                if (memoryGame == 1) {
                    memoryStore(stopCounter, gameFrequency, currentDisplayTime);
                }
                stopCounter++;
                if (stopCounter == chosenLesssonLength) {
                    stopCounter = 0;
                    count_last_x = 0;
                } // Added for continuous looping
            }
        }
    };

    public void nextTextMessage(int counter) {
        String selectedWord = chosenLesson[counter];
        Log.e("flash_card ", " selectedWord " + selectedWord);
        //TODO Show lessons name while switching lesson
        if (!selectedWord.isEmpty()) {
            TextView temp2 = (TextView) findViewById(R.id.textView);
            temp2.setText(selectedWord);
        }
    }

    public void nextImage(int counter) {
        // TODO if picture file is not available, then move to the next picture
        //TODO add some basic animation
        String selectedPicture = "@drawable/" + chosenLesson[counter];
        int pic_id = getResources().getIdentifier(selectedPicture, null, getPackageName()); // get the location of where l1, l2, etc are stored
        //Drawable pic = getResources().getDrawable(pic_id);  // take picture from that location and save it in a drawable pic
        Drawable pic = ContextCompat.getDrawable(this, pic_id); // http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
        ImageView temp1 = (ImageView) findViewById(R.id.imageView1);
        temp1.setImageDrawable(pic);        //display pic in the image
        Log.e("selected picture = ", selectedPicture);
    }

    public double nextSound(int counter) {

        String voiceFile = chosenLesson[counter];

        int resID = this.getResources().getIdentifier(voiceFile, "raw", this.getPackageName());
        Log.e("flash_card ", "resID = " + resID);

        double soundLength = 0;
        if (resID == 0) {
            Log.e("voice file not found ", voiceFile);
            voiceFile = voiceFile.replaceAll("_", " ");
            t1.speak(voiceFile, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            Log.e("voice file found ", voiceFile);
            pronouncePlay = MediaPlayer.create(this, resID);
            pronouncePlay.start();
            soundLength = pronouncePlay.getDuration(); // returns duration in milliseconds
            Log.e("voice file found ",voiceFile + soundLength);
        }
        Log.e("Finished playing ", voiceFile);

        return soundLength;
// TODO debug silent cat problem
// TODO check voice file length, and set time based on that
// TODO use voice if available, if not use text to speech - COMPLETE
// TODO MediaPlayer finalized without being released
    }

    public void stopresume(View v) {

        Button mStartStop = (Button) findViewById(R.id.button2);
        String temp1 = mStartStop.getText().toString();
        Log.e("flash_card", "startstop = " + temp1);
        switch (temp1) {
            case STOP_STRING:
                mHandler.removeCallbacksAndMessages (null); // fix this to stop only mUpdate; also stop media player first (perhaps add a function for mHandler)
                if (pronouncePlay.isPlaying()) {
                    pronouncePlay.reset();
                }
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
        for (int i = 0; i < selectedvalueofLessons.length; i++) {
            selectedvalueofLessons[i] = 0;
        }
        nooflessons = 0;
    }

    public void selectSet(View v) {
        String clickedLesson = (String) v.getTag();
        int tagValue = Integer.parseInt(clickedLesson) - 1000;
        Log.e("clciked Tag =", " " + tagValue);

        position++;
        float alpha = v.getAlpha();
        if (alpha == 1) {
            alpha = alpha / 2;
        } else {
            alpha = alpha * 2;
        }
        v.setAlpha(alpha);

        if (alpha != 1) {
            selectedvalueofLessons[tagValue] = position;// TODO use the saved position
        } else {
            selectedvalueofLessons[tagValue] = 0;
        }
        ;
        Log.e("tag= " + tagValue + ";a = " + alpha + ";pos= " + position, "selectedvalue[" + tagValue + "] =" + selectedvalueofLessons[tagValue]);
    }

    public void selectionReset(View v) {
        int[] picIds = new int[]{R.id.image1000, R.id.image1001, R.id.image1002, R.id.image1003, R.id.image1004,
                R.id.image1005, R.id.image1006, R.id.image1007, R.id.image1008, R.id.image1009, R.id.image1010,
                R.id.image1011, R.id.image1012, R.id.image1013, R.id.image1014, R.id.image1015, R.id.image1016,
                R.id.image1017, R.id.image1018, R.id.image1019, R.id.image1020};
        for (int i = 0; i < 21; i++) {
            ImageView temp1 = (ImageView) findViewById(picIds[i]);
            temp1.setAlpha(1.0f);
        }
        for (int i = 0; i < selectedvalueofLessons.length; i++) {
            selectedvalueofLessons[i] = 0;
        }
    }

    public void selectionDone(View v) {
        int j = 0;
        position = 0;
        for (int i = 0; i < masternoofLessons; i++) {
            Log.e("masterlistofLessons = " + masterlistofLessons[i], "itemp2 = " + i);
            if (selectedvalueofLessons[i] > 0) {// TODO use position here
                System.arraycopy(masterlistofLessons, i, selectedlistofLessons, j, 1);
                Log.e("slctdlistofLe[" + j + "] = " + selectedlistofLessons[j], "pos = " + j);
                j++;
            }
            Log.e("i= " + i, "j =" + j);
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
        strtoArrMaps.put(LESSON_VEGETABLES, vegetables);
        strtoArrMaps.put(LESSON_DINNER, dinner);
        strtoArrMaps.put(LESSON_PARK, park);
        strtoArrMaps.put(LESSON_CLOTHES, clothes);
        strtoArrMaps.put(LESSON_BEACH, beach);

        int k = 0;
        if (j == 0) {
            System.arraycopy(fruits, 0, fullList, 0, fruits.length);
            k = fruits.length;
        } else {
            for (int i = 0; i < j; i++) {
                if (i == 0) {
                    System.arraycopy(strtoArrMaps.get(selectedlistofLessons[i]), 0, fullList, 0, strtoArrMaps.get(selectedlistofLessons[i]).length);
                    k = k + strtoArrMaps.get(selectedlistofLessons[i]).length;
                } else {
                    System.arraycopy(strtoArrMaps.get(selectedlistofLessons[i]), 0, fullList, k, strtoArrMaps.get(selectedlistofLessons[i]).length);
                    k = k + strtoArrMaps.get(selectedlistofLessons[i]).length;
                }
            }
        }

        for (int l = 0; l < k; l++) {
            Log.e("fulllist[" + l + "] = ", fullList[l]);
        }
        CheckBox temp1 = (CheckBox) findViewById(R.id.memoryGameCheckBox);
        if (temp1.isChecked()) {memoryGame = 1;} else {memoryGame = 0;}
        Log.e("memory game =", " " + memoryGame);

        setContentView(R.layout.activity_main);
        stopCounter = 0;
        count_last_x = 0;

        chosenLesson = fullList;
        chosenLesssonLength = k;
        Log.e("chosen lesson length =", " " + chosenLesssonLength);

        //mHandler = new Handler();
        mHandler.post(mUpdate);
    }

    public void memoryStore(int counter, int frequency, long timeToWaitBeforeStartingGame) {
        // memory game
        //TODO: remove voice overlap from previous picture
        last_x[count_last_x] = chosenLesson[counter];
        Log.e("stored card " + count_last_x + " = ", last_x[count_last_x]);
        if (count_last_x != frequency -1) {
            count_last_x++;
        } else {
            // cancel next slide in activity_main
            mHandler.removeCallbacksAndMessages(null);
            Log.e("removed callbacks","");

            // go to memory1 layout after some delay
            Runnable memoryGameObj = new memoryGameRunnable( frequency);
            mHandler.postDelayed( memoryGameObj, timeToWaitBeforeStartingGame);
        }
    }

    public void memoryGame1(View v) {
        String clickedCard = (String) v.getTag();
        int tagValue = Integer.parseInt(clickedCard);
        String veryGood = "clapclapclap";
        String tryAgain = "try again";

        if (tagValue == badvariable)
        {
            Log.e("correct ",clickedCard);

            int resID = this.getResources().getIdentifier(veryGood, "raw", this.getPackageName());
            pronouncePlay = MediaPlayer.create(this, resID);
            pronouncePlay.start();
            long soundLength1 = pronouncePlay.getDuration();
            //t1.speak(veryGood, TextToSpeech.QUEUE_FLUSH, null);
            mHandler.postDelayed(mUpdate, soundLength1 +500);
            //setContentView(R.layout.activity_main);
        }
        else {
            Log.e("incorrect",clickedCard);
            t1.speak(tryAgain, TextToSpeech.QUEUE_FLUSH, null);
            //TODO Insert delay here so that voice doesnt overlap
        }
    }

    public class memoryGameRunnable implements Runnable { // http://stackoverflow.com/questions/9123272/is-there-a-way-to-pass-parameters-to-a-runnable
        private int m_frequency;

        public memoryGameRunnable(int _data) {
            this.m_frequency= _data;
        }

        @Override
        public void run() {
            count_last_x = 0;
            card1 = r1.nextInt(m_frequency);
            card2 = r1.nextInt(m_frequency);
            while (card1 == card2) {
                card2 = r1.nextInt(m_frequency);
            }
            if (r1.nextInt(2) == 0) {card3 = card1; badvariable=0;} else {card3 = card2;badvariable=1;}
            Log.e("r1= " + card1, "; r2 = " + card2 + "; sel = " + card3);
            Log.e("c1 = " + last_x[card1], "; c2 = " + last_x[card2] + "; sel = " + last_x[card3]);

            memoryGameUpdatePicture();
            String memoryquestion = "Which one is "+ last_x[card3].replaceAll("_", " ");
            t1.speak(memoryquestion, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void memoryGameUpdatePicture() {
        setContentView(R.layout.memory1);

        String memoryPicture1= "@drawable/" + last_x[card1];
        int pic1_id = getResources().getIdentifier(memoryPicture1, null, getPackageName());
        Drawable pic1 = ContextCompat.getDrawable(this, pic1_id);
        ImageView leftImage = (ImageView) findViewById(R.id.memory1left);
        leftImage.setImageDrawable(pic1);

        String memoryPicture2= "@drawable/" + last_x[card2];
        int pic2_id = getResources().getIdentifier(memoryPicture2, null, getPackageName());
        Drawable pic2 = ContextCompat.getDrawable(this, pic2_id);
        ImageView rightImage = (ImageView) findViewById(R.id.memory1right);
        rightImage.setImageDrawable(pic2);

    }
}
