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
        System.out.println("\n===== Add New Doctor =====");
        System.out.print("Enter first name: ");
        doctor.setFirstName(scanner.nextLine());
        System.out.print("Enter last name: ");
        doctor.setLastName(scanner.nextLine());
        System.out.print("Enter email: ");
        doctor.setEmail(scanner.nextLine());
        System.out.print("Enter phone number: ");
        doctor.setPhoneNumber(scanner.nextLine());
        System.out.print("Enter specialization: ");
        doctor.setSpecialization(scanner.nextLine());
        System.out.print("Enter Doctor Qualification: ");
        doctor.setQualification(scanner.nextLine());
        System.out.print("Enter Doctor department Id: ");
        doctor.setDepartmentId();
        System.out.print("Enter available slots (comma separated, e.g., 10AM-11AM,2PM-3PM ) :");
        String availableSlotsInput = scanner.nextLine();
        doctor.setAvailableSlots(availableSlotsInput.isEmpty() ? new ArrayList<>() : Arrays.asList(availableSlotsInput.split(",")));
        double fee = 0.0;
        while (true) {
            System.out.print("Enter consultation fee: ");
            String input = scanner.nextLine().trim();
            try {
                fee = Double.parseDouble(input);
                if (fee < 0) {
                    System.out.println("Consultation fee cannot be negative. Try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value (e.g., 50.0).");
            }
        }
        doctor.setConsultationFee(fee);
        System.out.print("Enter Doctor Experience years: ");
        int years = scanner.nextInt();
        doctor.setExperienceYears(years);
        scanner.nextLine();
        System.out.println("Generated Doctor ID: " + doctor.getDoctorId());
        System.out.println("Doctor information collected.");
        return doctor;
    }

    public static Surgeon addSurgeon(Surgeon surgeon) {
        addDoctor(surgeon);
        System.out.println("\n===== Add New Surgeon =====");
        System.out.print("Please enter number of Surgeries Performed: ");
        surgeon.setSurgeriesPerformed(Integer.parseInt(scanner.nextLine()));
        System.out.println("Please enter surgery types (comma separated): ");
        String surgeryTypesInput = scanner.nextLine();
        surgeon.setSurgeryTypes(surgeryTypesInput.isEmpty() ? new ArrayList<>() : Arrays.asList(surgeryTypesInput.split(",")));
        System.out.println("Does the surgeon have Operation Theatre access? (yes/no):)");
        String accessInput = scanner.nextLine();
        surgeon.setOperationTheatreAccess(accessInput.equalsIgnoreCase("yes"));
        System.out.println("Surgeon information collected.");
        return surgeon;

    }

    public static Doctor addConsultant(Consultant consultant) {
        addDoctor(consultant); // collect all shared doctor info first

        System.out.println("\n===== Add New Consultant =====");

        System.out.print("Enter consultation types (comma separated): ");
        String typesInput = scanner.nextLine();
        consultant.setConsultationTypes( typesInput.isEmpty() ? new ArrayList<>() : Arrays.asList(typesInput.split(",")) );

        System.out.print("Is online consultation available? (yes/no): ");
        String onlineInput = scanner.nextLine();
        consultant.setOnlineConsultationAvailable(onlineInput.equalsIgnoreCase("yes"));
        System.out.println(" Enter consultation duration (minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // consume newline
        consultant.setConsultationDuration(duration);

        System.out.println("✅ Consultant information collected successfully.");
        return consultant;
    }

    public static GeneralPractitioner addGeneralPractitioner(GeneralPractitioner gp) {
        System.out.println("\n===== Add New General Practitioner =====");

        // Step 1: Collect base doctor info
        addDoctor(gp); // ✅ Reuse existing doctor input logic

        // Step 2: Collect GP-specific information
        System.out.print("Is walk-in available? (yes/no): ");
        String walkinInput = scanner.nextLine().trim();
        gp.setWalkinAvailable(walkinInput.equalsIgnoreCase("yes"));

        System.out.print("Is home visit available? (yes/no): ");
        String homeVisitInput = scanner.nextLine().trim();
        gp.setHomeVisitAvailable(homeVisitInput.equalsIgnoreCase("yes"));

        System.out.print("Is the doctor vaccination certified? (yes/no): ");
        String vaccinationInput = scanner.nextLine().trim();
        gp.setVaccinationCertified(vaccinationInput.equalsIgnoreCase("yes"));

        // Step 3: Confirmation
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
        System.out.print("Enter the doctor ID to edit: ");
        String doctorId = scanner.nextLine();

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

        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.print("Enter new first name: ");
            doctorToEdit.setFirstName(scanner.nextLine());
        } else if (choice.equals("2")) {
            System.out.print("Enter new last name: ");
            doctorToEdit.setLastName(scanner.nextLine());
        } else if (choice.equals("3")) {
            System.out.print("Enter new email: ");
            doctorToEdit.setEmail(scanner.nextLine());
        } else if (choice.equals("4")) {
            System.out.print("Enter new phone number: ");
            doctorToEdit.setPhoneNumber(scanner.nextLine());
        } else if (choice.equals("5")) {
            System.out.print("Enter new specialization: ");
            doctorToEdit.setSpecialization(scanner.nextLine());
        } else if (choice.equals("6")) {
            System.out.print("Enter The Doctor available slots (comma separated ):");
            String availableSlotsInput = scanner.nextLine();
            doctorToEdit.setAvailableSlots(availableSlotsInput.isEmpty() ? new ArrayList<>() : Arrays.asList(availableSlotsInput.split(",")));

        } else if (choice.equals("0")) {
            System.out.println("Edit cancelled.");
            return doctorToEdit;
        } else {
            System.out.println("Invalid choice.");
            return doctorToEdit;
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
        System.out.println("Please enter specialization to filter doctors (or press Enter to skip):");
        String specialization = InputHandler.getStringInput("Specialization (or press Enter to skip): ").trim();

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
        System.out.println("Please enter the Doctor ID to remove:");
        String doctorIdToRemove = scanner.nextLine();
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


