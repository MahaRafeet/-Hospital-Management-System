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
        System.out.print("Enter first name: ");
        nurse.setFirstName(scanner.nextLine());

        System.out.print("Enter last name: ");
        nurse.setLastName(scanner.nextLine());

        nurse.setGender();

        System.out.print("Enter date of birth (yyyy-mm-dd): ");
        String dobStr = scanner.nextLine();
        nurse.setDateOfBirth(LocalDate.parse(dobStr));

        System.out.print("Enter address: ");
        nurse.setAddress(scanner.nextLine());

        System.out.print("Enter email: ");
        nurse.setEmail(scanner.nextLine());

        System.out.print("Enter phone number: ");
        nurse.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Nurse Qualification: ");
        nurse.setQualification(scanner.nextLine());

        System.out.print("Enter Nurse department Id: ");
        nurse.setDepartmentId();

        System.out.print("Enter Nurse shift: ");
        nurse.setShift(scanner.nextLine());

        System.out.print("Enter assigned Patients (comma separated): ");
        String assignedPatientInput = scanner.nextLine();
        nurse.setAssignedPatients(
                assignedPatientInput.isEmpty()
                        ? new ArrayList<>()
                        : Arrays.asList(assignedPatientInput.split(","))
        );

        System.out.println("Nurse Information Collected Successfully.");

        return nurse;

    }

    public void save(Nurse nurse) {
        nurseList.add(nurse);
        System.out.println("Nurse added successfully.");
    }

    public Nurse editNurse() {
        System.out.println("\n===== Edit Nurse =====");
        System.out.print("Enter the nurse ID to edit: ");
        String nurseId = scanner.nextLine();

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

        System.out.println("Select what to update:");
        System.out.println("1 - First Name");
        System.out.println("2 - Last Name");
        System.out.println("3 - Email");
        System.out.println("4 - Phone Number");
        System.out.println("5 - Qualification");
        System.out.println("6 - Shift");
        System.out.println("7 - Assigned Patients");
        System.out.println("8- Department ID");
        System.out.println("0 - Cancel");

        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.print("Enter new first name: ");
            nurseToEdit.setFirstName(scanner.nextLine());
        } else if (choice.equals("2")) {
            System.out.print("Enter new last name: ");
            nurseToEdit.setLastName(scanner.nextLine());
        } else if (choice.equals("3")) {
            System.out.print("Enter new email: ");
            nurseToEdit.setEmail(scanner.nextLine());
        } else if (choice.equals("4")) {
            System.out.print("Enter new phone number: ");
            nurseToEdit.setPhoneNumber(scanner.nextLine());
        } else if (choice.equals("5")) {
            System.out.print("Enter new Qualification: ");
            nurseToEdit.setQualification(scanner.nextLine());
        } else if (choice.equals("6")) {
            System.out.print("Enter new shift: ");
            nurseToEdit.setShift(scanner.nextLine());
        } else if (choice.equals("7")) {
            System.out.print("Enter new Assigned Patient (Comma separated):");
            String assignedPatient = scanner.nextLine();
            nurseToEdit.setAssignedPatients(assignedPatient.isEmpty() ? new ArrayList<>() : Arrays.asList(assignedPatient.split(",")));

        } else if (choice.equals("8")) {
            System.out.print("Enter new Department Id: ");
            nurseToEdit.setDepartmentId();

        } else if (choice.equals("0")) {
            System.out.println("Edit cancelled.");
            return nurseToEdit;
        } else {
            System.out.println("Invalid choice.");
            return nurseToEdit;
        }

        System.out.println("Nurse updated successfully.");
        return nurseToEdit;
    }


    public void getNursesByDepartment() {
        System.out.print("Enter department ID to search: ");
        String departmentId = scanner.nextLine();
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
        System.out.print("Enter shift to search (e.g., Morning, Evening, Night): ");
        String shift = scanner.nextLine();
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
        System.out.print("Enter nurse first and last name: ");
        String name = scanner.nextLine();
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
        System.out.print("Enter Nurse ID: ");
        String nurseId = scanner.nextLine().trim();

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
        System.out.print("Enter Nurse ID: ");
        String nurseId = scanner.nextLine().trim();

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

        System.out.print("Enter Patient ID to assign: ");
        String patientId = scanner.nextLine().trim();

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
        System.out.print("Enter the nurse ID to remove: ");
        String nurseIdToRemove = scanner.nextLine();
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
}





