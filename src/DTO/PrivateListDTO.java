package DTO;


import Entity.AudioEntity;
import Entity.PrivateEntity;

import java.util.ArrayList;
import java.util.List;

public class PrivateListDTO {
    public PrivateListDTO(){}

    public static PrivateDTO getUserDTO(PrivateEntity audioEntity) {
        return new PrivateDTO(audioEntity);
    }

    public static List<PrivateDTO> getListPrivateDTO(List<PrivateEntity> userEntity) {
        ArrayList<PrivateDTO> result = new ArrayList<PrivateDTO>();
        for(PrivateEntity aEntity : userEntity) {
            PrivateDTO aDro = new PrivateDTO(aEntity);
            result.add(aDro);
        }
        return result;
    }
}
