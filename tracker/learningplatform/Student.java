package tracker.learningplatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Student {

    private final long id;
    private final String name;
    private final String lastName;
    private final String email;

    private final Map<Courses, List<Integer>> activities;

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

        this.activities = new HashMap<>();

        for (Courses course : Courses.values()) {
            this.activities.put(course, new ArrayList<>());
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

        if (points.get(0) > 0) {
            this.activities.get(Courses.JAVA).add(points.get(0));
        }

        if (points.get(1) > 0) {
            this.activities.get(Courses.DSA).add(points.get(1));
        }

        if (points.get(2) > 0) {
            this.activities.get(Courses.DATABASES).add(points.get(2));
        }

        if (points.get(3) > 0) {
            this.activities.get(Courses.SPRING).add(points.get(3));
        }
    }

    public String listUser() {
        return String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                this.id,
                this.activities.get(Courses.JAVA).stream().mapToInt(Integer::intValue).sum(),
                this.activities.get(Courses.DSA).stream().mapToInt(Integer::intValue).sum(),
                this.activities.get(Courses.DATABASES).stream().mapToInt(Integer::intValue).sum(),
                this.activities.get(Courses.SPRING).stream().mapToInt(Integer::intValue).sum());
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
