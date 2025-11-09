package Entities;

import Interfaces.Displayable;
import Services.AppointmentService;
import Services.MedicalRecordService;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Services.MedicalRecordService.getRecordsByPatientId;
import static Services.PatientService.scanner;

public class Patient extends Person implements Displayable {
    private final String patientId;
    private String bloodGroup;
    private List<String> allergies;
    private String emergencyContact;
    private LocalDate registrationDate;
    private String insuranceId;

    public Patient(String firstName, String lastName, String address, LocalDate dateOfBirth,
                   String phoneNumber, String email, String gender,
                   String bloodGroup, List<String> allergies, String emergencyContact,
                   LocalDate registrationDate, String insuranceId, String patientId) {

        super(firstName, lastName, address, dateOfBirth, phoneNumber, email, gender);
        this.patientId = patientId != null ? patientId : ValidationUtils.generateId("PAI");
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.insuranceId = insuranceId;
    }

    public Patient() {
        super(); // calls default Person constructor
        this.patientId = ValidationUtils.generateId("PAI");
    }

    public String getPatientId() {
        return patientId;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        Scanner scanner = new Scanner(System.in); // or pass a scanner from the service
        while (!ValidationUtils.isValidString(insuranceId)) {
            System.out.println("Invalid insurance ID. Please enter a valid insurance ID:");
            insuranceId = scanner.nextLine();
        }
        this.insuranceId = insuranceId.trim();
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        Scanner scanner = new Scanner(System.in);

        while (registrationDate == null || ValidationUtils.isFutureDate(registrationDate)) {
            if (registrationDate == null) {
                System.out.println("Registration date cannot be null. Please enter a valid date (yyyy-MM-dd):");
            } else if (ValidationUtils.isFutureDate(registrationDate)) {
                System.out.println("Registration date cannot be in the future. Enter a valid date (yyyy-MM-dd):");
            }

            String input = scanner.nextLine();
            try {
                registrationDate = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                registrationDate = null; // keep looping
            }
        }

        this.registrationDate = registrationDate;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        Scanner scanner = new Scanner(System.in);
        while (!ValidationUtils.isValidString(emergencyContact)) {
            System.out.println("Invalid emergency contact. Please enter a valid emergency contact:");
            emergencyContact = scanner.nextLine();
        }
        this.emergencyContact = emergencyContact.trim();
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        Scanner scanner = new Scanner(System.in);

        // Keep asking until a valid list is provided
        while (allergies == null || allergies.isEmpty()) {
            System.out.println("Enter allergies (comma separated), or 'none' if no allergies:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("none")) {
                this.allergies = new ArrayList<>();
                return;
            }

            String[] allergyArray = input.split(",");
            List<String> cleanedAllergies = new ArrayList<>();
            for (String allergy : allergyArray) {
                allergy = allergy.trim();
                if (!allergy.isEmpty()) {
                    cleanedAllergies.add(allergy);
                }
            }

            if (!cleanedAllergies.isEmpty()) {
                this.allergies = cleanedAllergies;
                return;
            } else {
                System.out.println("Invalid input. Please enter at least one allergy or 'none'.");
            }
        }

        // If a valid list was passed as parameter, just use it
        this.allergies = allergies;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup() {
        Scanner scanner = new Scanner(System.in);
        List<String> validBloodGroups = List.of("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
        String bloodGroup = null;

        while (bloodGroup == null || !validBloodGroups.contains(bloodGroup.toUpperCase())) {
            System.out.println("Enter blood group (A+, A-, B+, B-, O+, O-, AB+, AB-):");
            bloodGroup = scanner.nextLine().trim();
            if (!validBloodGroups.contains(bloodGroup.toUpperCase())) {
                System.out.println("Invalid blood group. Try again.");
            }
        }

        this.bloodGroup = bloodGroup.toUpperCase();
    }

    // ===== Methods to access related data =====
    public List<MedicalRecord> getMedicalRecords(String patientId) {

        return getRecordsByPatientId(patientId);
    }

    public List<Appointment> getAppointments() {
        return AppointmentService.getAppointmentsByPatientIdInteractive();
    }

    public static List<MedicalRecord> getRecordsByPatientIdInteractive() {
        System.out.print("Please enter Patient ID to search records: ");
        String patientId = scanner.nextLine().trim();
        return getRecordsByPatientId(patientId);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Patient ID :" + patientId);
        System.out.println("Blood Group : " + bloodGroup);
        System.out.println("Allergies: ");
        if (allergies != null && !allergies.isEmpty()) {
            System.out.println(allergies);

        } else {
            System.out.println("None");
        }
        System.out.println("Emergency Contact : " + emergencyContact);
        System.out.println("Registration Date :" + registrationDate);
        System.out.println("Insurance Id :" + insuranceId);
        System.out.println("Medical Records:");
        List<MedicalRecord> records = MedicalRecordService.getRecordsByPatientId(this.patientId);

        if (records != null && !records.isEmpty()) {
            for (MedicalRecord r : records) {
                System.out.println(r);
            }
        } else {
            System.out.println("None");
        }

        System.out.println("Appointments:");
        List<Appointment> apps = AppointmentService.getAppointmentsByPatientId(this.patientId);

        if (apps != null && !apps.isEmpty()) {
            for (Appointment a : apps) {
                System.out.println(a.toString());
            }
        } else {
            System.out.println("None");
        }
    }

    @Override
    public void displaySummery() {
        System.out.println("Patient ID :" + patientId);
        System.out.println("Patient name : " + getFirstName()+ " "+ getLastName());
        System.out.println("Blood Group : " + bloodGroup);
    }

    public void updateContact(String phone) {
        setPhoneNumber(phone);
    }

    public void updateContact(String phone, String email) {
        setPhoneNumber(phone);
        setEmail(email);
    }

    public void updateContact(String phone, String email, String address) {
        setPhoneNumber(phone);
        setEmail(email);
        setAddress(address);
    }

    public void setGender(String s) {
    }

    public void setBloodGroup(String s) {
    }
}
