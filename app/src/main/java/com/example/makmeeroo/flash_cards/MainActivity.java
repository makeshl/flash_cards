package com.example.makmeeroo.flash_cards;

//android:src="@drawable/art"

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makmeeroo.flash_cards.DisplayData.DisplayDataUser;
import com.example.makmeeroo.flash_cards.CsvReaderV2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

// TODO http://www.tutorialspoint.com/java/
public class MainActivity extends Activity {

    List<String> SelectionList = new ArrayList<>();
    List<String> DeckList = new ArrayList<>();
    List<DisplayDataUser> IconList = new ArrayList<>();
    List<DisplayDataUser> IconList2 = new ArrayList<>();
    List<String> ListofAllCards = new ArrayList<>();
    Datadownloader getDatafromURL = new Datadownloader();

    List<Lesson> masterLessonList = new ArrayList<>();



    List<String> Last_x_pictures= new ArrayList<>();
    List<String> Last_x_words= new ArrayList<>();

    public static final String RESUME_STRING = "Resume";
    public static final String STOP_STRING = "Stop";

//  Map<String, String[]> strtoArrMaps = new HashMap<>();
//  strtoArrMaps.put(LESSON_FRUITS, fruits);


    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences preferenceSettings; // = super.getPreferences(PREFERENCE_MODE_PRIVATE);
    private SharedPreferences.Editor preferenceEditor; // = preferenceSettings.edit();
    boolean preferencesCreated = false;

    public void setSharedPreference() {
        preferenceSettings = super.getPreferences(PREFERENCE_MODE_PRIVATE);
        return;
    }
    public SharedPreferences getSharedPreference() {
        return preferenceSettings;
    }
    public SharedPreferences.Editor getPreferenceEditor() {
        return preferenceEditor;
    }
    public void setPreferenceEditor() {
        preferenceEditor = preferenceSettings.edit();
    }

    int stopCounter = 0;
    private Handler mHandler = null;
    private Handler sHandler = null;
    long displayMinTime = 1000; // milliseconds

    int chosenLesssonLength;
    String wordMarker = "_";
    String plusMarker = "!";
    int quiz = 0;

    // added for memory game
    int[] last_x = new int[20];
    int count_last_x = 0;
    Random r1 = new Random();
    int card1, card2, card3, badVariable, memGameIncorrectAnswer;
    int gameFrequency = 5;

    MediaPlayer pronouncePlay = new MediaPlayer();
    //MediaPlayer pronouncePlay = null;
    TextToSpeech textToSpeechObj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Should remove this and make it an async task per android best practices
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        InputStream inputStream = getResources().openRawResource(R.raw.lesson2);
        CsvReader csvFile = new CsvReader(inputStream);
        IconList = csvFile.read();

        // Breaking out spelling lessons into components
        for (int i =0; i< IconList.size(); i++) {

            DisplayDataUser dummyVar = new DisplayDataUser();
            List<String> SpellingList = new ArrayList<>();

            dummyVar.setLessonName(IconList.get(i).getLessonName());
            dummyVar.setImage(IconList.get(i).getImage());
            dummyVar.setNumberLessons(IconList.get(i).getNumberLessons());

            String l1 = IconList.get(i).getLessonName().substring(0, 1);
            //Log.d("+++ lesson Name = " + i + " = " + IconList.get(i).getLessonName(), l1);

            if (l1.equals(plusMarker)) {
                Log.d("!entered here", plusMarker);
                for (int j = 0; j < IconList.get(i).getCards().size(); j++) {
                    String tempWord = IconList.get(i).getCards().get(j);
                    SpellingList.add(wordMarker+tempWord.substring(1,tempWord.length()));
                    for (int k = 2;k<=tempWord.length(); k++){
                        SpellingList.add(plusMarker + tempWord.substring(1,k));
                    }
                    SpellingList.add(wordMarker+tempWord.substring(1,tempWord.length()));
                }
                dummyVar.setCards(SpellingList);
            }
            else {
                dummyVar.setCards(IconList.get(i).getCards());
            }

            IconList2.add(dummyVar);
        }

