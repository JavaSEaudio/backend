package DTO;


        import DAO.util.Factory;
        import Entity.AudioEntity;
        import Entity.PrivateEntity;
        import rest.file.Audio;
        import rest.goodies.LikesSystem;

        import javax.xml.bind.annotation.XmlRootElement;
        import java.util.Date;

@XmlRootElement
public class AudioDTO {
    private int id;
    private int number;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String comment;
    private int year;
    private double price;
    private String type;
    private int length;
    private int size;
    private int userid;
    private String linkImage;
    private String linkFile;
    private Date upload_date;
    private boolean edit;
    private boolean buy;
    private boolean like;
    private int countLike;
    private boolean load;
    private  String linkTube;

    public AudioDTO(){}


    public AudioDTO(AudioEntity audioEntity, int userid, int number){
        this.id = audioEntity.getId();
        this.name = audioEntity.getName();
        this.artist = audioEntity.getArtist();
        this.album = audioEntity.getAlbum();
        this.genre = audioEntity.getGenre();
        this.comment = audioEntity.getComment();
        this.year = audioEntity.getYear();
        this.number = number;
        this.price = audioEntity.getPrice();
        this.type = audioEntity.getType();
        this.length = audioEntity.getLength();
        this.size = audioEntity.getSize();
        this.userid = audioEntity.getUserid();
        this.linkTube= audioEntity.getLinkTube();
        this.linkImage = "/rest/get/image?id="+audioEntity.getId();
        this.linkFile = "/rest/listen?id="+audioEntity.getId();
        this.upload_date = audioEntity.getUpload_date();
        if(userid != -1) {
            UserDTO user = new UserDTO(Factory.getInstance().getUserDAO().getById(userid));
            if(this.price > 0) {
                if (user.getAccess() > 0) {
                    this.buy = false;
                } else {
                    this.buy = true;
                    for (int i : user.getBuyListArray()) {
                        if (i == this.id) {
                            this.buy = false;
                            break;
                        }
                    }
                }
            } else {
                this.buy = false;
            }
            if (audioEntity.getUserid() == userid || user.getAccess() > 0) {
                this.buy = false;
                this.edit = true;
            } else {
                this.edit = false;
            }
        } else {
            this.edit = false;
            this.buy = false;
        }
        this.countLike = LikesSystem.count(this.id);
        this.like = LikesSystem.check(userid, this.id);
        if(this.album.equals("luaged")) {
            this.load = true;
        }else {
            this.load = false;
        }
        System.out.println(like);
    }



    public AudioDTO(PrivateEntity privateEntity) {
        this.id = privateEntity.getId();
        this.name = privateEntity.getName();
        this.artist = privateEntity.getArtist();
        this.album = privateEntity.getAlbum();
        this.linkImage = "/rest/get/image?id="+privateEntity.getId();
        this.linkFile = "/rest/listen?id="+privateEntity.getId();
        this.upload_date = privateEntity.getUpload_date();
        this.userid = privateEntity.getUserid();
        this.buy = false;
        this.edit = true;
        this.like = false;
        this.countLike = 0;
        this.genre = "";
        this.comment = "";
        this.year = 0;
        this.price = 0;
        this.type = "";
        this.length = 0;
        this.size = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public String getLinkTube() {
        return linkTube;
    }

    public void setLinkTube(String linkTube) {
        this.linkTube = linkTube;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
