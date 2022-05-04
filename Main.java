import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Bye!");
        } else if ("".equals(input.trim())) {
            System.out.println("No input.");
        } else {
            System.out.println("Error: unknown command!");
        }

        scanner.close();
    }
}