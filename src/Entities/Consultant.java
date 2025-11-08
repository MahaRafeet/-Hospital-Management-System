package Entities;

import Interfaces.Displayable;
import Utils.InputHandler;
import Utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Consultant extends Doctor implements Displayable {
    public static Scanner scanner = new Scanner(System.in);
    private List<String> consultationTypes;
    private boolean onlineConsultationAvailable;
    private Integer consultationDuration;

    public Consultant(String doctorId, String specialization, String qualification, int experienceYears,
                      String departmentId, double consultationFee, List<String> availableSlots, List<String> assignedPatients,
                      List<String> consultationTypes, boolean onlineConsultationAvailable, Integer consultationDuration) {
        super(doctorId, specialization, qualification, experienceYears, departmentId, consultationFee, availableSlots, assignedPatients);
        this.consultationTypes = consultationTypes;
        this.onlineConsultationAvailable = onlineConsultationAvailable;
        this.consultationDuration = consultationDuration;
    }

    public Consultant() {
        super();
    }


    public List<String> getConsultationTypes() {

        return consultationTypes;
    }

    public void setConsultationTypes(List<String> consultationTypes) {
        if (ValidationUtils.isNull(consultationTypes) || consultationTypes.isEmpty()) {
            System.out.println("Consultation types cannot be null or empty");
            return;
        }
        this.consultationTypes = consultationTypes;
    }

    public boolean isOnlineConsultationAvailable() {
        return onlineConsultationAvailable;
    }

    public void setOnlineConsultationAvailable(boolean onlineConsultationAvailable) {
        this.onlineConsultationAvailable = onlineConsultationAvailable;
    }

    public Integer getConsultationDuration() {
        return consultationDuration;
    }

    public void setConsultationDuration(Integer consultationDuration) {
        if (ValidationUtils.isNull(consultationDuration) || !ValidationUtils.isPositive(consultationDuration)) {
            System.out.println("Consultation duration must be a positive integer");
            return;
        }
        this.consultationDuration = consultationDuration;
    }

    @Override
    public void displayInfo() {
        System.out.println("Consultation types :" + consultationTypes);
        System.out.println("Is there online consultation Available :" + isOnlineConsultationAvailable());
        System.out.println("Consultation Duration : " + consultationDuration);

    }

    public void scheduleConsultation() {
        if (ValidationUtils.isNull(consultationTypes) || consultationTypes.isEmpty()) {
            System.out.println("Cannot schedule consultation: Consultation types are not set.");
            return;
        }
        System.out.println("Scheduling consultation...");
        System.out.println("Consultation types: " + consultationTypes);
        System.out.println("Online consultation available: " + onlineConsultationAvailable);
        System.out.println("Consultation duration: " + consultationDuration + " minutes");
        System.out.println("Consultation scheduled successfully!");
    }

    public void provideSecondOpinion() {

        System.out.println("Providing a second opinion...");
    }

    @Override
    public void displaySummery() {
        System.out.println("Consultant ID: " + getDoctorId());
        System.out.println("Specialization: " + getSpecialization());
        System.out.println("Qualification: " + getQualification());
        System.out.println("Experience Years: " + getExperienceYears());
    }
}
