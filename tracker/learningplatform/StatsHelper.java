package tracker.learningplatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class StatsHelper {

    private final List<Student> students;
    private  List<Courses> mPopular;
    private  List<Courses> lPopular;

    private List<Courses> hActivity;
    private List<Courses> lActivity;

    private List<Courses> eCourse;
    private List<Courses> hCourse;

    public StatsHelper(List<Student> students) {

        this.students = new ArrayList<>(students);
        this.measureCoursesPopularity();
        this.measureCoursesActivity();
        this.measureCoursesDifficulty();

    }

    public List<Courses> mostPopular() {

        return this.mPopular;
    }

    public List<Courses> leastPopular() {

        return this.lPopular;
    }

    public List<Courses> highestActivity() {

        return this.hActivity;
    }

    public List<Courses> lowestActivity() {

        return this.lActivity;
    }

    public List<Courses> easiestCourse() {

        return this.eCourse;
    }

    public List<Courses> hardestCourse() {

        return this.hCourse;
    }

    public String courseDetails(Courses course) {

        // TODO
        return null;
    }

    private void measureCoursesPopularity() {

        // Calcula os estudantes matriculados em cada curso
        Map<Courses, Integer> studentsByCourse = new HashMap<>();

        Arrays.stream(Courses.values()).forEach(course -> {
            int numberOfStudents = (int) this.students.stream()
                    .filter(student -> student.getActivities().get(course).size() > 0)
                    .count();

            studentsByCourse.put(course, numberOfStudents);
        });

        // Calcula os cursos com mais alunos
        int maxEnrolledStudents = studentsByCourse.values().stream().max(Integer::compareTo).orElse(0);

        this.mPopular = new ArrayList<>();
        studentsByCourse.forEach((course, numberOfStudents) -> {
            if (numberOfStudents == maxEnrolledStudents) {
                this.mPopular.add(course);
            }
        });


        // Calcula os cursos com menos alunos
        int minEnrolledStudents = studentsByCourse.values().stream().min(Integer::compareTo).orElse(0);

        this.lPopular = new ArrayList<>();
        studentsByCourse.forEach((course, numberOfStudents) -> {
            if (numberOfStudents == minEnrolledStudents) {
                this.lPopular.add(course);
            }
        });

    }

    private void measureCoursesActivity() {

        // Calcula a quantidade de tarefas feitas por curso
        Map<Courses, Integer> activitiesByCourse = new HashMap<>();

        Arrays.stream(Courses.values()).forEach(course -> {
            int numberOfActivities = (int) this.students.stream()
                    .map(student -> student.getActivities().get(course).size())
                    .count();

            activitiesByCourse.put(course, numberOfActivities);
        });

        // Calcula os cursos com mais tarefas feitas
        int maxActivities = activitiesByCourse.values().stream().max(Integer::compareTo).orElse(0);

        this.hActivity = activitiesByCourse.entrySet().stream()
                .filter(entry -> entry.getValue() == maxActivities)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        // Calcula os cursos com menos tarefas feitas
        int minActivities = activitiesByCourse.values().stream().min(Integer::compareTo).orElse(0);

        this.lActivity = activitiesByCourse.entrySet().stream()
                .filter(entry -> entry.getValue() == minActivities)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void measureCoursesDifficulty() {

        // Calcula a media por curso
        Map<Courses, Double> averageByCourse = new HashMap<>();

        Arrays.stream(Courses.values()).forEach(course -> {
            double numberOfAssignments = 0;
            double sumOfGrades = 0;

            for (Student student : this.students) {
                List<Integer> activities = student.getActivities().get(course);
                numberOfAssignments += activities.size();
                sumOfGrades += activities.stream().mapToInt(Integer::intValue).sum();
            }

            averageByCourse.put(course, (sumOfGrades / numberOfAssignments));

        });

        // Calcula o mais fácil
        double highestAverage = averageByCourse.values().stream().max(Double::compareTo).orElse(0.0);
        this.eCourse = averageByCourse.entrySet().stream()
                .filter(entry -> entry.getValue() == highestAverage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        // Calcula o mais dificil
        double lowestAverage = averageByCourse.values().stream().min(Double::compareTo).orElse(0.0);
        this.hCourse = averageByCourse.entrySet().stream()
                .filter(entry -> entry.getValue() == lowestAverage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

}
