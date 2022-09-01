package tracker.learningplatform;

enum Courses {

    JAVA("Java", 600),
    DSA("Data Structures and Algorithms", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);

    private final String courseName;
    private final int pointsToCompletion;

    Courses(String courseName, int pointsToCompletion) {
        this.courseName = courseName;
        this.pointsToCompletion = pointsToCompletion;
    }

    public int getPointsToCompletion() {
        return this.pointsToCompletion;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseName='" + courseName + '\'' +
                '}';
    }
}
