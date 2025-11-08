package Services;

import Entities.*;
import Interfaces.Manageable;
import Interfaces.Searchable;
import Utils.InputHandler;
import Utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class DoctorService implements Manageable, Searchable {
    public static List<Doctor> doctorList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static Doctor addDoctor(Doctor doctor) {
        doctor.setFirstName(InputHandler.getStringInput("Enter first name: "));
        doctor.setLastName(InputHandler.getStringInput("Enter last name: "));
        doctor.setEmail(InputHandler.getStringInput("Enter email: "));
        doctor.setPhoneNumber(InputHandler.getStringInput("Enter phone number: "));
        doctor.setSpecialization(InputHandler.getStringInput("Enter specialization: "));
        doctor.setQualification(InputHandler.getStringInput("Enter Doctor Qualification: "));
        doctor.setDepartmentId();
        String availableSlotsInput = InputHandler.getStringInput(
                "Enter available slots (comma separated, e.g., 10AM-11AM,2PM-3PM): "
        );
        doctor.setAvailableSlots(
                availableSlotsInput.isEmpty() ? new ArrayList<>() : Arrays.asList(availableSlotsInput.split(","))
        );

        double fee = InputHandler.getDoubleInput("Enter consultation fee: "); // replaces manual parsing
        doctor.setConsultationFee(fee);

        int years = InputHandler.getIntInput("Enter Doctor Experience years: "); // replaces scanner.nextInt()
        doctor.setExperienceYears(years);
        return doctor;

    }

    public static Surgeon addSurgeon(Surgeon surgeon) {
        // Collect base doctor info
        addDoctor(surgeon);

        System.out.println("\n===== Add New Surgeon =====");

        // Number of surgeries performed (validated integer input)
        int surgeriesPerformed = InputHandler.getIntInput("Please enter number of Surgeries Performed: ");
        surgeon.setSurgeriesPerformed(surgeriesPerformed);

        // Surgery types (comma separated)
        String surgeryTypesInput = InputHandler.getStringInput("Please enter surgery types (comma separated): ");
        surgeon.setSurgeryTypes(
                surgeryTypesInput.isEmpty() ? new ArrayList<>() : Arrays.asList(surgeryTypesInput.split("\\s*,\\s*"))
        );

        // Operation theatre access (yes/no)
        String accessInput = InputHandler.getStringInput("Does the surgeon have Operation Theatre access? (yes/no): ");
        surgeon.setOperationTheatreAccess(accessInput.equalsIgnoreCase("yes"));

        System.out.println("✅ Surgeon information collected successfully.");
        return surgeon;
    }


    public static Doctor addConsultant(Consultant consultant) {
        addDoctor(consultant); // collect all shared doctor info first

        System.out.println("\n===== Add New Consultant =====");

        String typesInput = InputHandler.getStringInput("Enter consultation types (comma separated): ");
        consultant.setConsultationTypes(
                typesInput.isEmpty() ? new ArrayList<>() : Arrays.asList(typesInput.split(","))
        );

        consultant.setOnlineConsultationAvailable(
                InputHandler.getStringInput("Is online consultation available? (yes/no): ").equalsIgnoreCase("yes")
        );

        int duration = InputHandler.getIntInput("Enter consultation duration (minutes): ");
        consultant.setConsultationDuration(duration);


        System.out.println("✅ Consultant information collected successfully.");
        return consultant;
    }

    public static GeneralPractitioner addGeneralPractitioner(GeneralPractitioner gp) {
        System.out.println("\n===== Add New General Practitioner =====");

        // Step 1: Collect base doctor info
        addDoctor(gp);

        // Step 2: Collect GP-specific information using InputHandler
        String walkinInput = InputHandler.getStringInput("Is walk-in available? (yes/no): ").trim();
        gp.setWalkinAvailable(walkinInput.equalsIgnoreCase("yes"));

        String homeVisitInput = InputHandler.getStringInput("Is home visit available? (yes/no): ").trim();
        gp.setHomeVisitAvailable(homeVisitInput.equalsIgnoreCase("yes"));

        String vaccinationInput = InputHandler.getStringInput("Is the doctor vaccination certified? (yes/no): ").trim();
        gp.setVaccinationCertified(vaccinationInput.equalsIgnoreCase("yes"));

        System.out.println("✅ General Practitioner information collected successfully!");
        return gp;
    }



    public void save(Doctor doctor) {
        if (doctor != null) {
            doctorList.add(doctor);
            System.out.println(doctor.getClass().getSimpleName() + " added successfully!");
        } else {
            System.out.println("Cannot save null doctor.");
        }
    }


    public Doctor editDoctor() {
        System.out.println("\n===== Edit Doctor =====");
        String doctorId =InputHandler.getStringInput("Enter the doctor ID to edit:").trim();

        Doctor doctorToEdit = null;
        for (Doctor doc : doctorList) {
            if (doc.getDoctorId().equals(doctorId)) {
                doctorToEdit = doc;
                break;
            }
        }

        if (doctorToEdit == null) {
            System.out.println("Doctor ID not found.");
            return null;
        }

        System.out.println("Select what to update:");
        System.out.println("1 - First Name");
        System.out.println("2 - Last Name");
        System.out.println("3 - Email");
        System.out.println("4 - Phone Number");
        System.out.println("5 - Specialization");
        System.out.println("6 - Availability");
        System.out.println("0 - Cancel");

        String choice = InputHandler.getStringInput("Your choice: ").trim();

        switch (choice) {
            case "1" -> doctorToEdit.setFirstName(InputHandler.getStringInput("Enter new first name: "));
            case "2" -> doctorToEdit.setLastName(InputHandler.getStringInput("Enter new last name: "));
            case "3" -> doctorToEdit.setEmail(InputHandler.getStringInput("Enter new email: "));
            case "4" -> doctorToEdit.setPhoneNumber(InputHandler.getStringInput("Enter new phone number: "));
            case "5" -> doctorToEdit.setSpecialization(InputHandler.getStringInput("Enter new specialization: "));
            case "6" -> {
                String slots = InputHandler.getStringInput("Enter available slots (comma separated): ");
                doctorToEdit.setAvailableSlots(slots.isEmpty() ? new ArrayList<>() : Arrays.asList(slots.split("\\s*,\\s*")));
            }
            case "0" -> {
                System.out.println("Edit cancelled.");
                return doctorToEdit;
            }
            default -> {
                System.out.println("Invalid choice.");
                return doctorToEdit;
            }
        }


        System.out.println("Doctor updated successfully.");
        return doctorToEdit;
    }

    public void assignPatient(String doctorId, String patientId) {
        for (Doctor doc : doctorList) {
            if (doc.getDoctorId().equals(doctorId)) {
                List<String> patients = doc.getAssignedPatients();
                if (patients == null) {
                    patients = new ArrayList<>();
                }
                patients.add(patientId);
                doc.setAssignedPatients(patients);
                System.out.println("Patient assigned successfully.");
                return;
            }
        }
        System.out.println("Doctor ID not found. Cannot assign patient.");

    }

    public void assignPatient() {
        System.out.println("\n===== Assign Patient to Doctor =====");

        // Get Doctor ID
        String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        Doctor selectedDoctor = doctorList.stream()
                .filter(d -> d.getDoctorId().equalsIgnoreCase(doctorId))
                .findFirst()
                .orElse(null);

        if (selectedDoctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        // Ask user if they want to assign one or multiple patients
        String choice = InputHandler.getStringInput("Assign single patient or multiple? (S/M): ").trim();

        if (choice.equalsIgnoreCase("M")) {
            // Multiple patient IDs separated by commas
            String input = InputHandler.getStringInput("Enter patient IDs separated by commas: ");
            List<String> patientIds = Arrays.stream(input.split(","))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .toList();

            assignPatient(selectedDoctor, patientIds);

        } else {
            // Single patient
            String patientId = InputHandler.getStringInput("Enter Patient ID: ");
            Patient selectedPatient = PatientService.patientList.stream()
                    .filter(p -> p.getPatientId().equalsIgnoreCase(patientId))
                    .findFirst()
                    .orElse(null);

            if (selectedPatient == null) {
                System.out.println("Patient not found!");
                return;
            }

            assignPatient(selectedDoctor, selectedPatient);
        }
    }


    // Assign one patient
    public void assignPatient(Doctor doctor, Patient patient) {
        List<String> patients = doctor.getAssignedPatients();
        if (patients == null) {
            patients = new ArrayList<>();
        }
        patients.add(patient.getPatientId());
        doctor.setAssignedPatients(patients);
        System.out.println(" Patient assigned successfully.");
    }


    // Assign multiple patients
    public void assignPatient(Doctor doctor, List<String> patientIds) {
        List<String> patients = doctor.getAssignedPatients();
        if (patients == null) {
            patients = new ArrayList<>();
        }
        patients.addAll(patientIds);
        doctor.setAssignedPatients(patients);
        System.out.println(" Patients assigned successfully.");
    }


    public static void displayDoctors() {
        String specialization = InputHandler.getStringInput("Enter specialization to filter doctors :").trim();

        System.out.println("\n===== Doctors with Specialization: " + specialization + " =====");
        boolean found = false;

        for (Doctor doc : doctorList) {
            if (ValidationUtils.isNull(specialization) ||
                    doc.getSpecialization().trim().equalsIgnoreCase(specialization)) {
                doc.displayInfo();
                found = true;
                System.out.println("-------------");
            }
        }

        if (!found) {
            System.out.println("No doctors found with that specialization.");
        }
    }


    public void displayDoctors(String departmentId, boolean showAvailableOnly) {
        System.out.println("\n===== Doctors in Department ID: " + departmentId + " =====");
        boolean found = false;

        for (Doctor doc : doctorList) {
            if (doc.getDepartmentId().equalsIgnoreCase(departmentId)) {
                if (showAvailableOnly) {
                    if (doc.getAvailableSlots() != null && !doc.getAvailableSlots().isEmpty()) {
                        doc.displayInfo();
                        found = true;
                        System.out.println("-------------");
                    }
                } else {
                    doc.displayInfo();
                    found = true;
                    System.out.println("-------------");
                }
            }
        }

        if (!found) {
            System.out.println("No doctors found in that department.");
        }
    }


    public void getAvailableDoctors() {
        System.out.println("\n===== Available Doctors =====");
        boolean found = false;

        for (Doctor doc : doctorList) {
            if (doc.getAvailableSlots() != null && !doc.getAvailableSlots().isEmpty()) {
                doc.displayInfo();
                found = true;
                System.out.println("-------------");
            }
        }

        if (!found) {
            System.out.println("No available doctors found.");
        }
    }

    public static void displayDoctorsList() {
        System.out.println("===== Available Doctors =====");

        if (doctorList.isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }

        for (Doctor d : doctorList) {
            System.out.println(d.getDoctorId() + " - "
                    + d.getFirstName() + " "
                    + d.getLastName());
        }
    }

    @Override
    public void add(Object entity) {
        if (entity instanceof Doctor) {
            doctorList.add((Doctor) entity);
            System.out.println("Doctor Added successfully");
        } else {
            System.out.println("Invalid entity. Please provide a Doctor object.");
        }

    }

    @Override
    public void remove() {
        String doctorIdToRemove =InputHandler.getStringInput("Please enter the Doctor ID to remove:").trim();
        Doctor doctorToRemove = null;
        for (Doctor doc : doctorList) {
            if (doc.getDoctorId().equals(doctorIdToRemove)) {
                doctorToRemove = doc;
                break;
            }
        }

        if (doctorToRemove != null) {
            doctorList.remove(doctorToRemove);
            System.out.println(" Doctor removed successfully.");
        } else {
            System.out.println(" Doctor not found.");
        }

    }

    @Override
    public void getAll() {
        System.out.println("\n===== All Doctors =====");

        if (doctorList.isEmpty()) {
            System.out.println("There are no doctors in the system.");
        } else {
            for (Doctor doc : doctorList) {
                System.out.println("------------------");
                doc.displayInfo();
            }
            System.out.println("------------------");
        }

    }

    @Override
    public void search(String keyword) {
        System.out.println("\n===== Search Doctors by keyword: " + keyword + " =====");
        boolean found = false;
        for (Doctor doc : doctorList) {
            if (doc.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                    doc.getLastName().toLowerCase().contains(keyword.toLowerCase()) ||
                    doc.getDoctorId().toLowerCase().contains(keyword.toLowerCase()) ||
                    doc.getSpecialization().toLowerCase().contains(keyword.toLowerCase()) ||
                    doc.getDepartmentId().toLowerCase().contains(keyword.toLowerCase())) {
                doc.displayInfo();
                System.out.println("------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No doctors found matching the keyword.");
        }

    }

    @Override
    public void searchById(String doctorId) {
        for (Doctor doc : doctorList) {
            if (doc.getDoctorId().equals(doctorId)) {
                doc.displayInfo();
                return;
            }
        }

        System.out.println("Doctor ID not found.");

    }
}


