import java.util.ArrayList;

class Student {

    private String name;
    private String id;
    private ArrayList<Student> partners;
    private boolean paid;

    Student(String name, String id, ArrayList<Student> partners, boolean paid) {

    }

    Student(String name, String id) {

    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPartners(ArrayList<Student> partners) {
        this.partners = partners;
    }

    public ArrayList<Student> getPartners() {
        return partners;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean hasPaid() {
        return paid;
    }

    public boolean equals(Student s) {
        if (this.getId().equals(s.getId()) && this.getName().equals(s.getName())) {
            return true;
        }
        else {
            return false;
        }
    }

}