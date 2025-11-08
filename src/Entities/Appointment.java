package Entities;

import Interfaces.Displayable;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.util.Scanner;

import static Services.DoctorService.doctorList;
import static Services.PatientService.patientList;


public class Appointment implements Displayable {
    private final String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String status;  // Scheduled, Completed, Cancelled, Rescheduled
    private String reason;
    private String notes;


    public Appointment(String patientId, String doctorId,
                       LocalDate appointmentDate, String appointmentTime,
                       String status, String reason, String notes) {
        this.appointmentId = ValidationUtils.generateId("APT");
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.reason = reason;
        this.notes = notes;
    }

    public Appointment() {
        this.appointmentId = ValidationUtils.generateId("APT");
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId() {
        String inputId;
        boolean valid = false;

        while (!valid) {
            inputId = InputHandler.getStringInput("Enter Patient ID: ");

            if (!ValidationUtils.isValidString(inputId)) {
                System.out.println("Patient ID cannot be null or empty.");
                continue;
            }

            boolean found = false;
            for (Patient patient : patientList) {
                if (patient.getPatientId().equalsIgnoreCase(inputId)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                this.patientId = inputId;
                valid = true;
                System.out.println("✅ Patient ID verified successfully.");
            } else {
                System.out.println("Patient ID not found in the system. Please try again.");
            }
        }
    }


    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId() {
        Scanner scanner = new Scanner(System.in);
        String doctorId;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter Doctor ID: ");
            doctorId = scanner.nextLine().trim();

            // Check if empty or null
            if (!ValidationUtils.isValidString(doctorId)) {
                System.out.println("Doctor ID cannot be null or empty.");
                continue; // ask again
            }

            boolean found = false;
            // ✅ Make sure this refers to the correct service list
            for (Doctor doctor : doctorList) {
                if (doctor.getDoctorId().equalsIgnoreCase(doctorId)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                this.doctorId = doctorId;
                valid = true;
                System.out.println("Doctor ID verified successfully.");
            } else {
                System.out.println(" Doctor ID not found in the system. Please try again.");
            }
        }
    }


    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();

            try {
                LocalDate date = LocalDate.parse(input);

                if (ValidationUtils.isNull(date)) {
                    System.out.println("Appointment date cannot be null.");
                    continue;
                }

                if (ValidationUtils.isPastDate(date)) {
                    System.out.println("Appointment date cannot be in the past.");
                    continue;
                }

                this.appointmentDate = date;
                valid = true;
                System.out.println("Appointment date set successfully: " + date);

            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in format yyyy-MM-dd (e.g., 2025-12-10).");
            }
        }
    }


    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        if (!ValidationUtils.isValidString(appointmentTime)) {
            System.out.println("Appointment time cannot be null or empty.");
            return;
        }
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        Scanner scanner = new Scanner(System.in);
        // Allowed statuses
        String[] allowedStatuses = {"Completed", "Scheduled", "Canceled"};
        boolean valid = false;
        while (!valid) {

            if (!ValidationUtils.isValidString(status)) {
                System.out.println("Status cannot be null or empty, please Try Again");
                status = scanner.nextLine().trim();
                continue;
            }

            for (String s : allowedStatuses) {
                if (s.equalsIgnoreCase(status.trim())) {
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                System.out.println("Invalid status. Allowed values are: Completed, Scheduled, or Canceled.");
                System.out.print("Enter status again: ");
                status = scanner.nextLine().trim();
            }
        }

        this.status = status.trim();
        System.out.println("✅ Status set successfully to: " + this.status);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if (ValidationUtils.isNull(reason)) {
            System.out.println("Reason cannot be null.");
            return;
        }
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (ValidationUtils.isNull(notes)) {
            System.out.println("Reason cannot be null.");
            return;
        }
        this.notes = notes;
    }

    public void addNotes(String notes) {
        if (!ValidationUtils.isValidString(notes)) {
            this.notes = notes;
        } else {
            this.notes += "\n" + notes;
        }
    }

    public void addNotes(String notes, String addedBy) {
        String noteEntry = "Added by " + addedBy + ": " + notes;
        addNotes(noteEntry);

    }

    public void addNotes(String notes, String addedBy, LocalDate timestamp) {
        String noteEntry = "Added by " + addedBy + " on " + timestamp.toString() + ": " + notes;
        addNotes(noteEntry);

    }

    @Override
    public void displayInfo() {
        System.out.println("Appointment ID   : " + appointmentId);
        System.out.println("Patient ID       : " + patientId);
        System.out.println("Doctor ID : " + doctorId);
        System.out.println("Date  : " + appointmentDate);
        System.out.println("Time    : " + appointmentTime);
        System.out.println("Status  : " + status);
        System.out.println("Reason   : " + reason);
        System.out.println("Notes    : " + notes);
    }


    @Override
    public void displaySummery() {
        System.out.println("Appointment ID   : " + appointmentId);
        System.out.println("Patient ID       : " + patientId);
        System.out.println("Doctor ID : " + doctorId);
        System.out.println("Date  : " + appointmentDate);
        System.out.println("Time    : " + appointmentTime);

    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId +
                "\nPatient ID: " + patientId +
                "\nDoctor ID: " + doctorId +
                "\nDate: " + appointmentDate +
                "\nTime: " + appointmentTime +
                "\nReason: " + reason +
                "\nStatus: " + status +
                "\nNotes: " + notes;
    }
}
