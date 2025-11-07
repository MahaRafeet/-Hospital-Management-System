package Entities;

import Interfaces.Displayable;
import Services.DoctorService;
import Services.NurseService;
import Utils.InputHandler;
import Utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Entities.InPatient.scanner;


public class Department implements Displayable {
    private String departmentId;
    private String departmentName;
    private String headDoctorId;
    private List<String> doctors;
    private List<String> nurses;
    private Integer bedCapacity;
    private Integer availableBeds;
    // Default constructor
    public Department(String s, String cardiology, int i) {
        this.departmentId = "";
        this.departmentName = "";
        this.headDoctorId = "";
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
        this.bedCapacity = 0;
        this.availableBeds = 0;
    }

    // Constructor with ID and Name
    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headDoctorId = "";
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
        this.bedCapacity = 0;
        this.availableBeds = 0;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        if(!ValidationUtils.isValidString(departmentId)){
            System.out.println("Department ID cannot be null or empty.");
            return;
        }
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        if(!ValidationUtils.isValidString(departmentName)){
            System.out.println("Department Name cannot be null or empty.");
            return;
        }
        this.departmentName = departmentName;
    }

    public String getHeadDoctorId() {
        return headDoctorId;
    }

    public void setHeadDoctorId() {
        // First, show available doctors
        DoctorService.displayDoctorsList();

        // Ask the user for input *before* entering the loop
        String headDoctorId = InputHandler.getStringInput("Enter Head Doctor ID: ");

        while (true) {
            // Validate input
            if (!ValidationUtils.isValidString(headDoctorId)) {
                System.out.println("Head Doctor ID cannot be null or empty.");
                headDoctorId = InputHandler.getStringInput("Enter Head Doctor ID: ");
                continue;
            }

            String finalHeadDoctorId = headDoctorId;
            boolean exists = DoctorService.doctorList != null &&
                    DoctorService.doctorList.stream()
                            .filter(Objects::nonNull)
                            .map(Doctor::getDoctorId)
                            .filter(Objects::nonNull)
                            .anyMatch(id -> id.equalsIgnoreCase(finalHeadDoctorId));

            if (!exists) {
                System.out.println(" No doctor found with ID: " + headDoctorId);
                DoctorService.displayDoctorsList(); // show all doctors again
                headDoctorId = InputHandler.getStringInput("Enter a valid Head Doctor ID: ");
                continue;
            }

            // If valid → assign and break
            this.headDoctorId = headDoctorId;
            System.out.println("✅ Head doctor assigned successfully: " + headDoctorId);
            break;
        }
    }


    public List<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<String> doctors) {
        if(ValidationUtils.isNull(doctors)){
            System.out.println("Doctors list cannot be null or empty");
            return;
        }
        this.doctors = doctors;
    }

    public List<String> getNurses() {
        return nurses;
    }

    public void setNurses(List<String> nurses) {
        if(ValidationUtils.isNull(nurses)){
            System.out.println("Nurses list cannot be null or empty");
            return;
        }
        // Check that all entered nurse IDs exist
        boolean allExist = nurses.stream().allMatch(id ->
                NurseService.nurseList.stream()
                        .anyMatch(n -> n.getNurseId().equalsIgnoreCase(id))
        );

        if (!allExist) {
            System.out.println("One or more nurse IDs are invalid.");
            return;
        }

        this.nurses = nurses;
        System.out.println("Nurses assigned successfully!");

    }

    public int getBedCapacity() {
        return bedCapacity;
    }

    public void setBedCapacity(int bedCapacity) {
        while (!ValidationUtils.isPositive(bedCapacity)) {
            System.out.println(" Bed Capacity must be a positive integer.");
            try {
                bedCapacity = Integer.parseInt(scanner.nextLine());
                if (bedCapacity <= 0) System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number, please try again.");
            }
        }
        this.bedCapacity = bedCapacity;
        }


    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        while (availableBeds < 0 || availableBeds > bedCapacity) {
            System.out.println("Available Beds must be between 0 and " + bedCapacity + ".");
            availableBeds = InputHandler.getIntInput(
                    "Please enter a valid number of available beds: ");
        }

        this.availableBeds = availableBeds;
        System.out.println("✅ Available beds set to: " + availableBeds);
    }
    @Override
    public void displayInfo(){
        System.out.println("Department ID     : " + departmentId);
        System.out.println("Department Name   : " + departmentName);
        System.out.println("Head Doctor ID    : " + headDoctorId);

        System.out.println("Doctors:");
        if (doctors != null && !doctors.isEmpty()) {
            System.out.println( doctors);
        } else {
            System.out.println("  None");
        }

        System.out.println("Nurses:");
        if (nurses != null && !nurses.isEmpty()) {
            System.out.println(nurses);
        } else {
            System.out.println("None");
        }
        System.out.println("Bed Capacity      : " + bedCapacity);
        System.out.println("Available Beds    : " + availableBeds);
    }
    @Override
    public void displaySummery(){
        System.out.println("Department ID   : " + departmentId);
        System.out.println("Department Name : " + departmentName);
        System.out.println("Head Doctor ID  : " + headDoctorId);
        System.out.println("Bed Capacity    : " + bedCapacity);
        System.out.println("Available Beds  : " + availableBeds);
    }

}
