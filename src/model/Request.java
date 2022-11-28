package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Request {
    private String requestID;
    private String requestDate;
    private String requestStatus;
    private String description;
    private String schoolID;

    public Request(String requestStatus, String description, String schoolID) {
        this.requestID = UUID.randomUUID().toString();
        this.requestDate = getCurrentDate();
        this.requestStatus = requestStatus;
        this.description = description;
        this.schoolID = schoolID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    private String getCurrentDate() {
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID='" + requestID + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", description='" + description + '\'' +
                ", schoolID='" + schoolID + '\'' +
                '}';
    }
}