        for (int i =0; i< IconList2.size(); i++){
            for (int j=0;j< IconList2.get(i).getCards().size(); j++) {
                Log.d("++ row "+i+ " word" + j+ " ", IconList2.get(i).getCards().get(j));
            }
        }

        IconList = IconList2;

        // Added 1/15 to create a new instance of lessons (and cards) with more parameters
//        InputStream inputStream2 = getResources().openRawResource(R.raw.lesson2);
//        CsvReaderV2 csvFilev2 = new CsvReaderV2(inputStream2);
//        masterLessonList = csvFilev2.read();

        ///*
        setSharedPreference();
        setPreferenceEditor();
        SharedPreferences.Editor prefEditor = getPreferenceEditor();
        // test commit
        Boolean prefExist = true;
        prefEditor.putBoolean("prefExist",prefExist);
        prefEditor.apply();

        SharedPreferences prefSettings = getSharedPreference();
        Boolean testWrite = prefSettings.getBoolean("prefExist",false);
        Log.d("flash_cards", "testWrite is " + testWrite);
         //*/

        for (int i =0; i< IconList.size(); i++){
            for (int j=0;j< IconList.get(i).getCards().size(); j++) {
                ListofAllCards.add(IconList.get(i).getCards().get(j));
            }
        }
        SelectionList.add(IconList.get(0).getLessonName());
        updateDeckList();
    }

    @Override
    protected void onPause() {
        super.onPause();

//        SharedPreferences.Editor prefEditor = getPreferenceEditor();
//        // test commit
//        Boolean prefExist = true;
//        prefEditor.putBoolean("prefExist",prefExist);
//        prefEditor.apply();

        SharedPreferences prefSettings = getSharedPreference();
        Boolean testWrite = prefSettings.getBoolean("prefExist", false);
        Log.d("flash_cards", "onPause: testWrite is " + testWrite);

        // release media player
        stopMediaPlayers();

        // cancel other posts, and launch afresh
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        Log.d("flash_cards", "onPause - remove callbacks on main handler");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("flash_cards", "onResume is called");

        if ( textToSpeechObj == null )
            textToSpeechObj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        Log.d("flash_cards", "entered tts object");
                        textToSpeechObj.setLanguage(Locale.US);
                        textToSpeechObj.setSpeechRate(0.75f);
                        textToSpeechObj.setPitch(1.0f);
                        Log.d("flash_cards", "tts initiated ");
                    }
                }
            });
        Log.d("flash_cards", "onResume textToSpeech created ");

        if (mHandler==null) {
            mHandler = new Handler();
            Log.d("flash_cards", "onResume - create main handler");
        }
        else {        // cancel other posts, and launch afresh
            mHandler.removeCallbacksAndMessages(null);
            Log.d("flash_cards", "onResume - remove callbacks on main handler");
        }
        mHandler.post(mUpdate);
        //mHandler.postDelayed(mUpdate, displayMinTime);
    }

    private Runnable mUpdate = new Runnable() {
        public void run() {
            Log.d("flash_cards", " mUpdate called. stopCounter = " + stopCounter);
            setContentView(R.layout.activity_main);

            nextTextMessage(stopCounter);
            nextImage(stopCounter);
            double soundLength = nextSound(stopCounter);

            long currentDisplayTime = (long) (soundLength); // add 0.5 seconds margin
            if (currentDisplayTime < displayMinTime) // too short
                currentDisplayTime = displayMinTime;

            if (stopCounter < chosenLesssonLength) {
                mHandler.postDelayed(this, currentDisplayTime);
                Log.d("flash_cards", "Request made for " + DeckList.get(stopCounter));
                if (quiz == 1) {
                    memoryStore(stopCounter, gameFrequency, currentDisplayTime);
                }
                stopCounter++;
                if (stopCounter == chosenLesssonLength) {
                    stopCounter = 0;
                } // Added for continuous looping
            }
        }
    };

    private Runnable sUpdate = new Runnable() {
        public void run() {
        }
    };


    public void nextTextMessage(int counter) {
        String selectedWord = DeckList.get(counter);
        Log.d("flash_cards", " selectedWord " + selectedWord);
        //TODO Show lessons name while switching lesson
        if (!selectedWord.isEmpty()) {
            TextView temp2 = (TextView) findViewById(R.id.textView);
            temp2.setText(selectedWord.replaceAll("_", " "));
        }
    }

    public void nextImage(int counter) {
        // TODO if picture file is not available, then move to the next picture
        //TODO add some basic animation
        ImageViewWithGesture temp1 = (ImageViewWithGesture) findViewById(R.id.imageView1);
        temp1.setMainActivity(this);
        TextView temp3 = (TextView) findViewById(R.id.textReading);
        String selectedWord = DeckList.get(counter);

        if ((selectedWord.substring(0, 1)).equals(wordMarker)) {
            temp3.setVisibility(View.VISIBLE);
            temp1.setVisibility(View.INVISIBLE);
            temp3.setText(selectedWord.replaceAll("_", " "));
        } else if((selectedWord.substring(0, 1)).equals(plusMarker))   {
            temp3.setVisibility(View.VISIBLE);
            temp1.setVisibility(View.INVISIBLE);
            String dummy = selectedWord.replaceAll("!", " ");
            String dummy2 = "<font color=#ff0000>"+dummy.substring(1,dummy.length()-1)+
                    "</font><font color=#000080>"+dummy.substring(dummy.length()-1,dummy.length())+"</font>";
            temp3.setText(Html.fromHtml(dummy2));
        }
        else {
            temp1.setVisibility(View.VISIBLE);
            temp3.setVisibility(View.INVISIBLE);

            String selectedPicture = "@drawable/" + selectedWord;
            int pic_id = getResources().getIdentifier(selectedPicture, null, getPackageName()); // get the location of where l1, l2, etc are stored

            if (pic_id !=0){
                Log.d(selectedWord, " displayed from resource XXX");
                Drawable pic = ContextCompat.getDrawable(this, pic_id); // http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
                temp1.setImageDrawable(pic);

            } else{
                File imgFile = new File(getFilesDir(),selectedWord+".jpg");
                Log.d(selectedWord + " is from loc:XXX ", imgFile.getPath());
                Bitmap bmp = null;
                try {
                    FileInputStream fis = new FileInputStream(imgFile);
                    bmp = BitmapFactory.decodeStream(fis);
                    temp1.setImageBitmap(bmp); //display pic in the image  FileNotFoundException, //http://www.e-nature.ch/tech/saving-loading-bitmaps-to-the-android-device-storage-internal-external/
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d("Bitmap not found XXX", "..");
                }
            }
        }
    }

    public double nextSound(int counter) {

        String voiceFile1 = DeckList.get(counter);
        String voiceFile2; //dummy variable for removing first underscore
        String voiceFile;
        int spelling =0;

        if (voiceFile1.substring(0,1).equals(wordMarker)) {
            voiceFile2 = voiceFile1.substring(1,voiceFile1.length());
        }

        else if (voiceFile1.substring(0,1).equals(plusMarker)) {
            voiceFile2= voiceFile1.substring(voiceFile1.length()-1,voiceFile1.length());
            spelling =1;
        }
        else {
        voiceFile2 = voiceFile1;
        }
        voiceFile = voiceFile2.replaceAll(wordMarker," "); //convert all subsequent underscores to spaces for TTS object to read

        double soundLength = 0;

        File f = new File(getFilesDir(), voiceFile +".mp3");
        String path = f.getPath();

        MediaPlayer player = new MediaPlayer();

        int resID = this.getResources().getIdentifier(voiceFile, "raw", this.getPackageName());
        if (resID !=0){
            Log.d("voice file "+ voiceFile, " is from res XXX" + resID);
            player = MediaPlayer.create(this,resID);
            player.start();
            soundLength = player.getDuration()+500;
            Log.d("Sound length for " + voiceFile," = " + soundLength);
        } else {
            try {
                player.setDataSource(path);
                Log.d("voice file " + voiceFile, " from memory XXX");
                player.prepare();
                player.start();
                soundLength = player.getDuration();
                Log.d("Sound length for " + voiceFile," = " + soundLength);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("voice file " + voiceFile, " not present XXX");
                stopMediaPlayers();
                textToSpeechObj.speak(voiceFile, TextToSpeech.QUEUE_FLUSH, null);
                if (spelling == 1){
                    soundLength =1000;
                } else {
                    soundLength = 1000;
                }
            }
        }
        return soundLength;
    }

    public void stopMediaPlayers () {
        if ((null != pronouncePlay) && pronouncePlay.isPlaying()) {
            pronouncePlay.reset();
            //pronouncePlay.release();
        }
        if ((null != textToSpeechObj) && textToSpeechObj.isSpeaking()) {
            textToSpeechObj.stop();
            Log.d("text2speech", "XXX reset");
        }
    }

    public void slideBack(boolean slideBack) {
        mHandler.removeCallbacksAndMessages(null); // fix this to stop only mUpdate;
        stopMediaPlayers();
        if (slideBack) {
            stopCounter = stopCounter - 2;
            while (stopCounter < 0)
                stopCounter = stopCounter + chosenLesssonLength;
        }
        mHandler.post(mUpdate); // skip fwd one slide
    }

    public void stopResume(View v) {

        Button mStartStop = (Button) findViewById(R.id.button2);
        String temp1 = mStartStop.getText().toString();
        Log.d("flash_cards", "startstop = " + temp1);
        switch (temp1) {
            case STOP_STRING:
                mHandler.removeCallbacksAndMessages(null); // fix this to stop only mUpdate; also stop media player first (perhaps add a function for mHandler)
                stopMediaPlayers();

                mStartStop.setText(RESUME_STRING);
                break;
            case RESUME_STRING:
                mStartStop.setText(STOP_STRING);
                mHandler.post(mUpdate);
                break;
        }
    }

    public void memoryStore(int counter, int frequency, long timeToWaitBeforeStartingGame) {
        // memory game
        //TODO: remove voice overlap from previous picture
        //TODO: make this work for reading as well
        Last_x_pictures.add(DeckList.get(counter));
        last_x[count_last_x] = counter;
        Log.d("flash_cards", "stored card " + Last_x_pictures.get(Last_x_pictures.size() - 1));

        if (count_last_x != frequency - 1) {
            count_last_x = (count_last_x + 1) % frequency;
        } else {
            // cancel next slide in activity_main
            mHandler.removeCallbacksAndMessages(null);
            Log.d("flash_cards", "removed callbacks");
            // go to memory1 layout after some delay
            Runnable memoryGameObj = new memoryGameRunnable(frequency);
            mHandler.postDelayed(memoryGameObj, timeToWaitBeforeStartingGame);
        }
    }

    public void memoryGame1(View v) {
        String clickedCard = (String) v.getTag();
        int tagValue = Integer.parseInt(clickedCard);
        String veryGood = "clapclapclap";
        String tryAgain = "Try again. Which one is " + Last_x_pictures.get(card3) + "???";
        tryAgain = tryAgain.replaceAll("_", " ");
        String giveTheAnswer = "Thanks for trying. This is " + Last_x_pictures.get(card3)+ "!";
        giveTheAnswer = giveTheAnswer.replaceAll("_", " ");
        //TODO - need to stop the current playing message once user clicks

        int endTheGame = 0;

        if (tagValue == badVariable) {
            Log.d("flash_cards", "correct " + clickedCard);

            int resID = this.getResources().getIdentifier(veryGood, "raw", this.getPackageName());
            stopMediaPlayers();
            pronouncePlay = MediaPlayer.create(this, resID);
            pronouncePlay.start();
            long soundLength1 = pronouncePlay.getDuration();
            Last_x_pictures.clear();
            setContentView(R.layout.activity_main);
            nextImage(last_x[card3]);
            nextTextMessage(last_x[card3]);
            mHandler.postDelayed(mUpdate, soundLength1 + 500);
            //textToSpeechObj.speak(veryGood, TextToSpeech.QUEUE_FLUSH, null);
            //setContentView(R.layout.activity_main);
        } else {
            Log.d("flash_cards", "incorrect" + clickedCard);
            stopMediaPlayers();
            textToSpeechObj.speak(tryAgain, TextToSpeech.QUEUE_FLUSH, null);
            memGameIncorrectAnswer++;
        }
        if (memGameIncorrectAnswer >= 2) {
            endTheGame = 1;
            stopMediaPlayers();
            Log.d("flash_cards", "... moving on" + clickedCard);
            textToSpeechObj.speak(giveTheAnswer, TextToSpeech.QUEUE_FLUSH, null);
        }
        if (endTheGame == 1) {
            Last_x_pictures.clear();
            setContentView(R.layout.activity_main);
            stopMediaPlayers();
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
                badVariable = 0;
            } else {
                card3 = card2;
                badVariable = 1;
            }
            Log.d("flash_cards", "r1= " + card1 + "; r2 = " + card2 + "; sel = " + card3);
            Log.d("flash_cards", "c1 = " + last_x[card1] + "; c2 = " + last_x[card2] + "; sel = " + last_x[card3]);
            Log.d("flash_cards", "c1 = " + Last_x_pictures.get(card1) + "; c2 = " + Last_x_pictures.get(card2) + "; sel = " + Last_x_pictures.get(card3));

            memGameIncorrectAnswer = 0;
            memoryGameUpdatePicture();
            String memoryquestion = "Which one is " + Last_x_pictures.get(card3);
            memoryquestion = memoryquestion.replaceAll("_", " ");
            Log.d("flash_cards", memoryquestion);
            stopMediaPlayers();
            textToSpeechObj.speak(memoryquestion, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void memoryGameUpdatePicture() {
        setContentView(R.layout.memory1);

        String memoryPicture1 = "@drawable/" + Last_x_pictures.get(card1);
        int pic1_id = getResources().getIdentifier(memoryPicture1, null, getPackageName());
        Drawable pic1 = ContextCompat.getDrawable(this, pic1_id);
        ImageView leftImage = (ImageView) findViewById(R.id.memory1left);
        leftImage.setImageDrawable(pic1);

        String memoryPicture2 = "@drawable/" + Last_x_pictures.get(card2);
        int pic2_id = getResources().getIdentifier(memoryPicture2, null, getPackageName());
        Drawable pic2 = ContextCompat.getDrawable(this, pic2_id);
        ImageView rightImage = (ImageView) findViewById(R.id.memory1right);
        rightImage.setImageDrawable(pic2);

        RelativeLayout.LayoutParams paramsleft = (RelativeLayout.LayoutParams)leftImage.getLayoutParams();
        RelativeLayout.LayoutParams paramsright = (RelativeLayout.LayoutParams)rightImage.getLayoutParams();

        if (r1.nextInt(2) == 1) {
            paramsleft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsright.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else{
            paramsleft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            paramsright.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        ;
        leftImage.setLayoutParams(paramsleft);
        rightImage.setLayoutParams(paramsright);

    }

    public void userSelections(View v) {
        mHandler.removeCallbacksAndMessages(null);
        stopMediaPlayers();
        setContentView(R.layout.usersetting_v2);

        CheckBox temp1 = (CheckBox) findViewById(R.id.quizcheckBox);
        if (quiz ==1) {
            temp1.setChecked(true);
        } else {
            temp1.setChecked(false);
        }

        final GridView gvdummy = (GridView) findViewById(R.id.gv1);
        // Need an array adapter to take the source into the Gridview

        IconDisplayAdapter adapter = new IconDisplayAdapter(getApplicationContext(), R.layout.unit, IconList);
        gvdummy.setAdapter(adapter);

        //Turn ON item click listener. There seems to be something for scroll listener as well
        ListView lvdummy = (ListView) findViewById(R.id.lv1);
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SelectionList);
        lvdummy.setAdapter(adapter2);

        gvdummy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("flash_cards", "Arrived in grid view selection");
                Log.d("flash_cards", "pos = " + position);
                String selectedLesson = IconList.get(position).getLessonName();
                Log.d("flash_cards", "selected lesson = " + selectedLesson);
                SelectionList.add(selectedLesson);
                adapter2.notifyDataSetChanged();
            }
        });

        lvdummy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("flash_cards", "Arrived in list view deletion");
                Log.d("flash_cards", "pos = " + position);
                SelectionList.remove(position);
                adapter2.notifyDataSetChanged();
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
        //ViewHolder improves performance while scrolling: https://developer.android.com/training/improving-layouts/smooth-scrolling.html
        class ViewHolder {
            TextView iconText;
            ImageView iconPicture;
            //RatingBar iconRating;
            int position;
        }

        @Override
        // getView class is required by the gridview display
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null){
                holder = new ViewHolder();
                //convertView = inflater.inflate(R.layout.unit, null);
                convertView = inflater.inflate(resource, null);
                holder.iconPicture = (ImageView) convertView.findViewById(R.id.iconPicture);
                holder.iconText = (TextView) convertView.findViewById(R.id.iconText);
                //holder.iconRating = (RatingBar) convertView.findViewById(R.id.iconRating);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.iconText.setText(iconModelList.get(position).getLessonName() + " (" + iconModelList.get(position).getNumberLessons() + ")");
            String pic = "@drawable/" + iconModelList.get(position).getImage();
            int pic_id = getResources().getIdentifier(pic, null, getPackageName()); // get the location of where l1, l2, etc are stored
            Drawable pic2 = getResources().getDrawable(pic_id); // http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
            holder.iconPicture.setImageDrawable(pic2);        //display pic in the image

            Log.d("position = ", " " + position);
            return convertView;
        }

    }

    public void updateDeckList() {
        //TODO: Check error condition. Maybe use previous selection if blank?
        if (SelectionList.isEmpty()) {
            SelectionList.add(IconList.get(0).getLessonName());
            Toast.makeText(getApplicationContext(),"selection was empty; defaulting to " + SelectionList.get(0),Toast.LENGTH_LONG).show();
        }
        DeckList.clear();
        for (int i=0; i<SelectionList.size();i++ ){
            for (int j =0; j<IconList.size(); j++){
                if (SelectionList.get(i).equals(IconList.get(j).getLessonName()))
                {
                    for(int k=0; k< IconList.get(j).getCards().size();k++) {
                        DeckList.add(IconList.get(j).getCards().get(k));
                    }
                }
            }
        }
        chosenLesssonLength = DeckList.size();

        //Kickoff data downloaer
        String[] datatodownloadarray = new String[DeckList.size()];
        datatodownloadarray = DeckList.toArray(datatodownloadarray);
        //prioritizeDataFetch(datatodownloadarray);
    }

    public boolean doesFileExistInMemory (String card, String type) {
        boolean answer = true;
        int resId;
        File fileName;
        if (type.equals(".jpg")){
            resId = getResources().getIdentifier("@drawable/"+card, null, getPackageName());
            fileName = new File(getFilesDir(),card +".jpg");
            //Log.d("YYYY"+ card + type + " filename = "+ fileName, " resId = "+ resId);
        } else {
            resId = this.getResources().getIdentifier(card,"raw",this.getPackageName());
            fileName = new File(getFilesDir(),card +".mp3");
            //Log.d("YYYY"+ card + type + " filename = "+ fileName, " resId = "+ resId);
        }

        if ((resId == 0) &&(!fileName.exists())) {
            //Log.d("YYYY" + card + type, " Not Found in Memory");
            answer = false;
        }

        return answer;
    }

    public void prioritizeDataFetch (String[] cardlist){
        // Kick off an async task to prioritize the user selected lessons first
        // Then dd the remaining lessons to be downloaded
        // Kill the previous async task if it's still running, and start a new one

        List<String> NewListofAllUrlstoDownload  = new ArrayList<>();
        String cardtemp;

        for (int i =0; i<cardlist.length; i++) {
            cardtemp = cardlist[i];
            if (!doesFileExistInMemory(cardtemp,".jpg")){
                NewListofAllUrlstoDownload.add(cardtemp+ ".jpg");
                Log.d("ZZZ1 Added ", cardtemp+ ".jpg");
            }
            if (!doesFileExistInMemory(cardtemp,".mp3")){
                NewListofAllUrlstoDownload.add(cardtemp+ ".mp3");
                Log.d("ZZZ1 Added ", cardtemp + ".mp3");
            }
        }

        for (int k =0; k< NewListofAllUrlstoDownload.size(); k++){
            Log.d("ZZZ1 Reprioritizd list", NewListofAllUrlstoDownload.get(k));
        }

        for (int i = 0; i < ListofAllCards.size(); i++){
            cardtemp = ListofAllCards.get(i);
            if (!NewListofAllUrlstoDownload.contains(cardtemp+".jpg")) {
                if (!doesFileExistInMemory(cardtemp, ".jpg")) {
                    NewListofAllUrlstoDownload.add(cardtemp + ".jpg");
                    Log.d("ZZZ2 jpg Added ", cardtemp + ".jpg");
                }
            }

            if (!NewListofAllUrlstoDownload.contains(cardtemp+".mp3")) {
                if (!doesFileExistInMemory(cardtemp,".mp3")){
                    NewListofAllUrlstoDownload.add(cardtemp+ ".mp3");
                    Log.d("ZZZ2 mp3 Added ", cardtemp + ".mp3");
                }
            }
        }

        for (int k =0; k< NewListofAllUrlstoDownload.size(); k++){
            Log.d("ZZZ3 Reprioritizd "+ k + " ", NewListofAllUrlstoDownload.get(k));
        }

        if (getDatafromURL.getStatus() == AsyncTask.Status.RUNNING){
            Log.d("Async task", "running");
            getDatafromURL.cancel(true);
            Log.d("Async task", "cancelled");
            //TODO: Best practices in AsyncTask: http://www.shanekirk.com/2012/04/asynctask-missteps/
        }

        String[] datatodownloadarray = new String[NewListofAllUrlstoDownload.size()];
        datatodownloadarray = NewListofAllUrlstoDownload.toArray(datatodownloadarray);

        getDatafromURL = new Datadownloader();
        getDatafromURL.execute(datatodownloadarray);
        //TODO: Maybe we can pass a list instead of having to convert to a string array
    }

    public void selectionComplete(View v) {
        updateDeckList();
        CheckBox temp1 = (CheckBox) findViewById(R.id.quizcheckBox);
        if (temp1.isChecked()) {
            quiz = 1;
            temp1.setChecked(true);
        } else {
            quiz = 0;
            temp1.setChecked(false);
        }

        Log.d("flash_cards", "quiz selection = " + quiz);

        setContentView(R.layout.activity_main);
        stopCounter = 0;
        mHandler.removeCallbacksAndMessages(mUpdate);
        stopMediaPlayers();
        mHandler.post(mUpdate);

    }

    public class Datadownloader extends AsyncTask<String, Integer, Integer>{
        @Override
        protected Integer doInBackground(String... params){

            String link1 = "https://raw.githubusercontent.com/makeshl/flash_cards/master/app/src/main/res/drawable/";
            String link2= "https://raw.githubusercontent.com/makeshl/flash_cards/master/app/src/main/res/raw/";
            String link;

            for (int i =0; i< params.length; i++){
                if (params[i].substring(params[i].length() -1).equals("g")) {
                    link = link1+params[i];
                } else {
                    link = link2+params[i];
                }

                Log.d("path = ", link);
                File outfilelocation = new File(getFilesDir(), params[i]);
                Log.d("outfile =", outfilelocation.getPath());
                FileOutputStream fout = null;

                try {
                    URL url = new URL(link);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    BufferedInputStream fin = new BufferedInputStream(urlConnection.getInputStream());
                    fout = new FileOutputStream(outfilelocation);

                    byte[] buffer = new byte[1024];
                    int read;
                    while((read = fin.read(buffer)) != -1){
                        fout.write(buffer, 0, read);
                    }

                    //Bitmap bm = BitmapFactory.decodeStream(fin);
                    //Log.d("bytecount = " + bm.getHeight(), " .." + bm.getDensity());
                    //bm.compress(Bitmap.CompressFormat.PNG, 100, fout);
                    fout.flush();
                    fout.close();
                    fin.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d("Malformed Exc","");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IO Exc", "");
                }
            }
            return 1;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Integer a){
            Log.e("Async task complete!", "" + a);
            Toast.makeText(getApplicationContext(), "Done",Toast.LENGTH_LONG);
        }
    }

}

//TODO Bring back rating bar
