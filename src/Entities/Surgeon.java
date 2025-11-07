package Entities;

import Interfaces.Displayable;

import java.util.List;
import java.util.Scanner;

public class Surgeon extends Doctor implements Displayable {
    private Integer surgeriesPerformed;
    private List<String> surgeryTypes;
    private boolean operationTheatreAccess;
    public static final Scanner scanner = new Scanner(System.in);

    public Surgeon(String doctorId, String specialization, String qualification, int experienceYears,
                   String departmentId, double consultationFee, List<String> availableSlots,
                   List<String> assignedPatients, Integer surgeriesPerformed) {
        super(doctorId, specialization, qualification, experienceYears, departmentId, consultationFee,
                availableSlots, assignedPatients);
        setSurgeriesPerformed(surgeriesPerformed);
    }

    public Surgeon() {
        super();
    }

    public Integer getSurgeriesPerformed() {
        return surgeriesPerformed;
    }

    // Setter with validation
    public void setSurgeriesPerformed(Integer surgeriesPerformed) {
        if (surgeriesPerformed == null || surgeriesPerformed < 0) {
            System.out.println("Invalid number of surgeries. Must be 0 or positive.");
            this.surgeriesPerformed = 0;
        } else {
            this.surgeriesPerformed = surgeriesPerformed;
        }
    }

    // Interactive setter
    public void inputSurgeriesPerformed() {
        while (true) {
            try {
                System.out.print("Enter number of surgeries performed: ");
                Integer value = Integer.parseInt(scanner.nextLine());
                if (value < 0) {
                    System.out.println("Number cannot be negative.");
                    continue;
                }
                this.surgeriesPerformed = value;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid integer.");
            }
        }
    }

    public List<String> getSurgeryTypes() {
        return surgeryTypes;
    }

    public void setSurgeryTypes(List<String> surgeryTypes) {
        if (surgeryTypes == null) {
            System.out.println("Surgery types list cannot be null.");
            this.surgeryTypes = List.of();
        } else {
            this.surgeryTypes = surgeryTypes;
        }
    }

    public boolean isOperationTheatreAccess() {
        return operationTheatreAccess;
    }

    public void setOperationTheatreAccess(boolean operationTheatreAccess) {
        this.operationTheatreAccess = operationTheatreAccess;
    }

    public void performSurgery() {
        if (!operationTheatreAccess) {
            System.out.println("Surgeon does not have access to the operation theatre!");
        } else {
            System.out.println("Performing surgery...");
        }
    }

    public void updateSurgeryCount() {
        System.out.println("Current number of surgeries: " + (surgeriesPerformed != null ? surgeriesPerformed : 0));
        while (true) {
            System.out.print("Enter the additional number of surgeries: ");
            try {
                int additional = Integer.parseInt(scanner.nextLine());
                if (additional < 0) {
                    System.out.println("Number cannot be negative.");
                    continue;
                }
                surgeriesPerformed = (surgeriesPerformed != null ? surgeriesPerformed : 0) + additional;
                System.out.println("Updated number of surgeries: " + surgeriesPerformed);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Surgeries Performed     : " + (surgeriesPerformed != null ? surgeriesPerformed : "N/A"));
        System.out.println("Surgery Types           : " + (surgeryTypes != null && !surgeryTypes.isEmpty() ? surgeryTypes : "None"));
        System.out.println("Operation Theatre Access: " + (operationTheatreAccess ? "Yes" : "No"));
    }

    @Override
    public void displaySummery() {
        super.displaySummery();
        System.out.println("Surgeries Performed: " + (surgeriesPerformed != null ? surgeriesPerformed : "N/A"));
    }
}
