package util;

import Entity.AudioEntity;

public class ProjectPath {
    private ProjectPath(){}
    private final static String projectPath = "D://JavaEE//backend-master-0//";

    public static String getPath() {
        return projectPath;
    }
    public static String getAudioPath(AudioEntity audioEntity) {
        String link = projectPath+"web//file//audio//"+audioEntity.getId()+".mp3";
        return link;
    }
    public static String getImagePath(AudioEntity audioEntity) {
        String link = projectPath+"web//file//audio//"+audioEntity.getId()+".mp3";
        return link;
    }
}
