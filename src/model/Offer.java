package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Offer {
    private String offerId;
    private String offerDate;
    private String remarks;
    private String offerStatus;
    private String requestId;
    private String volunteerId;

    public Offer(String remarks, String offerStatus, String requestId, String volunteerId) {
        this.offerId = UUID.randomUUID().toString();
        this.offerDate = getCurrentDate();
        this.remarks = remarks;
        this.offerStatus = offerStatus;
        this.requestId = requestId;
        this.volunteerId = volunteerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    private String getCurrentDate() {
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId='" + offerId + '\'' +
                ", offerDate='" + offerDate + '\'' +
                ", remarks='" + remarks + '\'' +
                ", offerStatus='" + offerStatus + '\'' +
                ", requestId='" + requestId + '\'' +
                ", volunteerId='" + volunteerId + '\'' +
                '}';
    }
}
