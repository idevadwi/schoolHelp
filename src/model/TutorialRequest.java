package model;

public class TutorialRequest extends Request {
    private String proposedDate;
    private String proposedTime;
    private String studentLevel;
    private int numStudents;

    public TutorialRequest(String requestStatus, String description,
                           String schoolID, String proposedDate, String proposedTime, String studentLevel, int numStudents) {
        super(requestStatus, description, schoolID);
        this.proposedDate = proposedDate;
        this.proposedTime = proposedTime;
        this.studentLevel = studentLevel;
        this.numStudents = numStudents;
    }

    public String getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(String proposedDate) {
        this.proposedDate = proposedDate;
    }

    public String getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(String proposedTime) {
        this.proposedTime = proposedTime;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    @Override
    public String toString() {
        return "TutorialRequest{" +
                "proposedDate='" + proposedDate + '\'' +
                ", proposedTime='" + proposedTime + '\'' +
                ", studentLevel='" + studentLevel + '\'' +
                ", numStudents=" + numStudents +
                '}';
    }
}
