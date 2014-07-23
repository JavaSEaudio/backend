package DTO;

import Entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserListDTO {

    public UserListDTO(){}

    public static UserDTO getUserDTO(UserEntity userEntity) {
        return new UserDTO(userEntity);
    }

    public static List<UserDTO> getListUserDTO(List<UserEntity> userEntity) {
        ArrayList<UserDTO> result = new ArrayList<UserDTO>();
        for(UserEntity uEntity : userEntity) {
            UserDTO uDro = new UserDTO(uEntity);
            result.add(uDro);
        }
        return result;
    }
}
