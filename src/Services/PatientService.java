package Services;

import Entities.*;
import Interfaces.Manageable;
import Interfaces.Searchable;

import java.time.LocalDate;
import java.util.*;

import Utils.InputHandler;
import Utils.ValidationUtils;

import java.util.stream.Collectors;

public class PatientService implements Searchable, Manageable {
    public static List<Patient> patientList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    // Add Patient with full details
    public static void addPatient(Patient patient) {
        System.out.println("\n===== Add New Patient =====");
        patient.setFirstName(InputHandler.getStringInput("Enter patient first name: "));
        patient.setLastName(InputHandler.getStringInput("Enter patient last name: "));
        patient.setAddress(InputHandler.getStringInput("Enter patient address: "));
        patient.setDateOfBirth(InputHandler.getDateInput("Enter date of birth (yyyy-MM-dd): "));
        patient.setGender();
        patient.setEmail(InputHandler.getStringInput("Enter email: "));
        patient.setPhoneNumber(InputHandler.getStringInput("Enter phone number: "));
        patient.setBloodGroup();

        String allergiesInput = InputHandler.getStringInput("Enter allergies (comma separated): ");
        patient.setAllergies(allergiesInput.isEmpty() ? new ArrayList<>() :
                Arrays.stream(allergiesInput.split(",")).map(String::trim).collect(Collectors.toList()));

        patient.setEmergencyContact(InputHandler.getStringInput("Enter emergency contact: "));
        patient.setRegistrationDate(InputHandler.getDateInput("Enter registration date (yyyy-MM-dd): "));
        patient.setInsuranceId(InputHandler.getStringInput("Enter insurance ID: "));

        System.out.println("Generated Patient ID: " + patient.getPatientId());
        patientList.add(patient);
        System.out.println("Patient added successfully!");
    }


    //  Add InPatient
    public static void addPatient(InPatient inPatient) {
        // First, collect common patient fields
        addPatient((Patient) inPatient);

        // InPatient-specific fields
        inPatient.setRoomNumber(InputHandler.getStringInput("Enter room number: "));
        inPatient.setBedNumber(InputHandler.getStringInput("Enter bed number: "));

        // Admission date
        inPatient.setAdmissionDate(InputHandler.getDateInput("Enter admission date (yyyy-MM-dd): "));

        // Discharge date
        LocalDate dischargeDate = InputHandler.getOptionalDateInput(
                "Enter discharge date (yyyy-MM-dd) or leave empty if not discharged: "
        );

        // Daily charges
        inPatient.setDailyCharges(InputHandler.getDoubleInput("Enter daily charges: "));

        // Total charges
        inPatient.setTotalCharges(inPatient.getDailyCharges()); // initialize
        inPatient.setPaid(false);

        System.out.println("InPatient added successfully!");
    }


    public static void addOutPatient(OutPatient outPatient) {
        // Collect basic patient info
        addPatient((Patient) outPatient);

        // Collect OutPatient-specific fields
        int visitCount = InputHandler.getIntInput("Enter number of visits: ");
        outPatient.setVisitCount(visitCount);

        LocalDate lastVisit = InputHandler.getDateInput("Enter last visit date (yyyy-MM-dd): ");
        outPatient.setLastVisitDate(lastVisit);

        // Select preferred doctor
        if (DoctorService.doctorList == null || DoctorService.doctorList.isEmpty()) {
            System.out.println("No doctors available. Preferred doctor not set.");
            outPatient.setPreferredDoctorId(null);
        } else {
            System.out.println("Select preferred doctor:");
            for (int i = 0; i < DoctorService.doctorList.size(); i++) {
                Doctor doc = DoctorService.doctorList.get(i);
                System.out.println((i + 1) + " - " + doc.getFirstName() + " " + doc.getLastName()
                        + " (ID: " + doc.getDoctorId() + ")");
            }

            int choice;
            do {
                choice = InputHandler.getIntInput("Enter your choice (1-" + DoctorService.doctorList.size() + "): ");
                if (choice >= 1 && choice <= DoctorService.doctorList.size()) {
                    break;
                }
                System.out.println("Invalid choice! Try again.");
            } while (true);

            Doctor selectedDoctor = DoctorService.doctorList.get(choice - 1);
            outPatient.setPreferredDoctorId(
                    (selectedDoctor != null && selectedDoctor.getDoctorId() != null)
                            ? selectedDoctor.getDoctorId()
                            : null
            );
        }
        patientList.add(outPatient);
        System.out.println("OutPatient added successfully!");

    }

