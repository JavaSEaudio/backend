package DTO;

import Entity.AudioEntity;
import Entity.CommentsEntity;
import Entity.PrivateEntity;
import Entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class GetListDTO {
    public GetListDTO(){}


    public static List<AudioDTO> getListAudioDTO(List<AudioEntity> userEntity, int userid, int number) {
        ArrayList<AudioDTO> result = new ArrayList<AudioDTO>();
        for(AudioEntity aEntity : userEntity) {
            AudioDTO aDro = new AudioDTO(aEntity, userid, number);
            if(!aDro.isLoad()) {
                result.add(aDro);
                number++;
            } else {
                if(aDro.getUserid() == userid){
                    result.add(aDro);
                }
            }

        }
        return result;
    }

    public static List<PrivateDTO> getListPrivateDTO(List<PrivateEntity> userEntity) {
        ArrayList<PrivateDTO> result = new ArrayList<PrivateDTO>();
        for(PrivateEntity aEntity : userEntity) {
            PrivateDTO aDro = new PrivateDTO(aEntity);
            result.add(aDro);
        }
        return result;
    }

    public static List<UserDTO> getListUserDTO(List<UserEntity> userEntity) {
        ArrayList<UserDTO> result = new ArrayList<UserDTO>();
        for(UserEntity uEntity : userEntity) {
            UserDTO uDro = new UserDTO(uEntity);
            result.add(uDro);
        }
        return result;
    }

    public static List<CommentsDTO> getListCommentsDTO(List<CommentsEntity> userEntity) {
        ArrayList<CommentsDTO> result = new ArrayList<CommentsDTO>();
        for(CommentsEntity uEntity : userEntity) {
            CommentsDTO uDro = new CommentsDTO(uEntity);
            result.add(uDro);
        }
        return result;
    }

    public static List<ArtistDTO> getListArtistDTO(List<String> artist) {
        ArrayList<ArtistDTO> result = new ArrayList<ArtistDTO>();
        for(String art : artist) {
            ArtistDTO uDro = new ArtistDTO(art);
            result.add(uDro);
        }
        return result;
    }

    public static List<AlbumsDTO> getListAlbumsDTO(List<String> album, String artist) {
        ArrayList<AlbumsDTO> result = new ArrayList<AlbumsDTO>();
        for(String art : album) {
            AlbumsDTO uDro = new AlbumsDTO(art, artist);
            result.add(uDro);
        }
        return result;
    }
}
