import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();

        while (!"exit".equals(input)) {

            if (input.equalsIgnoreCase("add students")) {
                System.out.println("Enter student credentials or 'back' to return:");
                addStudent();
            } else if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if ("".equals(input.trim())) {
                System.out.println("No input.");
            } else {
                System.out.println("Error: unknown command!");
            }

            input = scanner.nextLine();
        }

        System.out.println("Bye!");
        scanner.close();
    }

    public static void addStudent() {

        String input = scanner.nextLine();
        int addedStudents = 0;
        while (!"back".equals(input)) {
            List<String> dataFields = Arrays.asList(input.split("\\s+"));

            if (dataFields.size() < 3) {
                System.out.println("Incorrect credentials.");
            } else {
                String name = dataFields.get(0);
                String lastName = String.join(" ", dataFields.subList(1, dataFields.size() - 1));
                String email = dataFields.get(dataFields.size() - 1);

                if (validateFields(name, lastName, email)) {
                    students.add(new Student(name, lastName, email));
                    addedStudents++;
                    System.out.println("The student has been added.");
                }
            }

            input = scanner.nextLine();
        }

        System.out.println("Total " + addedStudents + " students have been added.");
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
