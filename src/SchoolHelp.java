import model.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class SchoolHelp implements Serializable {
    List<School> schoolList = new ArrayList<>();
    List<Offer> offerList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Request> requestList = new ArrayList<>();

    final User schoolHELPAdmin = new User("admin", "admin", "School HELP Administrator",
            "admin@schoolhelp.com", "+6289038764");

    public boolean loginAsAdmin(String username, String password) {
        return schoolHELPAdmin.getUserName().equals(username) && schoolHELPAdmin.getPassword().equals(password);
    }

    public SchoolAdmin loginAsSchoolAdmin(String username, String password) {
        User userAdmin = userList.stream()
                .filter(user -> user.getUserName().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);

        if (!(userAdmin instanceof SchoolAdmin)) {
            System.out.println("User is not School Admin");
            return null;
        }

        return (SchoolAdmin) userAdmin;
    }

    public School getSchoolFromSchoolAdmin(SchoolAdmin admin) {
        return schoolList.stream()
                .filter(school -> school.getSchoolID().equals(admin.getSchoolID()))
                .findFirst().orElse(null);
    }

    public School isSchoolExist(String schoolName, String address, String city) {
        return schoolList.stream()
                .filter(school -> school.getSchoolName().equals(schoolName))
                .filter(school -> school.getAddress().equals(address))
                .filter(school -> school.getCity().equals(city))
                .findFirst().orElse(null);
    }

    public School createNewSchool(String schoolName, String address, String city) {
        School newSchool = new School(schoolName, address, city);
        schoolList.add(newSchool);

        return newSchool;
    }

    public void createSchoolAdmin(String userName, String passWord, String fullName, String email, String phoneNumber,
                                  String staffID, String position, String schoolID) {
        SchoolAdmin admin = new SchoolAdmin(userName, passWord, fullName, email, phoneNumber, staffID, position, schoolID);
        userList.add(admin);
    }

    //TODO remove return
    public TutorialRequest submitNewTutorialRequest(String requestStatus, String description,
                                                    String schoolID, String proposedDate, String proposedTime,
                                                    String studentLevel, int numStudents) {
        TutorialRequest req = new TutorialRequest(requestStatus, description, schoolID, proposedDate, proposedTime, studentLevel, numStudents);
        requestList.add(req);
        return req;
    }

    //TODO remove return
    public ResourceRequest submitNewResourceRequest(String requestStatus, String description,
                                                    String schoolID, String resourceType, int numOfRequired) {
        ResourceRequest req = new ResourceRequest(requestStatus, description, schoolID, resourceType, numOfRequired);
        requestList.add(req);

        return req;
    }

    //TODO remove return
    public Volunteer createNewVolunteer(String userName, String passWord, String fullName, String email,
                                        String phoneNumber, String dateOfBirth, String occupation) {
        Volunteer vol = new Volunteer(userName, passWord, fullName, email, phoneNumber, dateOfBirth, occupation);
        userList.add(vol);

        return vol;
    }

    public List<Request> getListRequest(String type, String data) {
        switch (type) {
            case "SCHOOL": {
                return requestList.stream()
                        .filter(request -> request.getSchoolID().equals(getSchoolIdBySchoolName(data)))
                        .filter(request -> request.getRequestStatus().equals("NEW"))
                        .collect(Collectors.toList());
            }
            case "CITY": {
                return requestList.stream()
                        .filter(request -> getListOfSchoolIdByCity(data).contains(request.getSchoolID()))
                        .filter(request -> request.getRequestStatus().equals("NEW"))
                        .collect(Collectors.toList());
            }
            case "REQUESTDATE": {
                return requestList.stream()
                        .filter(request -> request.getRequestDate().equals(data))
                        .filter(request -> request.getRequestStatus().equals("NEW"))
                        .collect(Collectors.toList());
            }
            default:
                return null;
        }
    }

    public String getSchoolIdBySchoolName(String schoolName) {
        School currentSchool = schoolList.stream()
                .filter(school -> school.getSchoolName().equals(schoolName))
                .findFirst().orElse(null);
        if (currentSchool == null) {
            return null;
        }

        return currentSchool.getSchoolID();
    }

    public List<String> getListOfSchoolIdByCity(String city) {
        List<School> currentSchool = schoolList.stream()
                .filter(school -> school.getCity().equals(city))
                .collect(Collectors.toList());
        return currentSchool.stream().map(School::getSchoolID).collect(Collectors.toList());
    }

    public School getSchoolById(String schoolId) {
        return schoolList.stream()
                .filter(school -> school.getSchoolID().equals(schoolId))
                .findFirst().orElse(null);
    }

    public Request getRequestDetail(String requestId) {
        return requestList.stream()
                .filter(request -> request.getRequestID().equals(requestId))
                .findFirst().orElse(null);
    }

    public void submitOffer(String remarks, String requestId, String volunteerId) {
        Offer newOffer = new Offer(remarks, "PENDING", requestId, volunteerId);
        offerList.add(newOffer);
    }

    public List<Offer> getOfferListByRequestId(String requestId) {
        return offerList.stream()
                .filter(offer -> offer.getRequestId().equals(requestId))
                .collect(Collectors.toList());
    }

    public Offer getOfferDetailById(String offerId) {
        return offerList.stream()
                .filter(offer -> offer.getOfferId().equals(offerId))
                .findFirst().orElse(null);
    }

    public void acceptOffer(Offer offer, Request request) {
        offer.setOfferStatus("ACCEPTED");
        request.setRequestStatus("CLOSED");
    }

    public User login(String username, String password) {
        User userData = userList.stream()
                .filter(user -> user.getUserName().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);

        return userData;
    }

    public Volunteer getVolunteerFromId(String volunteerId) {
        User userData = userList.stream()
                .filter(user -> user.getUserId().equals(volunteerId))
                .findFirst().orElse(null);

        return (Volunteer) userData;
    }

    public int calculateAge(String input) {
        LocalDate dob = LocalDate.parse(input);
        //creating an instance of the LocalDate class and invoking the now() method
        //now() method obtains the current date from the system clock in the default time zone
        LocalDate curDate = LocalDate.now();
        //calculates the amount of time between two dates and returns the years
        if (dob != null) {
            return Period.between(dob, curDate).getYears();
        } else {
            return 0;
        }
    }

    public User changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return user;
    }

    public SchoolAdmin updateProfile(SchoolAdmin admin, String fullname, String email,
                                     String phone, String staffId, String position) {
        admin.setFullName(fullname);
        admin.setEmail(email);
        admin.setPhoneNumber(phone);
        admin.setStaffID(staffId);
        admin.setPosition(position);

        return admin;
    }

    public List<User> getAllUser() {
        userList.sort(Comparator.comparing(User::getFullName));
        return userList;
    }

    public List<Request> getAllRequest() {
        requestList.sort(Comparator.comparing(Request::getRequestDate));
        requestList.sort(Comparator.comparing(a -> getSchoolNameById(a.getSchoolID())));
        return requestList;
    }

    public String getSchoolNameById(String schoolId) {
        return Objects.requireNonNull(schoolList.stream()
                .filter(school -> school.getSchoolID().equals(schoolId))
                .findFirst().orElse(null)).getSchoolName();
    }

}
