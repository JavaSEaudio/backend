package DTO;

import Entity.AudioEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Степанов on 01.08.2014.
 */
public class AudioListDTO {
    public AudioListDTO(){}

    public static AudioDTO getUserDTO(AudioEntity audioEntity, int userid) {
        return new AudioDTO(audioEntity, userid);
    }

    public static List<AudioDTO> getListAudioDTO(List<AudioEntity> userEntity, int userid) {
        ArrayList<AudioDTO> result = new ArrayList<AudioDTO>();
        for(AudioEntity aEntity : userEntity) {
            AudioDTO aDro = new AudioDTO(aEntity, userid);
            result.add(aDro);
        }
        return result;
    }
}
