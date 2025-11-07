package Services;

import Entities.Department;
import Entities.Doctor;
import Entities.Nurse;
import Interfaces.Manageable;
import Interfaces.Searchable;
import Utils.InputHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Services.DoctorService.doctorList;
import static Services.NurseService.nurseList;

public class DepartmentService implements Manageable, Searchable {
    public Scanner scanner = new Scanner(System.in);
    public static List<Department> departmentList = new ArrayList<>();

    static {
        departmentList.add(new Department("DEP-001", "Cardiology"));
        departmentList.add(new Department("DEP-002", "Neurology"));
        departmentList.add(new Department("DEP-003", "Orthopedics"));
        departmentList.add(new Department("DEP-004", "Pediatrics"));
        departmentList.add(new Department("DEP-005", "Obstetrics and Gynecology"));
        departmentList.add(new Department("DEP-006", "Emergency"));
        departmentList.add(new Department("DEP-007", "Radiology"));
        departmentList.add(new Department("DEP-008", "General Surgery"));
    }

    public Department addDepartment() {
        System.out.println("\n===== Add New Department =====");
        // Auto-generate next ID
        String newId = String.format("DEP-%03d", departmentList.size() + 1);
        System.out.println("Generated Department ID: " + newId);
        String name = InputHandler.getStringInput("Enter Department Name: ");
        Department newDept = new Department(newId, name);
        newDept.setDepartmentId(newId);
        newDept.setDepartmentName(name);
        newDept.setHeadDoctorId();
        System.out.print("Enter Bed Capacity: ");
        newDept.setBedCapacity(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Available Beds: ");
        newDept.setAvailableBeds(Integer.parseInt(scanner.nextLine()));

        // Initialize empty doctor/nurse lists
        newDept.setDoctors(new ArrayList<>());
        newDept.setNurses(new ArrayList<>());
        System.out.println("The new Department Information Collected successfully!");
        departmentList.add(newDept);
        return newDept;
    }

    public Department editDepartment() {
        System.out.println("----------Editing Department------------- ");
        System.out.print("Enter the Department ID to edit : ");
        String departmentIdToEdit = scanner.nextLine().trim();
        Department departmentToEdit = null;
        for (Department department1 : departmentList) {
            if (department1.getDepartmentId().equalsIgnoreCase(departmentIdToEdit)) {
                departmentToEdit = department1;
                break;
            }
        }
        if (departmentToEdit == null) {
            System.out.println("Department not found ");
            return null;
        }

        System.out.println("Enter what do you want to edit : ");
        System.out.println("1-Department Name ");
        System.out.println("2-Department head DoctorId  ");
        System.out.println("3-Department doctors ");
        System.out.println("4-Department Nurses  ");
        System.out.println("5-Department bed Capacity  ");
        System.out.println("6-Department available Beds  ");
        System.out.println("7-Exit ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        switch (choice) {
            case 1 -> {
                System.out.println("Current Name: " + departmentToEdit.getDepartmentName());
                departmentToEdit.setDepartmentName(InputHandler.getStringInput("Enter new Department Name: "));
            }
            case 2 -> {
                System.out.println("Current Head Doctor ID: " + departmentToEdit.getHeadDoctorId());
                departmentToEdit.setHeadDoctorId();
            }
            case 3 -> {
                System.out.println("Current Doctors: " + departmentToEdit.getDoctors());
                System.out.print("Enter new list of Doctors (comma separated): ");
                String doctorsList = scanner.nextLine().trim();
                departmentToEdit.setDoctors(
                        doctorsList.isEmpty() ? new ArrayList<>() : Arrays.asList(doctorsList.split("\\s*,\\s*"))
                );
            }
            case 4 -> {
                System.out.println("Current Nurses: " + departmentToEdit.getNurses());
                System.out.print("Enter new list of Nurses (comma separated): ");
                String nursesList = scanner.nextLine().trim();
                departmentToEdit.setNurses(
                        nursesList.isEmpty() ? new ArrayList<>() : Arrays.asList(nursesList.split("\\s*,\\s*"))
                );
            }
            case 5 -> {
                System.out.println("Current Bed Capacity: " + departmentToEdit.getBedCapacity());
                System.out.print("Enter new Bed Capacity: ");
                departmentToEdit.setBedCapacity(Integer.parseInt(scanner.nextLine()));
            }
            case 6 -> {
                System.out.println("Current Available Beds: " + departmentToEdit.getAvailableBeds());
                System.out.print("Enter new Available Beds: ");
                departmentToEdit.setAvailableBeds(Integer.parseInt(scanner.nextLine()));
            }
            case 7 -> System.out.println("Exiting edit...");
            default -> System.out.println("Invalid choice!");
        }

        System.out.println("✅ Department information updated successfully!");
        return departmentToEdit;

    }

    public void assignDoctorToDepartment() {
        System.out.println("\n*** Assign Doctor to Department ***");

        // Get Doctor ID
        System.out.print("Enter the Doctor ID: ");
        String docId = scanner.nextLine().trim();

        // Find the doctor
        Doctor doctorToAssign = null;
        for (Doctor doctor : doctorList) {
            if (doctor.getDoctorId().equalsIgnoreCase(docId)) {
                doctorToAssign = doctor;
                break;
            }
        }

        if (doctorToAssign == null) {
            System.out.println(" Doctor with this ID was not found.");
            return;
        }

        // Get Department ID
        System.out.print("Enter the Department ID: ");
        String depID = scanner.nextLine().trim();

        // Find the department
        Department departmentToAssign = null;
        for (Department dep : departmentList) {
            if (dep.getDepartmentId().equalsIgnoreCase(depID)) {
                departmentToAssign = dep;
                break;
            }
        }

        if (departmentToAssign == null) {
            System.out.println("Department with this ID was not found.");
            return;
        }

        // Assign doctor if not already in the list
        if (!departmentToAssign.getDoctors().contains(docId)) {
            departmentToAssign.getDoctors().add(docId);
            System.out.println("Doctor " + docId + " assigned to Department " + depID + " successfully!");
        } else {
            System.out.println("Doctor is already assigned to this department.");
        }
    }

    public void assignNurseToDepartment() {
        System.out.println("\n*** Assign Nurse to Department ***");

        // Get Nurse ID
        System.out.print("Enter the Nurse ID: ");
        String nurseId = scanner.nextLine().trim();

        // Find the nurse
        Nurse nurseToAssign = null;
        for (Nurse nurse : nurseList) { // Assuming you have nurseList
            if (nurse.getNurseId().equalsIgnoreCase(nurseId)) {
                nurseToAssign = nurse;
                break;
            }
        }

        if (nurseToAssign == null) {
            System.out.println("Nurse with this ID was not found.");
            return;
        }

        // Get Department ID
        System.out.print("Enter the Department ID: ");
        String depId = scanner.nextLine().trim();

        // Find the department
        Department departmentToAssign = null;
        for (Department dep : departmentList) { // Assuming you have departmentList
            if (dep.getDepartmentId().equalsIgnoreCase(depId)) {
                departmentToAssign = dep;
                break;
            }
        }

        if (departmentToAssign == null) {
            System.out.println("Department with this ID was not found.");
            return;
        }

        // Assign nurse if not already in the department
        if (!departmentToAssign.getNurses().contains(nurseId)) { // Assuming Department has getNurses()
            departmentToAssign.getNurses().add(nurseId);
            System.out.println("Nurse " + nurseId + " assigned to Department " + depId + " successfully!");
        } else {
            System.out.println("Nurse is already assigned to this department.");
        }
    }

    public void viewDepartmentStatistics() {
        System.out.println("\n===== Department Statistics =====");

        if (departmentList.isEmpty()) {
            System.out.println("⚠️ No departments available.");
            return;
        }

        for (Department dep : departmentList) {
            System.out.println("----------------------------");
            System.out.println("Department ID   : " + dep.getDepartmentId());
            System.out.println("Department Name : " + dep.getDepartmentName());

            // Number of doctors in this department
            int doctorCount = dep.getDoctors() != null ? dep.getDoctors().size() : 0;
            System.out.println("Number of Doctors: " + doctorCount);
            // Number of nurses in this department
            int nurseCount = dep.getNurses() != null ? dep.getNurses().size() : 0;
            System.out.println("Number of Nurses: " + nurseCount);
            System.out.println("----------------------------");
        }
    }

    @Override
    public void add(Object entity) {
        if (entity instanceof Department) {
            Department newDepartment = (Department) entity;
            departmentList.add(newDepartment);
            System.out.println(" Department added successfully!");
        } else {
            System.out.println("Invalid entity type. Expected a Department.");
        }

    }

    @Override
    public void remove() {
        System.out.println("please enter the department ID to remove ");
        String depIdToRemove = scanner.nextLine();
        Department departmentToRemove = null;
        for (Department department : departmentList) {
            if (department.getDepartmentId().equals(depIdToRemove)) {
                departmentToRemove = department;
                break;
            }
        }
        if (departmentToRemove != null) {
            departmentList.remove(departmentToRemove);
            System.out.println(" Department removed successfully!");
        } else {
            System.out.println(" Department not found in the list!");
        }

    }

    @Override
    public void getAll() {
        System.out.println("-----Display All Department -----");
        if (departmentList.isEmpty()) {
            System.out.println("There is no departments");
        } else {
            for (Department department : departmentList) {
                System.out.println("---------------");
                department.displayInfo();
            }
            System.out.println("---------------");

        }

    }

    @Override
    public void search(String keyword) {
        System.out.println("----- Search Departments by keyword: " + keyword + " -----");
        boolean found = false;
        for (Department department : departmentList) {
            if (department.getDepartmentName().toLowerCase().contains(keyword.toLowerCase()) ||
                    department.getDepartmentId().toLowerCase().contains(keyword.toLowerCase()) ||
                    department.getHeadDoctorId().toLowerCase().contains(keyword.toLowerCase())) {
                department.displayInfo();
                System.out.println("---------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No departments found matching the keyword.");
        }

    }

    @Override
    public void searchById(String departmentId) {
        boolean isFound = false;
        for (Department department : departmentList) {
            if (department.getDepartmentId().equalsIgnoreCase(departmentId)) {
                System.out.println("Department Information");
                department.displayInfo();
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Department with this Id is not found");
        }

    }
}











