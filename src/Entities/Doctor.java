package Entities;

import Interfaces.Displayable;
import Utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Services.DoctorService.doctorList;

public class Doctor extends Person implements Displayable {
    private String doctorId;
    private String specialization;
    private String qualification;
    private Integer experienceYears;
    private String departmentId;
    private double consultationFee;
    private List<String> availableSlots;
    private List<String> assignedPatients;

    public Doctor(String specialization, String qualification, String s, int experienceYears, String departmentId, double consultationFee, List<String> availableSlots, List<String> assignedPatients) {
        this.doctorId = ValidationUtils.generateId("DOC");
        this.specialization = specialization;
        this.qualification = qualification;
        this.experienceYears = experienceYears;
        this.departmentId = departmentId;
        this.consultationFee = consultationFee;
        this.availableSlots = availableSlots;
        this.assignedPatients = assignedPatients;
    }

    // No-arg constructor — must initialize final fields
    public Doctor() {
        super();
        this.doctorId = ValidationUtils.generateId("DOC");
    }

    public String getDoctorId() {
        return doctorId;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (!ValidationUtils.isValidString(specialization)) {
            System.out.println("Specialization cannot be empty or null.");
            return;
        }
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        if (!ValidationUtils.isValidString(qualification)) {
            System.out.println("Qualification cannot be empty or null.");
            return;
        }
        this.qualification = qualification;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        if (!ValidationUtils.isPositive(experienceYears)) {
            System.out.println("Experience years cannot be negative.");
            return;
        }
        this.experienceYears = experienceYears;
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
                System.out.println(" Department ID cannot be empty or invalid.");
                continue;
            }

            // Step 2: verify if it exists
            for (String id : departmentIds) {
                if (id.equalsIgnoreCase(inputId)) {
                    this.departmentId = id;
                    System.out.println("Department ID set successfully to " + id);
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                System.out.println("⚠️ Invalid Department ID. Please choose from the list above.\n");
            }

        } while (!valid);
    }


    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        if (!ValidationUtils.isPositive(consultationFee)) {
            System.out.println("Consultation fee cannot be negative.");
            return;
        }
        this.consultationFee = consultationFee;
    }

    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<String> availableSlots) {
        if (ValidationUtils.isNull(availableSlots)) {
            System.out.println("Available slots list cannot be null.");
            return;
        }
        this.availableSlots = availableSlots;
    }

    public List<String> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<String> assignedPatients) {
        if (ValidationUtils.isNull(assignedPatients)) {
            System.out.println("Assigned patients list cannot be null.");
            return;
        }
        this.assignedPatients = assignedPatients;
    }

    @Override
    public void displayInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("===== Doctor Info =====\n");
        sb.append(String.format("Doctor ID       : %s\n", getDoctorId()));
        sb.append(String.format("Name            : %s %s\n", getFirstName(), getLastName()));
        sb.append(String.format("Date of Birth   : %s\n", getDateOfBirth()));
        sb.append(String.format("Gender          : %s\n", getGender()));
        sb.append(String.format("Phone Number    : %s\n", getPhoneNumber()));
        sb.append(String.format("Email           : %s\n", getEmail()));
        sb.append(String.format("Address         : %s\n", getAddress()));
        sb.append(String.format("Specialization  : %s\n", getSpecialization()));
        sb.append(String.format("Qualification   : %s\n", getQualification()));
        sb.append(String.format("Experience Years: %d\n", getExperienceYears()));
        sb.append(String.format("Department ID   : %s\n", getDepartmentId()));
        sb.append(String.format("Consultation Fee: $%.2f\n", getConsultationFee()));

        sb.append("Available Slots : ");
        if (getAvailableSlots() != null && !getAvailableSlots().isEmpty()) {
            sb.append(String.join(", ", getAvailableSlots()));
        } else {
            sb.append("None");
        }
        sb.append("\n");

        sb.append("Assigned Patients: ");
        if (getAssignedPatients() != null && !getAssignedPatients().isEmpty()) {
            sb.append(String.join(", ", getAssignedPatients()));
        } else {
            sb.append("None");
        }
        sb.append("\n");
        sb.append("------------------------------\n");
        System.out.print(sb.toString());
    }




    @Override
public void displaySummery() {
    System.out.println("Doctor ID       : " + doctorId);
    System.out.println("Specialization  : " + specialization);
    System.out.println("Qualification   : " + qualification);
    System.out.println("Experience Years: " + experienceYears);
}

public void updateFee(double fee) {
    if (!ValidationUtils.isPositive(fee)) {
        System.out.println("Consultation fee cannot be negative.");
        return;
    }
    this.consultationFee = fee;
}

public void updateFee(double fee, String reason) {
    this.consultationFee = fee;
    System.out.println("Consultation fee updated to $" + fee + " due to " + reason);
}

public void addAvailability(String slot) {
    if (!ValidationUtils.isValidString(slot)) {
        System.out.println("Slot cannot be null or empty");
        return;
    }
    this.availableSlots.add(slot);
}

public void addAvailability(List<String> slots) {
    if (ValidationUtils.isNull(slots) || slots.isEmpty()) {
        System.out.println("Slots list cannot be null or empty");
        return;
    }
    this.availableSlots.addAll(slots);
}

public void setDepartmentId(String departmentId) {
    this.departmentId = departmentId;

}
public void setGender(String gender) {
    this.gender = gender;
}
}
