package nhm.com.odiya.DTO;

import java.io.Serializable;

/**
 * Created by Lee on 2016-08-10.
 */
public class UserDTO implements Serializable{

    private String id;
    private String pass;
    private String tel;
    private String birth;
    private String gender;


    public UserDTO(String id, String pass, String tel, String birth, String gender) {
        this.id = id;
        this.pass = pass;
        this.tel = tel;
        this.birth = birth;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserDTO(){

    }


}
