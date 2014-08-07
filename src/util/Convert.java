package util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convert {
    public static void convertAllFile(File source, int id, String extension, String fileLocation){

        String outFilePath = fileLocation+ id+"."+extension;
        AudioAttributes audio = new AudioAttributes();
        File target = new File(outFilePath);
        if (extension.equals("flac")) {
            audio.setCodec("flac");
        }
        if (extension.equals("wav")) {
            audio.setCodec("wmav2");
        }
        if (extension.equals("ogg")) {
            audio.setCodec("vorbis");
        }
        if(extension.equals("mp3")){
            audio.setCodec("libmp3lame");
        }
        audio.setBitRate(new Integer(128000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat(extension);
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(source, target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }

    public static String convertFile(File source, String fileLocation, String extension){
        String outFilePath = fileLocation+"."+extension;
        AudioAttributes audio = new AudioAttributes();
        File target = new File(outFilePath);
        if( target.exists()== false) {

            if (extension.equals("flac")) {
                audio.setCodec("flac");
            }
            if (extension.equals("wav")) {
                audio.setCodec("wmav2");
            }
            if (extension.equals("ogg")) {
                audio.setCodec("vorbis");
            }
            if(extension.equals("mp3")){
                audio.setCodec("libmp3lame");
            }
            audio.setBitRate(new Integer(128000));
            audio.setChannels(new Integer(2));
            audio.setSamplingRate(new Integer(44100));

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat(extension);
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            try {
                encoder.encode(source, target, attrs);
            } catch (EncoderException e) {
                e.printStackTrace();
            }
        }
        return outFilePath;
    }
    public static String getExpansion (String fileName){
        String expansion;
        String regexpAudio1 = "([^\\s]+(\\.(?i)(mp3|wav|og(?:g|a)|flac|midi?|rm|aac|wma|mka|ape))$)";
        Pattern pattern = Pattern.compile(regexpAudio1);
        Matcher matcher = pattern.matcher(fileName);

        if(matcher.matches()){
            String [] temp =  fileName.split("\\.");
            expansion = temp[temp.length-1];
        }else {
            expansion = "";
        }
        return expansion;
    }
}
