package util;

import com.google.code.mp3fenge.Mp3Fenge;

import java.io.File;

public class Cut {
    public static void file(String path){
        File file = new File(path);
        Mp3Fenge helper = new Mp3Fenge(file);
        String outPath = path.replace(file.getName(),"") + "cut" + file.getName();
        helper.generateNewMp3ByTime(new File(outPath), 0, 30000);
    }
}
