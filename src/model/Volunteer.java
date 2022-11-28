package model;

public class Volunteer extends User {
    public String dateOfBirth;
    public String occupation;


    public Volunteer(String userName, String passWord, String fullName, String email, String phoneNumber, String dateOfBirth, String occupation) {
        super(userName, passWord, fullName, email, phoneNumber);
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "dateOfBirth='" + dateOfBirth + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}
