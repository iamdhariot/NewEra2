package in.blogspot.iamdhariot.newera;

/**
 * Created by Dhariot on 12-Feb-18.
 */

public class UserData {
    String name, email, dob, mobileNum;

    public UserData() {

    }

    public UserData(String name, String email, String mobileNum, String dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.mobileNum = mobileNum;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
