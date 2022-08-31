import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class Student {

    private final long id;
    private final String name;
    private final String lastName;
    private final String email;

    private final Map<Courses, Integer> reportCard;

    public Student(String name, String surname, String email) {

        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("O campo 'nome' é obrigatório");
        }

        if (surname == null || surname.length() == 0) {
            throw new IllegalArgumentException("O campo 'sobrenome' é obrigatório");
        }

        if (email == null || email.length() == 0) {
            throw new IllegalArgumentException("O campo 'email' é obrigatório");
        }

        this.name = name;
        this.lastName = surname;
        this.email = email;
        this.id = this.hashCode();

        this.reportCard = new HashMap<>();

        for (Courses course : Courses.values()) {
            this.reportCard.put(course, 0);
        }

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void updateRecord(List<Integer> points) {

        this.reportCard.put(Courses.JAVA, this.reportCard.get(Courses.JAVA) + points.get(0));
        this.reportCard.put(Courses.DSA, this.reportCard.get(Courses.DSA) + points.get(1));
        this.reportCard.put(Courses.DATABASES, this.reportCard.get(Courses.DATABASES) + points.get(2));
        this.reportCard.put(Courses.SPRING, this.reportCard.get(Courses.SPRING) + points.get(3));
    }

    public String listUser() {
        return String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                this.id, this.reportCard.get(Courses.JAVA), this.reportCard.get(Courses.DSA),
                this.reportCard.get(Courses.DATABASES), this.reportCard.get(Courses.SPRING));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Math.abs(Objects.hash(name, lastName, email));
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
