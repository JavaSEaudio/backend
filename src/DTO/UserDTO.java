package DTO;

import Entity.UserEntity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {

    private int id;
    private String name;
    private String login;
    private String email;
    private String information;
    private double money;
    private int access;
    private int[] buylist;
    private int[] mylist;
    private String linkIAvatar;

    public UserDTO(){}


    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.information = user.getInformation();
        this.money = user.getMoney();
        this.access = user.getAccess();
        this.buylist = parser(user.getBuylist());
        this.mylist = parser(user.getMylist());
        this.linkIAvatar = user.getLinkAvatar();
    }

    private int[] parser (String str) {
        int[] result = new int[0];
        if(str == null || str.equals("") || str.equals(" ")) return result;
        String [] temp;
        try {
            temp = str.split(" ");
            result = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                result[i] = Integer.parseInt(temp[i]);
            }
        } catch (Exception e){  }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getBuylist() {

        String res = new String();
        for(int i = 0; i < buylist.length; i++){
            res += buylist[i]+" ";
        }
        return res;
    }

    public void setBuylist(String buylist) {
        this.buylist = parser(buylist);
    }

    public void addBuylist(int id) {
        int [] res = new int [buylist.length+1];
        for(int i = 0; i < buylist.length; i++){
            res[i] = buylist[i];
        }
        res[buylist.length] = id;
        buylist = res;
    }

    public String getMylist() {

        String res = new String();
        for(int i = 0; i < mylist.length; i++){
            res += mylist[i]+" ";
        }
        return res;
    }

    public void setMylist(String mylist) {
        this.mylist = parser(mylist);
    }

    public void addMylist(int id) {
        int [] res = new int [mylist.length+1];
        for(int i = 0; i < mylist.length; i++){
            res[i] = mylist[i];
        }
        res[mylist.length] = id;
        mylist = res;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLinkAvatar() {
        return linkIAvatar;
    }

    public void setILinkAvatar(String linkIAvatar) {
        this.linkIAvatar = linkIAvatar;
    }
}