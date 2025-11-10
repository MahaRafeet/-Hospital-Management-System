package Utils;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    /* ===========================
       String Input
    ============================ */
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (ValidationUtils.isValidString(input)) {
                return input;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }
    public static String getOptionalStringInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextLine().trim(); // no validation, user can leave empty
    }

    /* ===========================
       Integer Input
    ============================ */
    public static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please enter digits only.");
            }
        }
    }

    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(prompt);
            if (ValidationUtils.isValidNumber(value, min, max)) {
                return value;
            } else {
                System.out.println(" Number must be between " + min + " and " + max + ".");
            }
        }
    }

    /* ===========================
       Double Input
    ============================ */
    public static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number. Please enter a valid decimal number (e.g., 50.5).");
            }
        }
    }

    /* ===========================
       Date Input
    ============================ */
    public static LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (ValidationUtils.isValidDate(input)) {
                return LocalDate.parse(input);
            } else {
                System.out.println(" Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
    public static LocalDate getOptionalDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null; // user skipped
            if (ValidationUtils.isValidDate(input)) return LocalDate.parse(input);
            System.out.println("Invalid date format. Try again.");
        }
    }


    /* ===========================
       Yes/No Confirmation
    ============================ */
    public static boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) return true;
            if (input.equals("no") || input.equals("n")) return false;
            System.out.println("Please enter 'yes' or 'no'.");
        }
    }
}
