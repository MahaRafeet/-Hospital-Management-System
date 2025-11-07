package Entities;

import Interfaces.Displayable;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Nurse extends Person implements Displayable {
    private final String nurseId;
    private String departmentId;
    private String shift;
    private String qualification;
    private List<String> assignedPatients;
    private static final List<String> ALLOWED_SHIFTS = Arrays.asList("Morning", "Evening", "Night");

    // ✅ Default constructor (no arguments)
    public Nurse() {
        super(); // calls Person() with default empty values
        this.nurseId = ValidationUtils.generateId("NUR");
        this.departmentId = "";
        this.shift = "Morning";
        this.qualification = "";
        this.assignedPatients = new ArrayList<>();
    }

    public Nurse(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getNurseId() {
        return nurseId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId() {
        Scanner scanner = new Scanner(System.in);

        String[] departmentIds = {
                "DEP-001", "DEP-002", "DEP-003", "DEP-004",
                "DEP-005", "DEP-006", "DEP-007", "DEP-008"
        };

        String[] departmentNames = {
                "Cardiology",
                "Neurology",
                "Orthopedics",
                "Pediatrics",
                "Obstetrics and Gynecology",
                "Emergency",
                "Radiology",
                "General Surgery"
        };

        boolean valid = false;
        String inputId;

        do {
            System.out.println("\nAvailable Departments:");
            for (int i = 0; i < departmentIds.length; i++) {
                System.out.println(departmentIds[i] + " - " + departmentNames[i]);
            }

            System.out.print("Enter Department ID: ");
            inputId = scanner.nextLine().trim();

            // Step 1: check if empty or invalid
            if (!ValidationUtils.isValidString(inputId)) {
                System.out.println("Department ID cannot be empty or invalid.");
                continue;
            }

            // Step 2: verify if it exists
            for (String id : departmentIds) {
                if (id.equalsIgnoreCase(inputId)) {
                    this.departmentId = id;
                    System.out.println("✅ Department ID set successfully to " + id);
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                System.out.println("⚠️ Invalid Department ID. Please choose from the list above.\n");
            }

        } while (!valid);
    }


    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        if (shift == null || shift.isEmpty()) {
            System.out.println("Shift cannot be null or empty.");
            return;
        }

        // Trim and convert input to lowercase
        shift = shift.trim().toLowerCase();

        // Allowed shifts (lowercase)
        List<String> allowedShifts = List.of("morning", "evening", "night");

        if (!allowedShifts.contains(shift)) {
            System.out.println("Invalid shift. Allowed options: Morning, Evening, Night");
            return;
        }

        // Store it with proper formatting (capitalize first letter)
        this.shift = shift.substring(0, 1).toUpperCase() + shift.substring(1);
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        if (!ValidationUtils.isValidString(qualification)) {
            System.out.println("Invalid qualification");
            return;
        }
        this.qualification = qualification.trim();
    }

    public List<String> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<String> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Nurse ID: " + nurseId);
        System.out.println("Department ID: " + departmentId);
        System.out.println("Shift: " + shift);
        System.out.println("Qualification: " + qualification);
        System.out.println("Assigned Patients: " +
                ((assignedPatients != null && !assignedPatients.isEmpty()) ? assignedPatients : "None"));
    }

    @Override
    public void displaySummery() {
        System.out.println("Nurse ID: " + nurseId);
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Department ID: " + departmentId);
        System.out.println("Shift: " + shift);
    }
}
