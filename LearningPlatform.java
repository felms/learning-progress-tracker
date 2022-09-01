import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LearningPlatform {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Student> students = new ArrayList<>();

    private LearningPlatform() {
      // No instances. Only static methods
    }

    public static void addStudent() {

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

    public static void listStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.forEach(student -> System.out.println(student.getId()));
        }
    }

    public static void addPoints() {

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

    public static void find() {

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

    private static boolean validatePointsInput(String[] points) {

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

    private static boolean validateFields(String name, String lastName, String email) {

        if (!email.matches("^[a-z0-9.]+@[a-z0-9]+\\.([a-z0-9]+)?$")) {
            System.out.println("Incorrect email.");
            return false;
        }

        if (!name.matches("^[a-zA-Z]+['-]{0,1}[a-zA-Z]+$")) {
            System.out.println("Incorrect first name.");
            return false;
        }

        List<String> lastNames = Arrays.asList(lastName.split("\\s+"));
        boolean isValidLastName = lastNames.stream().allMatch(lName -> lName.matches("^[a-zA-Z]+((['-]{0,1}[a-zA-Z])|[a-zA-Z])+"));
        if (!isValidLastName) {
            System.out.println("Incorrect last name.");
            return false;
        }
        return true;
    }
}
