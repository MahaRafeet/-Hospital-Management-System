package Services;

import Entities.MedicalRecord;
import Interfaces.Manageable;
import Interfaces.Searchable;
import Utils.InputHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalRecordService implements Manageable, Searchable {

    public static Scanner scanner = new Scanner(System.in);
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    // ========================= ADD =========================
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        System.out.println("\n===== Add New Medical Record =====");
        medicalRecord.setPatientId();
        medicalRecord.setDoctorId();
        medicalRecord.setVisitDate(InputHandler.getDateInput("Enter Visit Date (yyyy-mm-dd): "));
        medicalRecord.setDiagnosis(InputHandler.getStringInput("Enter Diagnosis: ").trim());
        medicalRecord.setPrescription(InputHandler.getStringInput("Enter Prescription: ").trim());
        medicalRecord.setTestResults(InputHandler.getStringInput("Enter Test Results: ").trim());
        medicalRecord.setNotes(InputHandler.getStringInput("Enter Notes: ").trim());

        System.out.println("✅ The new Medical Record information collected successfully!");
        return medicalRecord;
    }

    public void saveNewRecord(MedicalRecord medicalRecord) {
        medicalRecordList.add(medicalRecord);
        System.out.println("\n✅ Medical Record added successfully!");
    }

    // ========================= EDIT =========================
    public static MedicalRecord editMedicalRecord(MedicalRecord medicalRecord) {
        System.out.println("\n===== Edit Medical Record =====");
        String recordIdToEdit = InputHandler.getStringInput("Enter the Record ID to edit:").trim();
        MedicalRecord recordToEdit = null;
        for (MedicalRecord rec : medicalRecordList) {
            if (rec.getRecordId().equalsIgnoreCase(recordIdToEdit)) {
                recordToEdit = rec;
                break;
            }
        }

        if (recordToEdit == null) {
            System.out.println("⚠️ Record not found!");
            return null;
        }

        System.out.println("Choose what you want to edit:");
        System.out.println("1- Patient ID");
        System.out.println("2- Doctor ID");
        System.out.println("3- Visit Date");
        System.out.println("4- Diagnosis");
        System.out.println("5- Prescription");
        System.out.println("6- Test Results");
        System.out.println("7- Notes");
        System.out.println("8- Exit");

        int choice = InputHandler.getIntInput("Enter your choice:");

        switch (choice) {
            case 1 -> {
                System.out.println("Current Patient ID: " + recordToEdit.getPatientId());
                recordToEdit.setPatientId();
            }
            case 2 -> {
                System.out.println("Current Doctor ID: " + recordToEdit.getDoctorId());
                recordToEdit.setDoctorId();
            }
            case 3 -> {
                System.out.println("Current Visit Date: " + recordToEdit.getVisitDate());
                recordToEdit.setVisitDate(InputHandler.getDateInput("Enter new Visit Date (yyyy-mm-dd): "));
            }
            case 4 -> {
                System.out.println("Current Diagnosis: " + recordToEdit.getDiagnosis());
                recordToEdit.setDiagnosis(InputHandler.getStringInput("Enter new Diagnosis: ").trim());
            }
            case 5 -> {
                System.out.println("Current Prescription: " + recordToEdit.getPrescription());
                recordToEdit.setPrescription(InputHandler.getStringInput("Enter new Prescription: ").trim());
            }
            case 6 -> {
                System.out.println("Current Test Results: " + recordToEdit.getTestResults());
                recordToEdit.setTestResults(InputHandler.getStringInput("Enter new Test Results: ").trim());
            }
            case 7 -> {
                System.out.println("Current Notes: " + recordToEdit.getNotes());
                recordToEdit.setNotes(InputHandler.getStringInput("Enter new Notes: ").trim());
            }
            case 8 -> {
                System.out.println("Exiting edit.");
                return recordToEdit;
            }
            default -> System.out.println("⚠️ Invalid choice!");
        }

        System.out.println("✅ Medical Record updated successfully!");
        return recordToEdit;
    }


    // ========================= SEARCH BY PATIENT =========================
