class Table {

    private int size;
    private int x;
    private int y;
    private ArrayList<Student> students;

    Table(int size, int x, int y) {
        
    }

    Table(int size, int x, int y, String[] students) {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void addStudent(Student s) {
        students.add(s);
    }
}