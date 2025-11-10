package Entities;

import Interfaces.Billable;
import Interfaces.Displayable;
import Utils.InputHandler;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class InPatient extends Patient implements Displayable, Billable {
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String roomNumber;
    private String bedNumber;
    private String admittingDoctorId;
    private double dailyCharges;
    private double totalCharges;
    private boolean isPaid;

    public static Scanner scanner = new Scanner(System.in);


    public InPatient(String firstName, String lastName, String address, LocalDate dateOfBirth,
                     String phoneNumber, String email, String gender,
                     String patientId, String bloodGroup, List<String> allergies,
                     String emergencyContact, LocalDate registrationDate, String insuranceId,
                     LocalDate admissionDate, LocalDate dischargeDate, String roomNumber,
                     String bedNumber, String admittingDoctorId, double dailyCharges) {
        super(firstName, lastName, address, dateOfBirth, phoneNumber, email, gender,
                bloodGroup, allergies, emergencyContact, registrationDate, insuranceId, patientId);

        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.admittingDoctorId = admittingDoctorId;
        this.dailyCharges = dailyCharges;
    }

    public InPatient() {
        super();
    }

    public double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(double totalCharges) {
        if (ValidationUtils.isNegative(totalCharges)) {
            System.out.println("Total charge can not be negative ");
        } else {
            this.totalCharges = totalCharges;
        }
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid() {
        this.isPaid = true;
    }
    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        if (admissionDate == null) {
            System.out.println("Admission Date cannot be null");
            return;
        }
        if (ValidationUtils.isFutureDate(admissionDate)) {
            System.out.println("Admission Date cannot be in the future");
            return;
        }
        this.admissionDate = admissionDate;
    }


    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate, LocalDate admissionDate) {
        if (dischargeDate == null) {
            System.out.println(" Discharge date cannot be null");
            return;
        }

        if (dischargeDate.isBefore(admissionDate)) {
            System.out.println("Discharge date cannot be before admission date");
            return;
        }

        this.dischargeDate = dischargeDate;
    }



    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        if (!ValidationUtils.isValidString(roomNumber)) {
            System.out.println("Invalid room number");
            return;
        }

        this.roomNumber = roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        if (!ValidationUtils.isValidString(bedNumber)) {
            System.out.println("Invalid bed number ");
            return;
        }
        this.bedNumber = bedNumber;
    }

    public String getAdmittingDoctorId() {
        return admittingDoctorId;
    }

    public void setAdmittingDoctorId(String admittingDoctorId) {
        if (!ValidationUtils.isValidString(admittingDoctorId)) {
            System.out.println("Invalid doctor Id");
            return;
        }
        this.admittingDoctorId = admittingDoctorId;
    }

    public double getDailyCharges() {
        return dailyCharges;
    }

    public void setDailyCharges(double dailyCharges) {
        if (ValidationUtils.isNegative(dailyCharges)) {
            System.out.println("Daily charge can not be negative ");
            return;
        }
        this.dailyCharges = dailyCharges;
    }

    // Override method to show all patient details
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Admission Date: " + admissionDate);
        System.out.println("Discharge Date: " + dischargeDate);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Bed Number: " + bedNumber);
        System.out.println("Admitting Doctor ID: " + admittingDoctorId);
        System.out.println("Daily Charges: " + dailyCharges);
    }

    @Override
    public void displaySummery() {
        super.displaySummery();
        System.out.println("InPatient Summary:");
        System.out.println("Patient ID: " + getPatientId());
        System.out.println("Admission Date: " + admissionDate);
        System.out.println("Discharge Date: " + dischargeDate);
        System.out.println("Room Number: " + roomNumber);
    }

    // Custom methods
    public void calculateStayDuration() {
        if (admissionDate == null || dischargeDate == null) {
            System.out.println("Admission or discharge date not set.");
        } else {
            long days = ChronoUnit.DAYS.between(admissionDate, dischargeDate);
            System.out.println("Stay Duration: " + days + " days");
        }
    }


    @Override
    public void calculateCharges() {
        if (admissionDate == null || dischargeDate == null) {
            System.out.println("Cannot calculate total charges without both dates.");
        } else {
            long days = ChronoUnit.DAYS.between(admissionDate, dischargeDate);
            double total = days * dailyCharges;
            System.out.println("Total Charges: " + total + " OMR");
        }
    }

    @Override
    public void generateBill() {
        System.out.println("===== Patient Bill =====");
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Total Charges: " + totalCharges + " OMR");
        System.out.println("Status: " + (isPaid ? "Paid" : "Unpaid"));
        System.out.println("========================");

    }

    @Override
    public void processPayment() {
        if (totalCharges == 0) {
            System.out.println(" Please calculate charges first.");
            return;
        }

        double amount = InputHandler.getDoubleInput("Enter payment amount (OMR): ");

        System.out.println("\nProcessing payment of: " + amount + " OMR");

        if (amount >= totalCharges) {
            isPaid = true;
            double change = amount - totalCharges;
            System.out.println("Payment successful! Change: " + change + " OMR");
            setPaid(); // mark as paid
            generateBill(); // show final bill
        } else {
            double remaining = totalCharges - amount;
            System.out.println("Partial payment received. Remaining balance: " + remaining + " OMR");
            isPaid = false;
            generateBill(); // show partial bill
        }
    }
}
