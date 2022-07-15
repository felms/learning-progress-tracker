import java.util.Objects;

public class Student {

    private final String name;
    private final String lastName;
    private final String email;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, email);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
