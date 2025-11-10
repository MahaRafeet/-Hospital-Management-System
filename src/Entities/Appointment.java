package Entities;

import Interfaces.Displayable;
import Utils.InputHandler;
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
        boolean valid = false;

        while (!valid) {
            String doctorIdInput = InputHandler.getStringInput("Enter Doctor ID: ");

            if (!ValidationUtils.isValidString(doctorIdInput)) {
                System.out.println("Doctor ID cannot be null or empty.");
                continue;
            }

            boolean found = false;
            for (Doctor doctor : doctorList) {
                if (doctor.getDoctorId().equalsIgnoreCase(doctorIdInput)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                this.doctorId = doctorIdInput;
                valid = true;
                System.out.println("Doctor ID verified successfully.");
            } else {
                System.out.println("Doctor ID not found in the system. Please try again.");
            }
        }
    }


    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate() {
        boolean valid = false;

        while (!valid) {
            LocalDate date = InputHandler.getDateInput("Enter Appointment Date (yyyy-MM-dd): ");

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
        }
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime() {
        String inputTime;
        boolean valid = false;
        while (!valid) {
            inputTime = InputHandler.getStringInput("Enter Appointment Time (e.g., 10:30 AM): ");

            if (!ValidationUtils.isValidString(inputTime)) {
                System.out.println("Appointment time cannot be null or empty. Please try again.");
                continue;
            }

            // Optional: You can add format checking (e.g., must contain ':' or AM/PM)
            if (!inputTime.matches("^(0?[1-9]|1[0-2]):[0-5][0-9]\\s?(AM|PM|am|pm)$")) {
                System.out.println("Invalid time format. Use format like '10:30 AM'.");
                continue;
            }

            this.appointmentTime = inputTime;
            valid = true;
            System.out.println("✅ Appointment time set successfully.");
        }
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        String[] allowedStatuses = {"Completed", "Scheduled", "Cancelled"};
        boolean valid = false;
        while (!valid) {
            if (!ValidationUtils.isValidString(status)) {
                System.out.println("Status cannot be null or empty, please Try Again");
                status = InputHandler.getStringInput("Enter Status: ");
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
                status = InputHandler.getStringInput("Enter status again: ");
            }
        }

        this.status = status.trim();
        System.out.println("✅ Status set successfully to: " + this.status);
    }
    public void setNewStatus(String status){
        this.status=status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason=reason;

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

    public void setPatientId(String patientId) {
        this.patientId=patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId=doctorId;
    }
}
