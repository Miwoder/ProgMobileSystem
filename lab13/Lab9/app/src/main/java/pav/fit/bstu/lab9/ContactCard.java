package pav.fit.bstu.lab9;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "cards")
public class ContactCard {

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @PrimaryKey
    @NonNull
    private String Id;

    private String fullName;

    private String photo = "sfbsdf";

    private String workPlace;

    private String email;

    private String mobile;

    private String link;


    public ContactCard(String fullName, String photo, String workPlace, String email, String mobile, String link) {
        Id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.photo = photo;
        this.workPlace = workPlace;
        this.email = email;
        this.mobile = mobile;
        this.link = link;
    }

}
