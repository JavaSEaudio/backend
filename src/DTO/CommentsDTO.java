package DTO;

import DAO.util.Factory;
import Entity.CommentsEntity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommentsDTO {
    private int id;
    private String login;
    private String comment;
    private String linkAvatar;

    public CommentsDTO(CommentsEntity commentsEntity){
        this.id = commentsEntity.getId();
        this.login = Factory.getInstance().getUserDAO().getById(commentsEntity.getUser()).getLogin();
        this.comment = commentsEntity.getComment();
        this.linkAvatar = "/rest/get/avatar?id="+Factory.getInstance().getUserDAO().getByLogin(this.login).getId();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public CommentsDTO(){}



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
