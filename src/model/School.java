package model;

import java.util.UUID;

public class School {
    private String schoolID;
    private String schoolName;
    private String address;
    private String city;

    public School(String schoolName, String address, String city) {
        this.schoolID = UUID.randomUUID().toString();
        this.schoolName = schoolName;
        this.address = address;
        this.city = city;
    }

    public School() {
        this.schoolID = "";
        this.schoolName = "";
        this.address = "";
        this.city = "";
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolID='" + schoolID + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

//    public boolean isEqual(School school1, School school2) {
//        return school1.schoolName.equals(school2.schoolName)
//                && school1.address.equals(school2.address)
//                && school1.city.equals(school2.city);
//    }
}
