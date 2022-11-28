package model;

import model.User;

public class SchoolAdmin extends User {
    private String staffID;
    private String position;
    private String schoolID;


    public SchoolAdmin(String userName, String passWord, String fullName, String email, String phoneNumber,
                       String staffID, String position, String schoolID) {
        super(userName, passWord, fullName, email, phoneNumber);
        this.staffID = staffID;
        this.position = position;
        this.schoolID = schoolID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    @Override
    public String toString() {
        return "model.SchoolAdmin{" +
                "staffID='" + staffID + '\'' +
                ", position='" + position + '\'' +
                ", schoolID='" + schoolID + '\'' +
                '}';
    }
}
