import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();

        while (!"exit".equals(input)) {

            if (input.trim().equalsIgnoreCase("add students")) {
                System.out.println("Enter student credentials or 'back' to return:");
                LearningPlatform.addStudent();
            } else if (input.trim().equalsIgnoreCase("add points")) {
                System.out.println("Enter an id and points or 'back' to return:");
                LearningPlatform.addPoints();
            } else if (input.trim().equalsIgnoreCase("find")) {
                System.out.println("Enter an id or 'back' to return:");
                LearningPlatform.find();
            } else if (input.trim().equalsIgnoreCase("list")){
                LearningPlatform.listStudents();
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
        scanner.close();
    }

}
