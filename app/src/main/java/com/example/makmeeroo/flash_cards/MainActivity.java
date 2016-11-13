package com.example.makmeeroo.flash_cards;

//android:src="@drawable/art"

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.makmeeroo.flash_cards.DisplayData.DisplayDataUser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public static final String LESSON_W_BODY_PARTS = "w_bodyParts";
    public static final String LESSON_W_ACTIONS = "w_actions";
    public static final String LESSON_W_WILDANIMALS = "w_wildAnimals";
    public static final String LESSON_W_FRUITS = "w_fruits";
    public static final String LESSON_W_VEHICLES = "w_vehicles";
    public static final String LESSON_W_PETS = "w_pets";
    public static final String LESSON_W_BIRDS = "w_birds";

    int stopCounter = 0;
    private Handler mHandler;
    long displayMinTime = 2000; // milliseconds

    String[] masterlistofLessons = {LESSON_WILD_ANIMALS, LESSON_FRUITS, LESSON_VEHICLES, LESSON_PETS, LESSON_BIRDS, LESSON_BODY_PARTS,
            LESSON_SHAPES, LESSON_HOME, LESSON_VEGETABLES, LESSON_DINNER, LESSON_PARK, LESSON_CLOTHES, LESSON_BEACH,
            LESSON_W_BODY_PARTS, LESSON_W_ACTIONS, LESSON_W_WILDANIMALS, LESSON_W_FRUITS, LESSON_W_VEHICLES, LESSON_W_PETS, LESSON_W_BIRDS};
    int[] selectedvalueofLessons = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
    String[] birds = {"butterfly", "duck", "eagle", "flamingo", "hawk", "hen", "ostrich", "owl", "parrot", "sea_gull", "sparrow"};
    String[] bodyParts = {"eyes", "nose", "mouth", "teeth", "tongue", "chin", "ear", "head", "hair", "fingers", "hands", "feet", "shoulders"};
    String[] shapes = {"circle", "diamond", "hexagon", "rectangle", "square", "triangle"};
    String[] home = {"carpet", "chair", "clock", "curtains", "cycle", "fireplace", "lamp", "potty_seat", "sofa", "speaker", "table", "tv"};
    String[] vegetables = {"carrot", "cauliflower", "eggplant", "green_beans", "lemon", "onion", "peas", "pepper", "potato", "pumpkin", "tomato"};
    String[] dinner = {"table", "chair", "spoon", "fork", "bowl", "bib", "pasta", "milk", "high_chair"};
    String[] park = {"bench", "kite", "slide", "swing", "spinner", "roller_slide", "sand_pit", "tree"};
    String[] clothes = {"diaper", "shorts", "shirt", "bib", "shoes", "socks", "jacket", "towel", "bag"};
    String[] beach = {"fish", "pail", "crab", "shell", "umbrella", "beach_towel", "sea_gull", "surfboard", "star_fish",};

    String[] w_bodyParts = {"_eyes", "_nose", "_mouth", "_teeth", "_tongue", "_chin", "_ear", "_head", "_hair", "_fingers", "_hands", "_feet", "_shoulders"};
    String[] w_actions = {"_sit", "_stand", "_come", "_go", "_walk", "_run", "_jump", "_stop"};
    String[] w_wildAnimals = {"_bear", "_bison", "_butterfly", "_cheetah", "_crab", "_crocodile", "_deer", "_dolphin", "_elephant", "_fox", "_giraffe", "_hippo",
            "_horse", "_kangaroo", "_koala", "_lion", "_monkey", "_octopus", "_orangutan", "_panda", "_rhinoceros", "_shark", "_sheep", "_snake", "_tiger",
            "_turtle", "_wolf", "_zebra"};
    String[] w_fruits = {"_apple", "_apricot", "_banana", "_grapes", "_jack_fruit", "_orange", "_papaya", "_peach", "_pear", "_plum", "_strawberry", "_watermelon",};
    String[] w_vehicles = {"_ambulance", "_boat", "_bus", "_car", "_fire_engine", "_helicopter", "_motorcycle", "_plane", "_rocket", "_tractor", "_train", "_truck", "_van"};
    String[] w_pets = {"_cat", "_cow", "_dog", "_pig", "_rabbit"};
    String[] w_birds = {"_butterfly", "_duck", "_eagle", "_flamingo", "_hawk", "_hen", "_ostrich", "_owl", "_parrot", "_sea_gull", "_sparrow"};

    String[] chosenLesson = pets;
    int chosenLesssonLength = chosenLesson.length;
    int nooflessons;
    String wordMarker = "_";

    // added for memory game
    int memoryGame = 1;
    int[] last_x = new int[20];
    int count_last_x = 0;
    Random r1 = new Random();
    int card1, card2, card3, badvariable, memGameIncorrectAnswer;
    int gameFrequency = 5;

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
            if (currentDisplayTime < displayMinTime) // too short
                currentDisplayTime = displayMinTime;

            if (stopCounter < chosenLesssonLength) {
                mHandler.postDelayed(this, currentDisplayTime);
                Log.e("Request made for ", chosenLesson[stopCounter]);
                if (memoryGame == 1) {
                    memoryStore(stopCounter, gameFrequency, currentDisplayTime);
                }
                stopCounter++;
                if (stopCounter == chosenLesssonLength) {
                    stopCounter = 0;
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
            temp2.setText(selectedWord.replaceAll("_", " "));
        }
    }

    public void nextImage(int counter) {
        // TODO if picture file is not available, then move to the next picture
        //TODO add some basic animation
        ImageView temp1 = (ImageView) findViewById(R.id.imageView1);
        TextView temp3 = (TextView) findViewById(R.id.textReading);
        String selectedWord = chosenLesson[counter];

        if ((selectedWord.substring(0, 1)).equals(wordMarker)) {
            temp3.setVisibility(View.VISIBLE);
            temp1.setVisibility(View.INVISIBLE);
            temp3.setText(selectedWord.replaceAll("_", " "));
        } else {
            temp1.setVisibility(View.VISIBLE);
            temp3.setVisibility(View.INVISIBLE);
            String selectedPicture = "@drawable/" + chosenLesson[counter];
            int pic_id = getResources().getIdentifier(selectedPicture, null, getPackageName()); // get the location of where l1, l2, etc are stored
            //Drawable pic = getResources().getDrawable(pic_id);  // take picture from that location and save it in a drawable pic
            Drawable pic = ContextCompat.getDrawable(this, pic_id); // http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22

            temp1.setImageDrawable(pic);        //display pic in the image
            Log.e("selected picture = ", selectedPicture);
        }
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
            Log.e("voice file found ", voiceFile + soundLength);
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
                mHandler.removeCallbacksAndMessages(null); // fix this to stop only mUpdate; also stop media player first (perhaps add a function for mHandler)
                if (pronouncePlay.isPlaying()) {
                    pronouncePlay.reset();
                }
                if (t1.isSpeaking())
                    t1.stop();
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
        CheckBox temp1 = (CheckBox) findViewById(R.id.memoryGameCheckBox);
        if (memoryGame == 1) {
            temp1.setChecked(true);
        } else {
            temp1.setChecked(false);
        }
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
        position = 0;
    }

    public void selectionDone(View v) {
        int j = 0; // TODO rename j to numSelected
        int numClicked = position;
        int numSelected = 0;
        position = 0;

        int[] positionArray = new int[numClicked];
        for (int i = 0; i < numClicked; i++)
            positionArray[i] = -1; // -1 means no lesson in that position
        for (int i = 0; i < masternoofLessons; i++) {
            if (selectedvalueofLessons[i] > 0) {
                numSelected++;
                positionArray[selectedvalueofLessons[i] - 1] = i;
                Log.e("posArray", "[" + (selectedvalueofLessons[i] - 1) + "] = " + i);
            }
        }

        for (int i = 0; i < numClicked; i++) {
            if (positionArray[i] >= 0) {// valid lesson at this position
                int cardIndex = positionArray[i];
                System.arraycopy(masterlistofLessons, cardIndex, selectedlistofLessons, j, 1);
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

        strtoArrMaps.put(LESSON_W_BODY_PARTS, w_bodyParts);
        strtoArrMaps.put(LESSON_W_ACTIONS, w_actions);
        strtoArrMaps.put(LESSON_W_WILDANIMALS, w_wildAnimals);
        strtoArrMaps.put(LESSON_W_FRUITS, w_fruits);
        strtoArrMaps.put(LESSON_W_VEHICLES, w_vehicles);
        strtoArrMaps.put(LESSON_W_PETS, w_pets);
        strtoArrMaps.put(LESSON_W_BIRDS, w_birds);

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
        if (temp1.isChecked()) {
            memoryGame = 1;
        } else {
            memoryGame = 0;
        }
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
        last_x[count_last_x] = counter;
        Log.e("stored card " + count_last_x + " = ", last_x[count_last_x] + " i.e. " + chosenLesson[last_x[count_last_x]]);
        if (count_last_x != frequency - 1) {
            count_last_x = (count_last_x + 1) % frequency;
        } else {
            // cancel next slide in activity_main
            mHandler.removeCallbacksAndMessages(null);
            Log.e("removed callbacks", "");

            // go to memory1 layout after some delay
            Runnable memoryGameObj = new memoryGameRunnable(frequency);
            mHandler.postDelayed(memoryGameObj, timeToWaitBeforeStartingGame);
        }
    }

    public void memoryGame1(View v) {
        String clickedCard = (String) v.getTag();
        int tagValue = Integer.parseInt(clickedCard);
        String veryGood = "clapclapclap";
        String tryAgain = "Try again. Which one is " + chosenLesson[last_x[card3]] + "???";
        tryAgain = tryAgain.replaceAll("_", " ");
        String giveTheAnswer = "Thanks for trying. This is " + chosenLesson[last_x[card3]] + "!";
        giveTheAnswer = giveTheAnswer.replaceAll("_", " ");
        //TODO - need to stop the current playing message once user clicks

        int endTheGame = 0;

        if (tagValue == badvariable) {
            Log.e("correct ", clickedCard);

            int resID = this.getResources().getIdentifier(veryGood, "raw", this.getPackageName());
            pronouncePlay = MediaPlayer.create(this, resID);
            pronouncePlay.start();
            long soundLength1 = pronouncePlay.getDuration();
            setContentView(R.layout.activity_main);
            nextImage(last_x[card3]);
            nextTextMessage(last_x[card3]);
            //t1.speak(veryGood, TextToSpeech.QUEUE_FLUSH, null);
            mHandler.postDelayed(mUpdate, soundLength1 + 500);
            //setContentView(R.layout.activity_main);
        } else {
            Log.e("incorrect", clickedCard);
            t1.speak(tryAgain, TextToSpeech.QUEUE_FLUSH, null);
            memGameIncorrectAnswer++;
        }
        if (memGameIncorrectAnswer >= 2) {
            endTheGame = 1;
            Log.e("... moving on", clickedCard);
            t1.speak(giveTheAnswer, TextToSpeech.QUEUE_FLUSH, null);
        }
        if (endTheGame == 1) {
            setContentView(R.layout.activity_main);
            nextImage(last_x[card3]);
            nextTextMessage(last_x[card3]);
            mHandler.postDelayed(mUpdate, 2 * displayMinTime);
        }
    }

    public class memoryGameRunnable implements Runnable { // http://stackoverflow.com/questions/9123272/is-there-a-way-to-pass-parameters-to-a-runnable
        private int m_frequency;

        public memoryGameRunnable(int _data) {
            this.m_frequency = _data;
        }

        @Override
        public void run() {
            count_last_x = 0;
            card1 = r1.nextInt(m_frequency);
            card2 = r1.nextInt(m_frequency);
            while (card1 == card2) {
                card2 = r1.nextInt(m_frequency);
            }
            if (r1.nextInt(2) == 0) {
                card3 = card1;
                badvariable = 0;
            } else {
                card3 = card2;
                badvariable = 1;
            }
            Log.e("r1= " + card1, "; r2 = " + card2 + "; sel = " + card3);
            Log.e("c1 = " + last_x[card1], "; c2 = " + last_x[card2] + "; sel = " + last_x[card3]);
            Log.e("c1 = " + chosenLesson[last_x[card1]], "; c2 = " + chosenLesson[last_x[card2]] + "; sel = " + chosenLesson[last_x[card3]]);

            memGameIncorrectAnswer = 0;
            memoryGameUpdatePicture();
            String memoryquestion = "Which one is " + chosenLesson[last_x[card3]];
            memoryquestion = memoryquestion.replaceAll("_", " ");
            Log.e(memoryquestion, " ");

            t1.speak(memoryquestion, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void memoryGameUpdatePicture() {
        setContentView(R.layout.memory1);

        String memoryPicture1 = "@drawable/" + chosenLesson[last_x[card1]];
        int pic1_id = getResources().getIdentifier(memoryPicture1, null, getPackageName());
        Drawable pic1 = ContextCompat.getDrawable(this, pic1_id);
        ImageView leftImage = (ImageView) findViewById(R.id.memory1left);
        leftImage.setImageDrawable(pic1);

        String memoryPicture2 = "@drawable/" + chosenLesson[last_x[card2]];
        int pic2_id = getResources().getIdentifier(memoryPicture2, null, getPackageName());
        Drawable pic2 = ContextCompat.getDrawable(this, pic2_id);
        ImageView rightImage = (ImageView) findViewById(R.id.memory1right);
        rightImage.setImageDrawable(pic2);

    }


    public void dbasetest (View v) {
        setContentView(R.layout.databasecreate);
    }

    //TODO: Should we set onclicklistener to the buttons of the memory game?

    public void gridViewtest(View v) {
        mHandler.removeCallbacksAndMessages(null);
        setContentView(R.layout.usersetting_v2);

//        List<String> dummy1 = Arrays.asList("Wild Animals", "Pets", "Fruits", "Vehicles","Park", "Beach", "Body","Shapes","Home","Clothes");
//        List<String> dummy2 = Arrays.asList("lion", "dog", "apple", "car","swing", "fish", "nose","circle","sofa","diaper");
//        List<Integer> dummy3 = Arrays.asList(10, 5,5,5,5,5,5,5,5,4);
//        List<Integer> dummy4 = Arrays.asList(3,3,3,3,3,3,3,3,3,3);

        //Create a list of objects of class type DisplayDataUser
//        List<DisplayDataUser> IconList = new ArrayList<>();
//        for(int i=0; i<9; i++) {
//            DisplayDataUser dummy5 = new DisplayDataUser();
//            dummy5.setLessonName(dummy1.get(i));
//            dummy5.setImage(dummy2.get(i));
//            dummy5.setNumberLessons(dummy3.get(i));
//            dummy5.setRating(dummy4.get(i));
//            IconList.add(dummy5);
//        }

        InputStream inputStream = getResources().openRawResource(R.raw.lesson);
        CsvReader csvFile = new CsvReader(inputStream);
        List<DisplayDataUser> IconList = csvFile.read();

        GridView gvdummy = (GridView) findViewById(R.id.gv1);
        // Need an array adapter to take the source into the Gridview
        IconDisplayAdapter adapter = new IconDisplayAdapter(getApplicationContext(), R.layout.unit, IconList);
        gvdummy.setAdapter(adapter);

        //Turn ON item click listener. There seems to be something for scroll listener as well
        gvdummy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position = position of object
            }
        });

    }

    public class IconDisplayAdapter extends ArrayAdapter{
        private List<DisplayDataUser> iconModelList;
        private int resource;
        private LayoutInflater inflater;
        public IconDisplayAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            iconModelList = objects;
            this.resource= resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        // getView class is required by the gridview display
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = inflater.inflate(R.layout.unit, null);
            }

            ImageView iconPicture = (ImageView) convertView.findViewById(R.id.iconPicture);
            TextView iconText = (TextView) convertView.findViewById(R.id.iconText);
            RatingBar iconRating = (RatingBar) convertView.findViewById(R.id.iconRating);

            iconText.setText(iconModelList.get(position).getLessonName()+" ("+ iconModelList.get(position).getNumberLessons()+")");
            iconRating.setRating(iconModelList.get(position).getRating());

            String pic = "@drawable/" + iconModelList.get(position).getImage();
            int pic_id = getResources().getIdentifier(pic, null, getPackageName()); // get the location of where l1, l2, etc are stored
//            //Drawable pic = getResources().getDrawable(pic_id);  // take picture from that location and save it in a drawable pic
            Drawable pic2 = getResources().getDrawable(pic_id); // http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
            iconPicture.setImageDrawable(pic2);        //display pic in the image

            return convertView;
        }

    }
}