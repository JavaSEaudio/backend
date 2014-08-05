package DTO;

import DAO.util.Factory;
import Entity.CommentsEntity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommentsDTO {
    private String login;
    private String comment;

    public CommentsDTO(){}

    public CommentsDTO(CommentsEntity commentsEntity){
        this.login = Factory.getInstance().getUserDAO().getById(commentsEntity.getUser()).getLogin();
        this.comment = commentsEntity.getComment();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
