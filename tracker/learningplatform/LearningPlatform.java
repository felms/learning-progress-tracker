package tracker.learningplatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LearningPlatform {

    private final Scanner scanner;

    private final List<Student> students;

    private static LearningPlatform instance;

    // private para ter uma unica instancia
    private LearningPlatform() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static LearningPlatform getInstance() {

        if(instance == null) {
            instance = new LearningPlatform();
        }

        return instance;
    }


    public void progressTracker() {

        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();

        while (!"exit".equals(input)) {

            if (input.trim().equalsIgnoreCase("add students")) {
                System.out.println("Enter student credentials or 'back' to return:");
                addStudent();
            } else if (input.trim().equalsIgnoreCase("add points")) {
                System.out.println("Enter an id and points or 'back' to return:");
                addPoints();
            } else if (input.trim().equalsIgnoreCase("find")) {
                System.out.println("Enter an id or 'back' to return:");
                find();
            } else if (input.trim().equalsIgnoreCase("list")){
                listStudents();
            } else if (input.trim().equalsIgnoreCase("statistics")){
                statistics();
            } else if (input.trim().equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if ("".equals(input.trim())) {
                System.out.println("No input.");
            } else {
                System.out.println("Error: unknown command!");
            }

            input = scanner.nextLine();
        }

        System.out.println("Bye!");
    }

    private void addStudent() {

        String input = scanner.nextLine();
        int addedStudents = 0;
        while (!"back".equals(input.trim())) {
            List<String> dataFields = Arrays.asList(input.split("\\s+"));

            if (dataFields.size() < 3) {
                System.out.println("Incorrect credentials.");
            } else {
                String name = dataFields.get(0);
                String lastName = String.join(" ", dataFields.subList(1, dataFields.size() - 1));
                String email = dataFields.get(dataFields.size() - 1);

                if (validateFields(name, lastName, email)) {

                    if (students.stream().anyMatch(student -> student.getEmail().equals(email))) {
                        System.out.println("This email is already taken.");
                    } else {
                        students.add(new Student(name, lastName, email));
                        addedStudents++;
                        System.out.println("The student has been added.");
                    }

                }
            }

            input = scanner.nextLine();
        }

        System.out.println("Total " + addedStudents + " students have been added.");
    }

    private void listStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.forEach(student -> System.out.println(student.getId()));
        }
    }

    private void addPoints() {

        String input = scanner.nextLine();
        while (!"back".equals(input.trim())) {

            String[] points = input.split("\\s+");
            if (validatePointsInput(points)) {

                List<Integer> pts = Arrays.stream(points)
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList());

                int id = pts.remove(0);
                Optional<Student> s = students.stream()
                        .filter(student -> student.getId() == id)
                        .findAny();

                if (s.isPresent()) {
                    s.get().updateRecord(pts);
                    System.out.println("Points updated.");
                } else {
                    System.out.printf("No student is found for id=%d.%n", id);
                }
            }

            input = scanner.nextLine();
        }

    }

    private void find() {

        String input = scanner.nextLine();
        while (!"back".equals(input.trim())) {

            int id = Integer.parseInt(input);

            Optional<Student> s = students.stream()
                    .filter(student -> student.getId() == id)
                    .findAny();

            if (s.isPresent()) {
                System.out.println(s.get().listUser());
            } else {
                System.out.printf("No student is found for id=%d.%n", id);
            }

            input = scanner.nextLine();
        }
    }

    private void statistics() {

        StatsHelper statsHelper = new StatsHelper(this.students);

        List<Courses> mp = statsHelper.mostPopular();
        List<Courses> lp = statsHelper.leastPopular();
        mp.forEach(lp::remove); // se o curso está nos mais populares ele não pode estar nos menos populares

        List<Courses> ha = statsHelper.highestActivity();
        List<Courses> la = statsHelper.lowestActivity();
        ha.forEach(la::remove); // se um curso está nos com maior atividade ele não pode estar nos com menor atividade

        String mostPopular = this.students.isEmpty() ? "n/a"
                : mp.stream().map(Courses::getCourseName).collect(Collectors.joining(", "));
        String leastPopular = this.students.isEmpty() || lp.isEmpty() ? "n/a"
                : lp.stream().map(Courses::getCourseName).collect(Collectors.joining(", "));
        String highestActivity = this.students.isEmpty() ? "n/a"
                : ha.stream().map(Courses::getCourseName).collect(Collectors.joining(", "));
        String lowestActivity = this.students.isEmpty() || la.isEmpty() ? "n/a"
                : la.stream().map(Courses::getCourseName).collect(Collectors.joining(", "));
        String easiestCourse = this.students.isEmpty() ? "n/a"
                : statsHelper.easiestCourse().stream().map(Courses::getCourseName)
                .collect(Collectors.joining(", "));
        String hardestCourse = this.students.isEmpty() ? "n/a"
                : statsHelper.hardestCourse().stream().map(Courses::getCourseName)
                .collect(Collectors.joining(", "));

        System.out.printf(
                """
                        Type the name of a course to see details or 'back' to quit:
                        Most popular: %s
                        Least popular: %s
                        Highest activity: %s
                        Lowest activity: %s
                        Easiest course: %s
                        Hardest course: %s%n""",
                mostPopular, leastPopular, highestActivity,
                lowestActivity, easiestCourse, hardestCourse);

        List<String> courseNames = Arrays.stream(Courses.values())
                .map(course -> course.getCourseName().toLowerCase()).toList();

        String input = scanner.nextLine();
        while (!"back".equals(input.trim())) {

            input = input.toLowerCase();
            if (courseNames.contains(input)) {

                String finalInput = input;
                Courses course = Arrays.stream(Courses.values())
                        .filter(c -> c.getCourseName().toLowerCase().equals(finalInput))
                        .findAny().get();

                List<Map.Entry<Student, Integer>> studentPerformance = statsHelper.studentPerfomanceByCourse(course)
                        .entrySet().stream()
                        .sorted((entry0, entry1) -> entry1.getValue() - entry0.getValue()) // tem que ser ordenado
                        .toList();                                                         // pelos pontos

                System.out.printf("""
                        %s
                        id\tpoints\tcompleted
                        """, course.getCourseName());

                studentPerformance.forEach(entry -> {

                    Student student = entry.getKey();
                    int points = entry.getValue();
                    double percentage = (double) points / (double) course.getPointsToCompletion();
                    percentage = percentage * 100;

                    String s = String.format("%.1f", percentage) + "%";
                    System.out.printf("%d\t%d\t%s\n", student.getId(), points, s);

                });

            } else {
                System.out.println("Unknown course.");
            }

            input = scanner.nextLine();
        }
    }

    private boolean validatePointsInput(String[] points) {

        if (points.length != 5) {
            System.out.println("Incorrect points format.");
            return false;
        }

        try {
            Integer.parseInt(points[0]);
        } catch (Exception e) {
            System.out.printf("No student is found for id=%s.%n", points[0]);
            return false;
        }

        try {
            if (Arrays.stream(points)
                    .mapToInt(Integer::parseInt)
                    .anyMatch(n -> n < 0)) {
                System.out.println("Incorrect points format.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect points format");
            return false;
        }
        return true;
    }

    private boolean validateFields(String name, String lastName, String email) {

        if (!email.matches("^[a-z0-9.]+@[a-z0-9]+\\.([a-z0-9]+)?$")) {
            System.out.println("Incorrect email.");
            return false;
        }

        if (!name.matches("^[a-zA-Z]+['-]{0,1}[a-zA-Z]+$")) {
            System.out.println("Incorrect first name.");
            return false;
        }

        List<String> lastNames = Arrays.asList(lastName.split("\\s+"));
        boolean isValidLastName = lastNames.stream()
                .allMatch(lName -> lName.matches("^[a-zA-Z]+((['-]{0,1}[a-zA-Z])|[a-zA-Z])+"));
        if (!isValidLastName) {
            System.out.println("Incorrect last name.");
            return false;
        }
        return true;
    }
}
