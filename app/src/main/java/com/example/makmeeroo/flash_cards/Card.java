package com.example.makmeeroo.flash_cards;

import java.util.List;
import android.content.Context;

/**
 * Created by MakMeeRoo on 1/14/2017.
 */
public class Card {

    public static final String IMAGE_URL = "https://raw.githubusercontent.com/makeshl/flash_cards/master/app/src/main/res/drawable/";
    public static final String VOICE_URL = "https://raw.githubusercontent.com/makeshl/flash_cards/master/app/src/main/res/raw/";

    private boolean isImageEligible;
    private String cardName;

    private String voiceName;
    private int resIdVoice;
    private boolean voiceFileExistsinRes = false;
    private String voiceFileInternalMemoryLocation;
    private boolean voiceFileExistsinInternalMemory;
    private String voiceFileExternalLocation;

    private String imageName;
    private int resIdImage;
    private boolean imageFileExistinRes = false;
    private String imageFileInternalMemoryLocation;
    private boolean imageFileExistsinInternalMemory;
    private String imageFileExternalLocation;

    private List<Integer> spellingVoiceFiles;
    private List<Integer> phoneticsVoiceFiles;

    public Card(String cardName) {
        this.cardName = cardName;
        voiceName = cardName;
        imageName = cardName;
        voiceFileExternalLocation = VOICE_URL + voiceName;
        imageFileExternalLocation = IMAGE_URL + imageName;
//        resIdVoice = context.getResources().getIdentifier(voiceName, "raw", context.getPackageName());
//        resIdImage = context.getResources().getIdentifier("@drawable/"+imageName, null, context.getPackageName());
//        if (resIdVoice!= 0){voiceFileExistsinRes = true;}
//        if (resIdImage!= 0){imageFileExistinRes = true;}
//
//        for (int i =0; i<voiceName.length(); i++){
//            String x = String.valueOf(voiceName.charAt(i));
//            int spellingFileRes = context.getResources().getIdentifier(x, "raw", context.getPackageName());
//            spellingVoiceFiles.add(spellingFileRes);
//            int phoneticFileRes = context.getResources().getIdentifier(x, "raw", context.getPackageName());
//            phoneticsVoiceFiles.add(phoneticFileRes);
//        }
    }

    public String getVoiceFileName() {return voiceName;}

    public void setResIdVoice(Integer resIdVoice) {this.resIdVoice = resIdVoice;}

    public int getResIdVoice(){return resIdVoice;}

    public boolean doesVoiceFileExistinRes(){return voiceFileExistsinRes;}

    public void setDoesVoiceFileExistinInternalMemory(boolean doesVoiceFileExistinInternalMemory){voiceFileExistsinInternalMemory = doesVoiceFileExistinInternalMemory;}

    public boolean doesVoiceFileExistinInternalMemory(){return voiceFileExistsinInternalMemory;}

    public void setVoiceFileInternalMemoryLocation (String voiceFileInternalMemoryLocation){this.voiceFileInternalMemoryLocation = voiceFileInternalMemoryLocation;}

    public String getVoiceFileInternalMemoryLocation (){return voiceFileInternalMemoryLocation;}

    public String getVoiceFileExternalMemoryLocation (){return voiceFileExternalLocation;}

    public void setSpellingVoiceFiles(List<Integer> spellingVoiceFiles) {this.spellingVoiceFiles = spellingVoiceFiles;}

    public List<Integer> getSpellingVoiceFiles () {return spellingVoiceFiles;}

    public void setPhoneticsVoiceFiles(List<Integer> phoneticsVoiceFiles) {this.phoneticsVoiceFiles= phoneticsVoiceFiles;}

    public List<Integer> getPhoneticsVoiceFiles () {return phoneticsVoiceFiles;}

    public String getImageFileName() {return imageName;}

    public void setResIdImage(Integer resIdImage) {this.resIdImage = resIdImage;}

    public int getResIdImage(){return resIdImage;}

    public boolean doesImageFileExistinRes(){return imageFileExistinRes;}

    public void setDoesImageFileExistinInternalMemory(boolean doesImageeFileExistinInternalMemory){imageFileExistsinInternalMemory = doesImageeFileExistinInternalMemory;}

    public boolean doesImageFileExistinInternalMemory(){return imageFileExistsinInternalMemory;}

    public void setIamgeFileInternalMemoryLocation (String imageFileInternalMemoryLocation){this.imageFileInternalMemoryLocation = imageFileInternalMemoryLocation;}

    public String getImageeFileInternalMemoryLocation (){return imageFileInternalMemoryLocation;}

    public String getImageFileExternalMemoryLocation (){return imageFileExternalLocation;}

}