    public static void addEmergencyPatient(EmergencyPatient emergencyPatient) {
        // Collect base info
        addPatient((InPatient) emergencyPatient);

        // Emergency type
        String emergencyType = InputHandler.getStringInput("Enter emergency type (e.g., Accident, Heart Attack, etc.): ");
        emergencyPatient.setEmergencyType(emergencyType);

        // Arrival mode
        String arrivalMode;
        do {
            arrivalMode = InputHandler.getStringInput("Enter arrival mode (Ambulance / Walk-in): ").trim();
            if (arrivalMode.equalsIgnoreCase("Ambulance") || arrivalMode.equalsIgnoreCase("Walk-in")) {
                break;
            }
            System.out.println("Invalid mode. Please enter 'Ambulance' or 'Walk-in'.");
        } while (true);
        emergencyPatient.setArrivalMode(arrivalMode);

        // Triage level (1–5)
        int triageLevel;
        do {
            triageLevel = InputHandler.getIntInput("Enter triage level (1-5): ");
            if (triageLevel >= 1 && triageLevel <= 5) break;
            System.out.println("Triage level must be between 1 and 5.");
        } while (true);
        emergencyPatient.setTriageLevel(triageLevel);

        // Admitted via ER
        boolean admittedViaER = InputHandler.getConfirmation("Was the patient admitted from ER? (yes/no): ");
        emergencyPatient.setAdmittedViaER(admittedViaER);

        System.out.println("Emergency patient added successfully!");
    }


    // Overloaded addPatient methods with fewer parameters
    public static void addPatient(String firstName, String lastName, String phoneNumber) {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhoneNumber(phoneNumber);
        patientList.add(patient);
        System.out.println("Patient added successfully!");
    }

    // Edit patient
    public Patient editPatient() {
        String patientId = InputHandler.getStringInput("Enter the patient ID to edit: ");

        Optional<Patient> patientOpt = patientList.stream()
                .filter(p -> p.getPatientId().equalsIgnoreCase(patientId))
                .findFirst();

        if (patientOpt.isEmpty()) {
            System.out.println("❌ The patient ID does not exist.");
            return null;
        }

        Patient updatedPatient = patientOpt.get();
        System.out.println("\nEditing patient ID: " + updatedPatient.getPatientId());

        System.out.println("""
                ─────────────────────────────
                Select field to edit:
                1 - First Name
                2 - Last Name
                3 - Address
                4 - Date of Birth
                5 - Gender
                6 - Email
                7 - Phone
                8 - Blood Group
                9 - Allergies
                0 - Exit
                ─────────────────────────────
                """);

        String choice = InputHandler.getStringInput("Enter your choice: ");

        switch (choice) {
            case "1" -> updatedPatient.setFirstName(InputHandler.getStringInput("Enter new first name: "));
            case "2" -> updatedPatient.setLastName(InputHandler.getStringInput("Enter new last name: "));
            case "3" -> updatedPatient.setAddress(InputHandler.getStringInput("Enter new address: "));
            case "4" ->
                    updatedPatient.setDateOfBirth(InputHandler.getDateInput("Enter new date of birth (yyyy-MM-dd): "));
            case "5" -> updatedPatient.setGender();
            case "6" -> updatedPatient.setEmail(InputHandler.getStringInput("Enter new email: "));
            case "7" -> updatedPatient.setPhoneNumber(InputHandler.getStringInput("Enter new phone number: "));
            case "8" -> updatedPatient.setBloodGroup();
            case "9" -> {
                String input = InputHandler.getStringInput("Enter new allergies (comma separated, or leave blank): ");
                updatedPatient.setAllergies(
                        input.isEmpty() ? new ArrayList<>() :
                                Arrays.stream(input.split(",")).map(String::trim).collect(Collectors.toList())
                );
            }
            case "0" -> {
                System.out.println("Exiting editing menu...");
                return updatedPatient;
            }
            default -> {
                System.out.println("Invalid choice. No changes made.");
                return updatedPatient;
            }
        }

        System.out.println(" Patient updated successfully!");
        return updatedPatient;
    }


