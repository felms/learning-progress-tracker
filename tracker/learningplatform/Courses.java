package tracker.learningplatform;

enum Courses {

    JAVA("Java"),
    DSA("Data Structures and Algorithms"),
    DATABASES("Databases"),
    SPRING("Spring");

    private final String courseName;

    Courses(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseName='" + courseName + '\'' +
                '}';
    }
}
