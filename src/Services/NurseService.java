package Services;

import Entities.Nurse;
import Interfaces.Manageable;
import Interfaces.Searchable;
import Utils.InputHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NurseService implements Searchable, Manageable {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Nurse> nurseList = new ArrayList<>();

    public static Nurse addNurse() {

        Nurse nurse = new Nurse();

        System.out.println("\n===== Add New Nurse =====");
        System.out.println("Generated Nurse ID: " + nurse.getNurseId());
        nurse.setFirstName(InputHandler.getStringInput("Enter first name: "));
        nurse.setLastName(InputHandler.getStringInput("Enter last name: "));
        nurse.setGender();
        nurse.setDateOfBirth(InputHandler.getDateInput("Enter date of birth: "));
        nurse.setAddress(InputHandler.getStringInput("Enter address: "));
        nurse.setEmail(InputHandler.getStringInput("Enter email: "));
        nurse.setPhoneNumber(InputHandler.getStringInput("Enter phone number: "));
        nurse.setQualification(InputHandler.getStringInput("Enter Nurse Qualification: "));
        nurse.setDepartmentId();
        nurse.setShift(InputHandler.getStringInput("Enter Nurse shift: "));

        String assignedPatientInput = InputHandler.getOptionalStringInput(
                "Enter assigned Patients (comma separated, or leave empty): "
        );
        nurse.setAssignedPatients(
                assignedPatientInput.isEmpty()
                        ? new ArrayList<>()
                        : Arrays.asList(assignedPatientInput.split("\\s*,\\s*"))
        );

        System.out.println("✅ Nurse Information Collected Successfully.");
        return nurse;
    }

    public void save(Nurse nurse) {
        nurseList.add(nurse);
        System.out.println("Nurse added successfully.");
    }

    public Nurse editNurse() {
        System.out.println("\n===== Edit Nurse =====");
        String nurseId = InputHandler.getStringInput("Enter the nurse ID to edit: ").trim();

        Nurse nurseToEdit = null;
        for (Nurse nurse : nurseList) {
            if (nurse.getNurseId().equals(nurseId)) {
                nurseToEdit = nurse;
                break;
            }
        }

        if (nurseToEdit == null) {
            System.out.println("Nurse ID not found.");
            return null;
        }
        String choice = InputHandler.getStringInput("Enter choice: ").trim();

        switch (choice) {
            case "1" -> nurseToEdit.setFirstName(InputHandler.getStringInput("Enter new first name: "));
            case "2" -> nurseToEdit.setLastName(InputHandler.getStringInput("Enter new last name: "));
            case "3" -> nurseToEdit.setEmail(InputHandler.getStringInput("Enter new email: "));
            case "4" -> nurseToEdit.setPhoneNumber(InputHandler.getStringInput("Enter new phone number: "));
            case "5" -> nurseToEdit.setQualification(InputHandler.getStringInput("Enter new Qualification: "));
            case "6" -> nurseToEdit.setShift(InputHandler.getStringInput("Enter new shift: "));
            case "7" -> {
                String assignedPatient = InputHandler.getStringInput("Enter new Assigned Patients (comma separated, or leave empty): ");
                nurseToEdit.setAssignedPatients(
                        assignedPatient.isEmpty() ? new ArrayList<>() : Arrays.asList(assignedPatient.split("\\s*,\\s*"))
                );
            }
            case "8" -> nurseToEdit.setDepartmentId();
            case "0" -> {
                System.out.println("Edit cancelled.");
                return nurseToEdit;
            }
            default -> {
                System.out.println("Invalid choice.");
                return nurseToEdit;
            }
        }

        System.out.println("✅ Nurse updated successfully.");
        return nurseToEdit;
    }

    public void getNursesByDepartment() {
        String departmentId = InputHandler.getStringInput("Enter department ID to search:").trim();
        boolean found = false;
        for (Nurse nurse : nurseList) {
            if (nurse.getDepartmentId().equalsIgnoreCase(departmentId)) {
                nurse.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No nurses found in that department.");
        }
    }

    public void getNursesByShift() {
        String shift = InputHandler.getStringInput("Enter shift to search (e.g., Morning, Evening, Night):").trim();
        boolean found = false;
        for (Nurse nurse : nurseList) {
            if (nurse.getShift().equalsIgnoreCase(shift)) {
                nurse.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No nurses found for that shift.");
        }
    }

    public void searchNurseByName() {
        String name = InputHandler.getStringInput("Enter nurse first and last name: ").trim();
        boolean found = false;
        for (Nurse nurse : nurseList) {
            String fullName = nurse.getFirstName() + " " + nurse.getLastName();
            if (fullName.equalsIgnoreCase(name)) {
                System.out.println("----------------------------");
                nurse.displayInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Nurse not found");
        }
    }

    public void showAssignedPatients() {
        System.out.println("\n===== Show Assigned Patients =====");
        String nurseId = InputHandler.getStringInput("Enter Nurse ID:");

        Nurse selectedNurse = null;
        for (Nurse nurse : nurseList) {
            if (nurse.getNurseId().equalsIgnoreCase(nurseId)) {
                selectedNurse = nurse;
                break;
            }
        }

        if (selectedNurse == null) {
            System.out.println("Nurse not found!");
            return;
        }

        System.out.println("\n--- Assigned Patients for Nurse: "
                + selectedNurse.getFirstName() + " " + selectedNurse.getLastName() + " ---");
        List<String> assigned = selectedNurse.getAssignedPatients();

        if (assigned == null || assigned.isEmpty()) {
            System.out.println("No patients assigned yet.");
        } else {
            for (String patientId : assigned) {
                System.out.println("- " + patientId);
            }
        }
    }

    public void assignNurseToPatient() {
        System.out.println("\n===== Assign Nurse to Patient =====");
        String nurseId = InputHandler.getStringInput("Enter Nurse ID: ").trim();

        Nurse selectedNurse = null;
        for (Nurse nurse : nurseList) {
            if (nurse.getNurseId().equalsIgnoreCase(nurseId)) {
                selectedNurse = nurse;
                break;
            }
        }

        if (selectedNurse == null) {
            System.out.println("Nurse not found!");
            return;
        }

        String patientId = InputHandler.getStringInput("Enter Patient ID to assign: ").trim();

        List<String> assignedPatients = selectedNurse.getAssignedPatients();
        if (assignedPatients.contains(patientId)) {
            System.out.println("Patient is already assigned to this nurse.");
        } else {
            assignedPatients.add(patientId);
            selectedNurse.setAssignedPatients(assignedPatients);
            System.out.println("Patient " + patientId + " assigned to Nurse " + selectedNurse.getFirstName() + " " + selectedNurse.getLastName() + " successfully.");
        }
    }


    public static boolean checkIfNurseIdExist(String idToCheck) {
        for (Nurse nurse : nurseList) {
            if (nurse.getNurseId().equals(idToCheck)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(Object entity) {
        if (entity instanceof Nurse) {
            nurseList.add((Nurse) entity);
            System.out.println("Nurse Added successfully");
        } else {
            System.out.println("Invalid entity type. Expected Nurse.");
        }

    }


    @Override
    public void remove() {
        String nurseIdToRemove = InputHandler.getStringInput("Enter the nurse ID to remove: ").trim();
        Nurse nurseToRemove = null;
        for (Nurse nurse : nurseList) {
            if (nurse.getNurseId().equals(nurseIdToRemove)) {
                nurseToRemove = nurse;
                break;
            }
        }

        if (nurseToRemove != null) {
            nurseList.remove(nurseToRemove);
            System.out.println("Nurse removed successfully.");
        } else {
            System.out.println("Nurse not found.");
        }

    }

    @Override
    public void getAll() {
        if (nurseList.isEmpty()) {
            System.out.println("There is no nurse");
        } else {
            System.out.println("===== All Nurses =====");
            for (Nurse nurse : nurseList) {
                System.out.println("______________");
                nurse.displayInfo();
            }
            System.out.println("______________");
        }

    }

    @Override
    public void search(String keyword) {
        System.out.println("----- Search Nurses by keyword: " + keyword + " -----");
        boolean found = false;
        for (Nurse nurse : nurseList) {
            if (nurse.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                    nurse.getLastName().toLowerCase().contains(keyword.toLowerCase()) ||
                    nurse.getNurseId().toLowerCase().contains(keyword.toLowerCase()) ||
                    nurse.getDepartmentId().toLowerCase().contains(keyword.toLowerCase()) ||
                    nurse.getShift().toLowerCase().contains(keyword.toLowerCase()) ||
                    nurse.getQualification().toLowerCase().contains(keyword.toLowerCase())) {
                nurse.displayInfo();
                System.out.println("---------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No nurses found matching the keyword.");
        }

    }

    @Override
    public void searchById(String id) {
        boolean isFound = false;
        for (Nurse nurse : nurseList) {
            if (nurse.getNurseId().equals(id)) {
                nurse.displayInfo();
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Nurse ID not found");
        }

    }
    public static void addSampleNurses() {

        String[] firstNames = {"Sophia", "James", "Olivia", "Ethan", "Ava"};
        String[] lastNames = {"Johnson", "Brown", "Davis", "Wilson", "Miller"};
        String[] qualifications = {"RN", "BSN", "Diploma in Nursing", "MSN", "Associate Nursing"};
        String[] departments = {"DEP-001", "DEP-002", "DEP-004", "DEP-005", "DEP-006","DEP-007","DEP-008"};
        String[] shifts = {"Morning", "Evening", "Night", "Morning", "Evening"};

        for (int i = 0; i < 5; i++) {
            Nurse nurse = new Nurse();
            nurse.setFirstName(firstNames[i]);
            nurse.setLastName(lastNames[i]);
            nurse.setGender(i % 2 == 0 ? "Female" : "Male");
            nurse.setDateOfBirth(LocalDate.of(1990 + i, (i % 12) + 1, (i % 28) + 1));
            nurse.setAddress("Nurse Street " + (i + 1) + ", City");
            nurse.setEmail(firstNames[i].toLowerCase() + "." + lastNames[i].toLowerCase() + "@hospital.com");
            nurse.setPhoneNumber("7200000" + (i + 1));
            nurse.setQualification(qualifications[i]);
            nurse.setDepartmentId(departments[i]);
            nurse.setShift(shifts[i]);
            nurse.setAssignedPatients(new ArrayList<>(Arrays.asList("PAT-10" + (i + 1), "PAT-20" + (i + 1))));

            nurseList.add(nurse);
        }
    }

}