//  version 1: used when you want to ask the user (like from a menu)
    public static List<MedicalRecord> getRecordsByPatientIdInteractive() {
        String patientId = InputHandler.getStringInput("Please enter Patient ID to search records:").trim();
        return getRecordsByPatientId(patientId);
    }

    // ✅ version 2: used internally (like inside displayInfo)
    public static List<MedicalRecord> getRecordsByPatientId(String patientId) {
        List<MedicalRecord> records = new ArrayList<>();
        for (MedicalRecord record : medicalRecordList) {
            if (record.getPatientId().equalsIgnoreCase(patientId)) {
                records.add(record);
            }
        }

        if (records.isEmpty()) {
            System.out.println("⚠️ No medical records found for patient ID: " + patientId);
        } else {
            System.out.println("\n===== Medical Records for Patient ID: " + patientId + " =====");
            for (MedicalRecord record : records) {
                System.out.println("------------------------");
                record.displayInfo();
            }
            System.out.println("------------------------");
        }

        return records; // always return the list
    }


    // ========================= SEARCH BY DOCTOR =========================
    public static List<MedicalRecord> getRecordsByDoctorId() {
        String doctorId = InputHandler.getStringInput("Please enter Doctor ID to search records:").trim();
        List<MedicalRecord> doctorRecords = new ArrayList<>();
        boolean found = false;
        System.out.println("\n===== Medical Records by Doctor ID: " + doctorId + " =====");
        for (MedicalRecord record : medicalRecordList) {
            if (record.getDoctorId().equalsIgnoreCase(doctorId)) {
                record.displayInfo();
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("⚠️ No records found for this doctor.");
        }
        return doctorRecords;
    }

    // ========================= PATIENT HISTORY =========================
    public void displayPatientHistory() {
        String patientId = InputHandler.getStringInput("Please enter Patient ID to view history: ").trim();
        System.out.println("\n===== Patient History for ID: " + patientId + " =====");
        boolean found = false;
        for (MedicalRecord record : medicalRecordList) {
            if (record.getPatientId().equalsIgnoreCase(patientId)) {
                System.out.println("Visit Date: " + record.getVisitDate());
                System.out.println("Diagnosis: " + record.getDiagnosis());
                System.out.println("Prescription: " + record.getPrescription());
                System.out.println("Notes: " + record.getNotes());
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("⚠️ No medical history found for this patient.");
        }
    }

    @Override
    public void add(Object entity) {
        MedicalRecord newRecord = (MedicalRecord) entity;
        saveNewRecord(newRecord);

    }


    @Override
    public void remove() {
        System.out.println("\n===== Remove Medical Record =====");
        String recordIdToRemove = InputHandler.getStringInput("Please Enter Medical To Remove:  ").trim();
        MedicalRecord recordToRemove = null;
        for (MedicalRecord rec : medicalRecordList) {
            if (rec.getRecordId().equalsIgnoreCase(recordIdToRemove)) {
                recordToRemove = rec;
                break;
            }
        }

        if (recordToRemove != null) {
            medicalRecordList.remove(recordToRemove);
            System.out.println("✅ Medical Record removed successfully!");
        } else {
            System.out.println("⚠️ Record not found in the list!");
        }

    }

    @Override
    public void getAll() {
        System.out.println("\n===== Display All Medical Records =====");
        if (medicalRecordList.isEmpty()) {
            System.out.println("There are no medical records to display.");
        } else {
            for (MedicalRecord record : medicalRecordList) {
                System.out.println("------------------------");
                record.displayInfo();
            }
            System.out.println("------------------------");
        }

    }

    @Override
    public void search(String keyword) {
        System.out.println("\n===== Search Medical Records by keyword: " + keyword + " =====");
        boolean found = false;
        for (MedicalRecord record : medicalRecordList) {
            if (record.getRecordId().toLowerCase().contains(keyword.toLowerCase()) ||
                    record.getPatientId().toLowerCase().contains(keyword.toLowerCase()) ||
                    record.getDoctorId().toLowerCase().contains(keyword.toLowerCase()) ||
                    record.getDiagnosis().toLowerCase().contains(keyword.toLowerCase()) ||
                    record.getPrescription().toLowerCase().contains(keyword.toLowerCase()) ||
                    record.getTestResults().toLowerCase().contains(keyword.toLowerCase()) ||
                    record.getNotes().toLowerCase().contains(keyword.toLowerCase())) {
                record.displayInfo();
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("⚠️ No medical records found matching the keyword.");
        }

    }

    @Override
    public void searchById(String id) {
        System.out.println("\n===== Search Medical Record by ID: " + id + " =====");
        boolean isFound = false;
        for (MedicalRecord record : medicalRecordList) {
            if (record.getRecordId().equalsIgnoreCase(id)) {
                System.out.println("Medical Record Information:");
                record.displayInfo();
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println(" Medical Record ID not found.");
        }

    }
    public static void addSampleMedicalRecords() {
        String[] diagnoses = {
                "Flu", "Cold", "Back Pain", "Headache", "Hypertension", "Diabetes",
                "Asthma", "Allergy", "Fracture", "Stomach Ache", "Skin Rash", "Eye Infection"
        };

        String[] prescriptions = {
                "Paracetamol", "Ibuprofen", "Antibiotics", "Cough Syrup", "Insulin",
                "Inhaler", "Antihistamine", "Painkillers", "Bandages", "Probiotics",
                "Cream", "Eye Drops"
        };

        String[] testResults = {
                "Normal", "High BP", "Blood Sugar High", "X-ray Clear", "MRI Required",
                "Allergy Test Positive", "Normal", "Abnormal", "Fracture Confirmed",
                "Infection Detected", "Rash Test Positive", "Vision Reduced"
        };

        String[] notes = {
                "Follow-up in 1 week", "Patient advised rest", "Monitor daily", "Urgent care needed",
                "Routine check", "Dietary changes recommended", "Medication adjusted", "Physical therapy",
                "Surgery suggested", "Observation required", "Referral to specialist", "No additional notes"
        };

        for (int i = 0; i < 12; i++) {
            MedicalRecord record = new MedicalRecord();

            // Assign patient and doctor IDs even if the lists are empty
            String patientId = (PatientService.patientList.isEmpty())
                    ? "PAI-" + String.format("%05d", i + 1)
                    : PatientService.patientList.get(i % PatientService.patientList.size()).getPatientId();
            String doctorId = (DoctorService.doctorList.isEmpty())
                    ? "DOC-" + String.format("%05d", i + 1)
                    : DoctorService.doctorList.get(i % DoctorService.doctorList.size()).getDoctorId();

            record.setPatientId(patientId);
            record.setDoctorRecId(doctorId);
            record.setVisitDate(LocalDate.now().minusDays(i * 2)); // different dates
            record.setDiagnosis(diagnoses[i % diagnoses.length]);
            record.setPrescription(prescriptions[i % prescriptions.length]);
            record.setTestResults(testResults[i % testResults.length]);
            record.setNotes(notes[i % notes.length]);

            medicalRecordList.add(record);
        }

    }


}
