import model.*;

import java.util.List;
import java.util.Scanner;

public class SchoolHELPConsole {
    private static SchoolHelp schoolHelp;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        schoolHelp = new SchoolHelp();
        beforeRun();

        int choice;
        do {
            System.out.println("\n\n ====== Welcome to SchoolHELP =====");
            System.out.println("1. Register School");
            System.out.println("2. Submit Request");
            System.out.println("3. Register as Volunteer");
            System.out.println("4. View Requests");
            System.out.println("5. Submit Offer");
            System.out.println("6. Review Offers");
            System.out.println("0. Quit");
            System.out.println("");
            System.out.print("Your choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    registerSchool();
                    break; //Line 5a. skipped
                case 2:
                    submitRequest();
                    break;
                case 3:
                    registerVolunteer();
                    break;
                case 4:
                    viewRequest();
                    break;
                case 5:
                    submitOffer();
                    break;
                case 6:
                    reviewOffer();
                    break;
                case 0:
                    System.out.println("Thank You");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private static void reviewOffer() {
        System.out.println("Review Offer");
        School school = loginAsSchoolAdmin();

        if (school == null) {
            System.out.println("Wrong credentials for School Administrator");
            return;
        }

        System.out.println("1. View Request");
        System.out.print("Enter your choice : ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice != 1) {
            System.out.println("Invalid choice!");
            return;
        }

        printRequest(schoolHelp.getListRequest("SCHOOL", school.getSchoolName()));

        System.out.print("Select Request By ID: ");
        String reqId = sc.nextLine();

        //get request detail by request ID
        Request request = schoolHelp.getRequestDetail(reqId);

        System.out.println("Tutorial Request Details");
        System.out.println("Description : " + request.getDescription());
        System.out.println();

        System.out.println("List of Offering for this request");
        List<Offer> offerList = schoolHelp.getOfferListByRequestId(request.getRequestID());

        if (offerList.size() < 1) {
            System.out.println("Offerings is Empty");
            return;
        }

        System.out.println(padRight("OFFER ID", 40)
                + padRight("DATE", 15)
                + padRight("REMARK", 30)
                + padRight("NAME", 25)
                + padRight("AGE", 5)
                + padRight("OCCUPATION", 5));


        offerList.forEach(offer -> {
            Volunteer volunteer = schoolHelp.getVolunteerFromId(offer.getVolunteerId());

            System.out.println(padRight(offer.getOfferId(), 40)
                    + padRight(offer.getOfferDate(), 15)
                    + padRight(offer.getRemarks(), 30)
                    + padRight(volunteer.getFullName(), 25)
                    + padRight(schoolHelp.calculateAge(volunteer.getDateOfBirth()) + "", 5)
                    + padRight(volunteer.getOccupation(), 5));
        });

        System.out.println();
        System.out.print("Enter Offering ID to Accept : ");
        String offeringId = sc.nextLine();

        Offer offer = schoolHelp.getOfferDetailById(offeringId);

        schoolHelp.acceptOffer(offer, request);

        // TODO need to handle logout
    }


    private static void submitOffer() {
        System.out.println("Submit Offer");
        Volunteer volunteer = loginAsVolunteer();

        if (volunteer == null) {
            return;
        }

        System.out.print("Enter request ID : ");
        String reqId = sc.nextLine();
        System.out.print("Enter remarks for this request : ");
        String remarks = sc.nextLine();
        schoolHelp.submitOffer(remarks, reqId, volunteer.getUserId());

        System.out.println("Your Offering has been submitted");
    }

    private static void viewRequest() {

        Volunteer volunteer = loginAsVolunteer();

        if (volunteer == null) {
            return;
        }

        int choice;
        do {
            System.out.println("View Request");
            System.out.println("1. By School Name");
            System.out.println("2. By City");
            System.out.println("3. By Request Date");
            System.out.println("0. Back to Menu");
            System.out.print("Your choice : ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("School name : ");
                    String schoolName = sc.nextLine();
                    printRequest(schoolHelp.getListRequest("SCHOOL", schoolName));
                    break;
                case 2:
                    System.out.print("City name : ");
                    String city = sc.nextLine();
                    printRequest(schoolHelp.getListRequest("CITY", city));
                    break;
                case 3:
                    System.out.print("Request date : ");
                    String reqDate = sc.nextLine();
                    printRequest(schoolHelp.getListRequest("REQUESTDATE", reqDate));
                    break;
            }

            if (choice == 0) {
                return;
            }

            System.out.print("Enter request id to get detail: ");
            String reqId = sc.nextLine();

            showRequestDetail(reqId, volunteer.getUserId());

        } while (choice != 0);
    }

    private static void showRequestDetail(String reqId, String volunteerId) {
        Request request = schoolHelp.getRequestDetail(reqId);

        if (request instanceof TutorialRequest) {
            System.out.println(padRight("REQUEST ID", 40)
                    + padRight("DATE", 15)
                    + padRight("TIME", 10)
                    + padRight("STUDENT LEVEL", 25)
                    + padRight("NUM OF STUDENTS", 15));

            System.out.println(padRight(request.getRequestID(), 40)
                    + padRight(((TutorialRequest) request).getProposedDate(), 15)
                    + padRight(((TutorialRequest) request).getProposedTime(), 10)
                    + padRight(((TutorialRequest) request).getStudentLevel(), 25)
                    + padRight(((TutorialRequest) request).getNumStudents() + "", 15));

            questionSubmitOfferFromRequest(request.getRequestID(), volunteerId);

        } else if (request instanceof ResourceRequest) {
            System.out.println(padRight("REQUEST ID", 40)
                    + padRight("RESOURCE TYPE", 20)
                    + padRight("NUM REQUIRED", 10));
            System.out.println(padRight(request.getRequestID(), 40)
                    + padRight(((ResourceRequest) request).getResourceType(), 20)
                    + padRight(((ResourceRequest) request).getNumRequired() + "", 10));

            questionSubmitOfferFromRequest(request.getRequestID(), volunteerId);
        }
    }

    public static void questionSubmitOfferFromRequest(String reqId, String volunteerId) {
        System.out.print("Do you want to create offer for this request ? (Y/N) : ");
        String answer = sc.nextLine();
        if (answer.equals("Y")) {
            System.out.print("Enter remarks for this request : ");
            String remarks = sc.nextLine();
            schoolHelp.submitOffer(remarks, reqId, volunteerId);
            System.out.println("Your Offering has been submitted");
            System.out.println();
        }


    }

    private static void printRequest(List<Request> requests) {
        if (requests.size() < 1) {
            System.out.println("Empty Request");
            return;
        }

        System.out.println(padRight("Request ID", 40)
                + padRight("DATE", 15)
                + padRight("DESCRIPTION", 30)
                + padRight("SCHOOL NAME", 15)
                + padRight("CITY", 5));
        requests.forEach(request -> {
                    School sc = schoolHelp.getSchoolById(request.getSchoolID());
                    System.out.println(padRight(request.getRequestID(), 40)
                            + padRight(request.getRequestDate(), 15)
                            + padRight(request.getDescription(), 30)
                            + padRight(sc.getSchoolName(), 15)
                            + padRight(sc.getCity(), 5));
                }
        );
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private static void registerVolunteer() {
        System.out.println("Register Volunteer Account");
        System.out.print("Enter username : ");
        String username = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.print("Enter fullname : ");
        String fullname = sc.nextLine();
        System.out.print("Enter email : ");
        String email = sc.nextLine();
        System.out.print("Enter phone : ");
        String phone = sc.nextLine();
        System.out.print("Enter occupation : ");
        String occupation = sc.nextLine();
        System.out.print("Enter position (yyyy-MM-dd): ");
        String dob = sc.nextLine();

        schoolHelp.createNewVolunteer(username, password, fullname, email, phone, occupation, dob);
    }

    private static School loginAsSchoolAdmin() {
        System.out.println("Login as School Administrator");
        System.out.print("Enter username : ");
        String username = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.println();

        return schoolHelp.loginAsSchoolAdmin(username, password);
    }

    private static Volunteer loginAsVolunteer() {
        System.out.println("Login as Volunteer");
        System.out.print("Enter username : ");
        String username = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.println();

        User user = schoolHelp.login(username, password);

        if (user == null) {
            System.out.println("Volunteer account not found");
            return null;
        }

        if (!(user instanceof Volunteer)) {
            System.out.println("User is not Volunteer");
            return null;
        }

        return (Volunteer) user;
    }

    private static void submitRequest() {
        School school = loginAsSchoolAdmin();

        if (school == null) {
            System.out.println("Wrong credentials for School Administrator");
            return;
        }

        System.out.println("Please Input for Request Details");
        System.out.println("1. Tutorial Request");
        System.out.println("2. Resource Request");
        System.out.print("Your choice : ");
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                createTutorialRequest(school.getSchoolID());
            case "2":
                createResourceRequest(school.getSchoolID());
        }
    }

    private static void createTutorialRequest(String schoolId) {
        System.out.println("Please Input for Tutorial Request Details");
        System.out.print("Enter description : ");
        String desc = sc.nextLine();
        System.out.print("Enter proposed date (yyyy-MM-dd) : ");
        String propDate = sc.nextLine();
        System.out.print("Enter proposed date (hh:mm) : ");
        String propTime = sc.nextLine();
        System.out.print("Enter student level : ");
        String studentLevel = sc.nextLine();
        System.out.print("Enter number of expected students : ");
        int numStudents = sc.nextInt();
        sc.nextLine();

        schoolHelp.submitNewTutorialRequest("NEW", desc, schoolId, propDate,
                propTime, studentLevel, numStudents);
    }

    private static void createResourceRequest(String schoolId) {
        System.out.println("Please Input for Tutorial Request Details");
        System.out.print("Enter description : ");
        String desc = sc.nextLine();
        System.out.print("Enter Resource Type : ");
        String resourceType = sc.nextLine();
        System.out.print("Enter number of required : ");
        int numOfRequired = sc.nextInt();
        sc.nextLine();

        schoolHelp.submitNewResourceRequest("NEW", desc, schoolId, resourceType,
                numOfRequired);
    }

    private static void registerSchool() {
        System.out.println("Login as SchoolHELP Administrator ");
        System.out.print("Enter username : ");
        String username = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.println();

        if (!schoolHelp.loginAsAdmin(username, password)) {
            System.out.println("Wrong credentials for SchoolHELP Administrator");
            return;
        }

        System.out.println("Please Input School Detail");
        System.out.print("Enter school name : ");
        String schoolName = sc.nextLine();
        System.out.print("Enter school address : ");
        String schoolAddress = sc.nextLine();
        System.out.print("Enter school city : ");
        String schoolCity = sc.nextLine();
        System.out.println();

        // Check if school already exist or not. Null mean school is not exist yet. Then create new school
        // If school in not null, mean the school already exist, then force to create School Admin Account
        School existSchool = schoolHelp.isSchoolExist(schoolName, schoolAddress, schoolCity);
        if (existSchool == null) {
            School newSchool = schoolHelp.createNewSchool(schoolName, schoolAddress, schoolCity);
            createSchoolAdmin(newSchool.getSchoolID());
        } else {
            createSchoolAdmin(existSchool.getSchoolID());
        }
    }

    private static void createSchoolAdmin(String schoolId) {
        System.out.println("Create School Administrator Account");
        System.out.print("Enter username : ");
        String schoolAdminUsername = sc.nextLine();
        System.out.print("Enter password : ");
        String schoolAdminPassword = sc.nextLine();
        System.out.print("Enter fullname : ");
        String schoolAdminFullname = sc.nextLine();
        System.out.print("Enter email : ");
        String schoolAdminEmail = sc.nextLine();
        System.out.print("Enter phone : ");
        String schoolAdminPhone = sc.nextLine();
        System.out.print("Enter staff ID : ");
        String schoolAdminStaffId = sc.nextLine();
        System.out.print("Enter position : ");
        String schoolAdminPosition = sc.nextLine();

        schoolHelp.createSchoolAdmin(schoolAdminUsername, schoolAdminPassword, schoolAdminFullname, schoolAdminEmail,
                schoolAdminPhone, schoolAdminStaffId, schoolAdminPosition, schoolId);

    }

    private static void beforeRun() {
        School newSchool = schoolHelp.createNewSchool("Tadika", "Jimbaran", "Badung");

        schoolHelp.createSchoolAdmin("scadmin", "scadmin", "School Admin", "admin@schooladmin.com",
                "08379917266532", "1212121212", "pegawai", newSchool.getSchoolID());

        TutorialRequest tr = schoolHelp.submitNewTutorialRequest("NEW", "tutorial description", newSchool.getSchoolID(),
                "2022-11-28", "10:30", "5", 10);

        ResourceRequest rr1 = schoolHelp.submitNewResourceRequest("NEW", "resource description 1", newSchool.getSchoolID(),
                "mobile device", 10);

        ResourceRequest rr2 = schoolHelp.submitNewResourceRequest("NEW", "resource description 2", newSchool.getSchoolID(),
                "personal computer", 5);

        Volunteer vol = schoolHelp.createNewVolunteer("vol", "vol", "Volunteer",
                "vol@email.com", "087653773", "1997-11-12", "12");

        Volunteer vol2 = schoolHelp.createNewVolunteer("vol1", "vol1", "Volunteer 1",
                "vol@email.com", "087653773", "1995-12-12", "10");

        schoolHelp.submitOffer("Sudah saya tandai", rr1.getRequestID(), vol.getUserId());
        schoolHelp.submitOffer("Sudah saya tandai ya asu", rr1.getRequestID(), vol2.getUserId());

    }
}