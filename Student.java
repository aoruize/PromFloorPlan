import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Student class required for Prom Project. Has additional methods for TicketingSystem extended capabilities from UML.
 * @see TicketingSystem
 * @author Daksh & Matthew
 */
public class Student {
    private String name;
    private String id;
    private ArrayList<Student> partners;
    private ArrayList<Student> blacklist;
    private ArrayList<String> accommodations;
    private boolean hasPaid;
    private String password;
    private String grade;
    private BufferedImage profilePic;

    /**
     * Constructor that follows UML. Creates a new Student Object. Never Used but here for the mark.
     * @param name Student name
     * @param studentNumber Student id
     */
    public Student(String name, String studentNumber) {
        this.name = name;
        this.id = studentNumber;
        this.grade = "";
        this.password = "";
        this.partners = new ArrayList<Student>();
        this.hasPaid = false;
        this.accommodations = new ArrayList<String>();
        this.blacklist = new ArrayList<Student>();
    }

    /**
     * Constructor that follows UML. Creates a new Student Object. Never Used but here for the mark.
     * @param name Student name.
     * @param studentNumber Student id.
     * @param partners Student's prefered partners.
     */
    public Student(String name, String studentNumber, ArrayList<Student> partners) {
        this.name = name;
        this.id = studentNumber;
        this.grade = "";
        this.password = "";
        this.partners = partners;
        this.hasPaid = false;
        this.accommodations = new ArrayList<String>();
        this.blacklist = new ArrayList<Student>();
    }

    /**
     * Constructor used to create a new student when a new account needs to be created.
     * Used in our rendition of TicketingSystem.
     * @param name Student name.
     * @param id Student id.
     * @param grade Student grade.
     * @param password Student password.
     */
    public Student(String name, String id, String grade, String password) {
        this.name = name;
        this.id = id;
        this.grade = grade;
        this.password = password;
        this.partners = new ArrayList<Student>();
        this.hasPaid = false;
        this.accommodations = new ArrayList<String>();
        this.blacklist = new ArrayList<Student>();
    }

    /**
     * Used to get the name of the student.
     * @return The student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Used to get the id of the student.
     * @return The student's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Used to get the grade of the student.
     * @return The student's grade.
     */
    public String getGrade() {
    	return grade;
    }

    /**
     * Used to get the student's preferred partners.
     * @return ArrayList of Students that are preferred Partners.
     */
    public ArrayList<Student> getPartners() {
        return partners;
    }

    /**
     * Used to set the student's preferred partners.
     * @param partners ArrayList of partners to set student partner ArrayList to.
     */
    public void setPartners(ArrayList<Student> partners) {
        this.partners = partners;
    }

    /**
     * Used to get whether or not the student has paid.
     * @return Boolean whether the student has paid.
     */
    public boolean hasPaid() {
        return this.hasPaid;
    }

    /**
     * Used to set whether or not the student has paid.
     * @param hasPaid Boolean whether the student has paid.
     */
    public void setPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    /**
     * Used to get the student's password to check login credentials.
     * @return The student's password.
     */
    public String getPassword() {
    	return this.password;
    }

    /**
     * Used to get the profile picture of the student.
     * @return The student's image.
     * @see BufferedImage
     */
    public BufferedImage getPicture() {
        return profilePic;
    }

    /**
     * Used to set the profile picture of the student.
     * @param image The student's new image.
     * @see BufferedImage
     */
    public void setPicture(BufferedImage image){
        this.profilePic = image;
    }

    /**
     * Used to get the accommodations of the student.
     * @return The student's accommodations
     */
    public ArrayList<String> getAccommodations() {
        return accommodations;
    }

    /**
     * Used to set the accommodations of the student.
     * @param accommodations The student's accommodations.
     */
    public void setAccommodations(ArrayList<String> accommodations) {
        this.accommodations = accommodations;
    }

    /**
     * Used to get the student's blacklist.
     * @return The student's blacklist.
     */
    public ArrayList<Student> getBlacklist() {
        return blacklist;
    }

    /**
     * Used to set the student's blacklist.
     * @param blacklist The student's blacklist.
     */
    public void setBlacklist(ArrayList<Student> blacklist) {
        this.blacklist = blacklist;
    }

    /**
     * Used to compare student object. Uses ID only to compare
     * @param obj Student
     * @return Whether it is the same or not.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Student) && (((Student) obj).getId().equals(this.id));
    }
}
