package Entities;

import Interfaces.Displayable;
import Services.AppointmentService;
import Services.DoctorService;
import Services.PatientService;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class MedicalRecord implements Displayable {
    private final String recordId;
    private String patientId;
    private String doctorId;
    private LocalDate visitDate;
    private String diagnosis;
    private String prescription;
    private String testResults;
    private String notes;

    public MedicalRecord(String patientId, String doctorId, LocalDate visitDate,
                         String diagnosis, String prescription, String testResults, String notes) {
        this.recordId = ValidationUtils.generateId("REC");
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.testResults = testResults;
        this.notes = notes;
    }

    public MedicalRecord() {
        this.recordId = ValidationUtils.generateId("REC");
    }

    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        String patientId;

        while (!valid) {
            System.out.print("Enter Patient ID: ");
            patientId = scanner.nextLine().trim();

            if (!ValidationUtils.isValidString(patientId)) {
                System.out.println(" Invalid patient ID. It cannot be null or empty. Please try again.");
                continue;
            }

            boolean found = false;
            for (Patient p : PatientService.patientList) { // assuming you have a patientList in PatientService
                if (p.getPatientId().trim().equalsIgnoreCase(patientId.trim())) {
                    found = true;
                    break;
                }
            }

            if (found) {
                this.patientId = patientId;
                valid = true;
                System.out.println("Patient ID verified successfully.");
            } else {
                System.out.println("Patient ID not found in the system. Please try again.");
            }
        }
    }
    public void setPatientId(String patientId ){
        this.patientId=patientId;
    }
    public void setDoctorRecId(String doctorRecId ){
        this.doctorId = doctorRecId;
    }



    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        String doctorId;

        while (!valid) {
            System.out.print("Enter Doctor ID: ");
            doctorId = scanner.nextLine().trim();

            if (!ValidationUtils.isValidString(doctorId)) {
                System.out.println("Invalid doctor ID. It cannot be null or empty. Please try again.");
                continue;
            }

            boolean found = false;
            for (Doctor d : DoctorService.doctorList) { // assuming you have a doctorList in DoctorService
                if (d.getDoctorId().trim().equalsIgnoreCase(doctorId)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                this.doctorId = doctorId;
                valid = true;
                System.out.println(" Doctor ID verified successfully.");
            } else {
                System.out.println("Doctor ID not found in the system. Please try again.");
            }
        }
    }


    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        if (ValidationUtils.isNull(visitDate)) {
            System.out.println("Visit date cannot be null.");
            return;
        }
        if (ValidationUtils.isFutureDate(visitDate)) {
            System.out.println(" Visit date cannot be in the future.");
            return;
        }
        this.visitDate = visitDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        if (!ValidationUtils.isValidString(diagnosis)) {
            System.out.println("Diagnosis can not be null .");
            return;
        }
        this.diagnosis = diagnosis.trim();
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        if (ValidationUtils.isNull(prescription)) {
            System.out.println("Prescription is empty (no medicine prescribed).");
            return;
        }
        this.prescription = prescription.trim();

    }

    public String getTestResults() {

        return testResults;
    }

    public void setTestResults(String testResults) {
        if (ValidationUtils.isNull(testResults)) {
            System.out.println("Test results not provided.");
            return;
        }
        this.testResults = testResults.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (ValidationUtils.isNull(notes)) {
            System.out.println("Test Note not provided.");
            return;
        }
        this.notes = notes.trim();
    }

    @Override
    public void displayInfo() {
        System.out.println("Record ID   : " + recordId);
        System.out.println("Patient ID  : " + patientId);
        System.out.println("Doctor ID   : " + doctorId);
        System.out.println("Visit Date  : " + visitDate);
        System.out.println("Diagnosis   : " + diagnosis);
        System.out.println("Prescription: " + prescription);
        System.out.println("Test Results: " + testResults);
        System.out.println("Notes       : " + notes);

    }


    @Override
    public void displaySummery() {
        System.out.println("Medical Record Summary:");
        System.out.println("Record ID: " + recordId +
                ", Patient ID: " + patientId +
                ", Visit Date: " + visitDate +
                ", Diagnosis: " + diagnosis);
    }

    @Override
    public String toString() {
        return "------------------------\n" +
                "Record ID  : " + recordId + "\n" +
                "Patient ID: " + patientId + "\n" +
                "Doctor ID : " + doctorId + "\n" +
                "Visit Date: " + visitDate + "\n" +
                "Diagnosis : " + diagnosis + "\n" +
                "Prescription: " + prescription + "\n" +
                "Test Results : " + testResults + "\n" +
                "Notes : " + notes + "\n" +
                "------------------------";
    }

}