    // Search by name
    public static void searchPatients(String firstName, String lastName) {
        boolean isFound = false;
        for (Patient patient : patientList) {
            if (patient.getFirstName().equalsIgnoreCase(firstName) &&
                    patient.getLastName().equalsIgnoreCase(lastName)) {
                patient.displayInfo();
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("No patient found with name: " + firstName + " " + lastName);
        }
    }


    public static void displayPatients(int limit) {
        System.out.println("----- Displaying up to " + limit + " Patients -----");
        int count = 0;
        for (Patient patient : patientList) {
            patient.displayInfo();
            System.out.println("----------------");
            if (++count >= limit) break;
        }
    }

    // Check patient ID
    public static boolean checkIfPatientIdExist(String idToCheck) {
        return patientList.stream().anyMatch(p -> p.getPatientId().equals(idToCheck));
    }

    // Implement Manageable
    @Override
    public void add(Object entity) {
        if (entity instanceof Patient) {
            patientList.add((Patient) entity);
            System.out.println("Patient added successfully");
        } else {
            System.out.println("Invalid entity type. Expected Patient.");
        }
    }

    @Override
    public void remove() {
        System.out.println("Enter Patient ID to remove: ");
        String patientId = scanner.nextLine();
        Patient patientToRemove = patientList.stream().filter(p -> p.getPatientId().equals(patientId)).findFirst().orElse(null);
        if (patientToRemove != null) {
            patientList.remove(patientToRemove);
            System.out.println("Patient removed successfully");
        } else {
            System.out.println("Patient ID not found");
        }
    }

    @Override
    public void getAll() {
        if (patientList.isEmpty()) {
            System.out.println("No patients available.");
            return;
        }
        System.out.println("===== All Patients =====");
        for (Patient patient : patientList) {
            patient.displayInfo();
            System.out.println("------------");
        }
    }

    @Override
    public void search(String keyword) {
        searchPatientMenu();
    }

    public void searchPatientMenu() {
        PatientService service = new PatientService();

        while (true) {
            System.out.println("""
                    -------------------------
                     Patient Search Menu
                    1 - Search by ID
                    2 - Search by Keyword
                    0 - Exit Search
                    -------------------------
                    """);

            String searchChoice = InputHandler.getStringInput("Enter your choice: ").trim();

            switch (searchChoice) {
                case "1" -> {
                    String patientId = InputHandler.getStringInput("Enter Patient ID to search: ");
                    service.searchById(patientId);
                }

                case "2" -> {
                    String keyword = InputHandler.getStringInput("Enter keyword to search: ").trim().toLowerCase();
                    boolean found = false;

                    for (Patient patient : patientList) {
                        boolean match =
                                (patient.getFirstName() != null && patient.getFirstName().toLowerCase().contains(keyword)) ||
                                        (patient.getLastName() != null && patient.getLastName().toLowerCase().contains(keyword)) ||
                                        (patient.getEmail() != null && patient.getEmail().toLowerCase().contains(keyword)) ||
                                        (patient.getPhoneNumber() != null && patient.getPhoneNumber().contains(keyword)) ||
                                        (patient.getAddress() != null && patient.getAddress().toLowerCase().contains(keyword)) ||
                                        (patient.getGender() != null && patient.getGender().toLowerCase().contains(keyword)) ||
                                        (patient.getPatientId() != null && patient.getPatientId().toLowerCase().contains(keyword)) ||
                                        (patient.getInsuranceId() != null && patient.getInsuranceId().toLowerCase().contains(keyword)) ||
                                        (patient.getBloodGroup() != null && patient.getBloodGroup().toLowerCase().contains(keyword)) ||
                                        (patient.getEmergencyContact() != null && patient.getEmergencyContact().contains(keyword)) ||
                                        (patient.getAllergies() != null && patient.getAllergies()
                                                .stream()
                                                .anyMatch(a -> a.toLowerCase().contains(keyword)));

                        if (match) {
                            patient.displayInfo();
                            System.out.println("-------------------------------");
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("No patients found matching: " + keyword);
                    }
                }

                case "0" -> {
                    System.out.println("Exiting search menu...");
                    return;
                }

                default -> System.out.println("⚠ Invalid choice! Please enter 1, 2, or 0.");
            }
        }
    }

    @Override
    public void searchById(String id) {
        patientList.stream()
                .filter(p -> p.getPatientId().equalsIgnoreCase(id))
                .findFirst()
                .ifPresentOrElse(
                        Patient::displayInfo,
                        () -> System.out.println("Patient ID not found: " + id)
                );
    }


}

