package Entities;


import Interfaces.Displayable;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.util.List;

public class EmergencyPatient extends InPatient implements Displayable {
    private String emergencyType;
    private String arrivalMode;  // "Ambulance" or "Walk-in"
    private int triageLevel;     // 1 (most critical) to 5 (least critical)
    private boolean admittedViaER;

    public EmergencyPatient(String firstName, String lastName, String address, LocalDate dateOfBirth,
                            String phoneNumber, String email, String gender, String patientId, String bloodGroup,
                            List<String> allergies, String emergencyContact, LocalDate registrationDate,
                            String insuranceId, LocalDate admissionDate, LocalDate dischargeDate, String roomNumber,
                            String bedNumber, String admittingDoctorId, double dailyCharges, String emergencyType,
                            String arrivalMode, int triageLevel, boolean admittedViaER) {

        // ✅ call InPatient constructor (without id)
        super(firstName, lastName, address, dateOfBirth, phoneNumber, email, gender,
                patientId, bloodGroup, allergies, emergencyContact, registrationDate,
                insuranceId, admissionDate, dischargeDate, roomNumber, bedNumber,
                admittingDoctorId, dailyCharges);

        this.emergencyType = emergencyType;
        this.arrivalMode = arrivalMode;
        this.triageLevel = triageLevel;
        this.admittedViaER = admittedViaER;
    }

    public EmergencyPatient() {

    }


    public String getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(String emergencyType) {
        if (!ValidationUtils.isValidString(emergencyType)) {
            System.out.println("Invalid emergency type ");
            return;
        } else {
            this.emergencyType = emergencyType;
        }
    }

    public String getArrivalMode() {
        return arrivalMode;
    }

    public void setArrivalMode(String arrivalMode) {
        if (!ValidationUtils.isValidString(arrivalMode)) {
            System.out.println("Invalid input ");
            return;
        } else {
            this.arrivalMode = arrivalMode;
        }
    }

    public int getTriageLevel() {
        return triageLevel;
    }

    public void setTriageLevel(int triageLevel) {
        if (triageLevel < 1 || triageLevel > 5) {
            System.out.println("triageLevel should be from 1 (most critical) to 5 (least critical)");
            return;
        } else {
            this.triageLevel = triageLevel;
        }
    }

    public boolean isAdmittedViaER() {
        return admittedViaER;
    }

    public void setAdmittedViaER(boolean admittedViaER) {
        this.admittedViaER = admittedViaER;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Emergency type: " + emergencyType);
        System.out.println("Arrival Mode: " + arrivalMode);
        System.out.println("triage Level : " + triageLevel);
        System.out.println("admitted Via ER : " + admittedViaER);
    }

    @Override
    public void displaySummery() {
        System.out.println("Emergency type: " + emergencyType);
        System.out.println("Arrival Mode: " + arrivalMode);
        System.out.println("triage Level : " + triageLevel);
    }

    @Override
    public void calculateStayDuration() {
        if (getAdmissionDate() == null || getDischargeDate() == null) {
            System.out.println("Admission or discharge date not set for emergency patient.");
        } else {
            long days = getDischargeDate().toEpochDay() - getAdmissionDate().toEpochDay(); // your style
            System.out.println("Emergency Patient Stay Duration: " + days + " days");
        }
    }

    public void calculateTotalCharges() {
        if (getAdmissionDate() == null || getDischargeDate() == null) {
            System.out.println("Cannot calculate total charges for emergency patient without both dates.");
        } else {
            long days = getDischargeDate().toEpochDay() - getAdmissionDate().toEpochDay();
            double total = days * getDailyCharges();
            if (admittedViaER) {
                total += 50; // optional emergency surcharge
            }
            System.out.println("Emergency Patient Total Charges: " + total + " OMR");
        }
    }

}
