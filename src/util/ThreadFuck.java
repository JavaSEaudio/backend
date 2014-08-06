package util;

import java.io.File;

public class ThreadFuck extends Thread {

    File file = null;
    int id  = 0;


    public ThreadFuck(File file, int id){
        this.file = file;
        this.id = id;
    }

    public void run() {
        Convert.convertAllFile(file, id, "mp3", "C://upload//private//");
        Convert.convertAllFile(file, id, "ogg", "C://upload//private//");
        Convert.convertAllFile(file, id, "wav", "C://upload//private//");
    }
}
