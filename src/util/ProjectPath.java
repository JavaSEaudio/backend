package util;

import Entity.AudioEntity;

public class ProjectPath {
    private ProjectPath(){}
    private final static String projectPath = "С://upload//";

    public static String getPath() {
        return projectPath;
    }
    public static String getAudioPath(AudioEntity audioEntity) {
        String link = projectPath+"//audio//"+audioEntity.getId()+".mp3";
        return link;
    }
    public static String getImagePath(AudioEntity audioEntity) {
        String link = projectPath+"//image//"+audioEntity.getId()+".mp3";
        return link;
    }
}
