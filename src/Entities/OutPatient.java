package Entities;

import Interfaces.Displayable;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class OutPatient extends Patient implements Displayable {
    private Integer visitCount;
    private LocalDate lastVisitDate;
    private String preferredDoctorId;

    public static Scanner scanner = new Scanner(System.in);


    public OutPatient(String firstName, String id, String lastName, String address, LocalDate dateOfBirth,
                      String phoneNumber, String email, String gender, String patientId, String bloodGroup,
                      List<String> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId,
                      int visitCount, LocalDate lastVisitDate, String preferredDoctorId) {

        super(firstName, lastName, address, dateOfBirth, phoneNumber, email,
                gender, bloodGroup, allergies, emergencyContact, registrationDate, insuranceId, patientId);

        this.visitCount = visitCount;
        this.lastVisitDate = lastVisitDate;
        this.preferredDoctorId = preferredDoctorId;
    }

    public OutPatient() {

    }

    // Getters and Setters
    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        if (visitCount < 0) {
            System.out.println("Visit count can not be negative ");
            return;
        }
        this.visitCount = visitCount;
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(LocalDate lastVisitDate) {
        if (lastVisitDate == null) {
            System.out.println("last visit can not be null");
            return;
        }
        if (ValidationUtils.isFutureDate(lastVisitDate)) {
            System.out.println("Last visit date cannot be in the future.");
            return;
        }
        this.lastVisitDate = lastVisitDate;
    }

    public String getPreferredDoctorId() {
        return preferredDoctorId;
    }

    public void setPreferredDoctorId(String preferredDoctorId) {
        if (!ValidationUtils.isValidString(preferredDoctorId)) {
            System.out.println("Invalid doctor ID.");
            return;
        }
        this.preferredDoctorId = preferredDoctorId.trim();
    }

    // Override method
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Patient ID: " + getPatientId());
        System.out.println("Visit Count: " + visitCount);
        System.out.println("Last Visit Date: " + lastVisitDate);
        System.out.println("Preferred Doctor ID: " + preferredDoctorId);
    }

    public void displaySummery() {
        System.out.println("Patient ID: " + getPatientId());
        System.out.println("Visit Count: " + visitCount);
        System.out.println("Last Visit Date: " + lastVisitDate);
    }

    // Custom methods
    public void scheduleFollowUp() {
        System.out.print("Enter follow-up date (YYYY-MM-DD): ");
        LocalDate followUpDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Follow-up appointment scheduled for: " + followUpDate);
    }

    public void updateVisitCount() {
        visitCount++;
        lastVisitDate = LocalDate.now();
        System.out.println("Visit count updated to: " + visitCount);
        System.out.println("Last visit date updated to: " + lastVisitDate);
    }
}
