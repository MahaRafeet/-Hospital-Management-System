package Entities;

import Interfaces.Displayable;

import java.util.Scanner;

public class GeneralPractitioner extends Doctor implements Displayable {
    private boolean walkinAvailable;
    private boolean homeVisitAvailable;
    private boolean vaccinationCertified;

    public static Scanner scanner = new Scanner(System.in);

    // Constructor
    public GeneralPractitioner() {
    }

    // Getters and Setters
    public boolean isWalkinAvailable() {
        return walkinAvailable;
    }

    public void setWalkinAvailable(boolean walkinAvailable) {
        this.walkinAvailable = walkinAvailable;
    }

    public boolean isHomeVisitAvailable() {
        return homeVisitAvailable;
    }

    public void setHomeVisitAvailable(boolean homeVisitAvailable) {
        this.homeVisitAvailable = homeVisitAvailable;
    }

    public boolean isVaccinationCertified() {
        return vaccinationCertified;
    }

    public void setVaccinationCertified(boolean vaccinationCertified) {
        this.vaccinationCertified = vaccinationCertified;
    }


    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Walk-in Available: " + walkinAvailable);
        System.out.println("Home Visit Available: " + homeVisitAvailable);
        System.out.println("Vaccination Certified: " + vaccinationCertified);

    }

    @Override
    public void displaySummery() {
        super.displayInfo();
        System.out.println("Walk-in Available: " + walkinAvailable);
        System.out.println("Home Visit Available: " + homeVisitAvailable);
    }

    // Custom methods
    public void scheduleHomeVisit() {
        if (!homeVisitAvailable) {
            System.out.println("Home visits are not available for this doctor.");
        } else {
            System.out.print("Enter patient name for the home visit: ");
            String patientName = scanner.nextLine();
            System.out.println("Home visit scheduled for patient: " + patientName);
        }
    }

    public void administerVaccine() {
        if (!vaccinationCertified) {
            System.out.println("Doctor is not certified to administer vaccines.");
        } else {
            System.out.print("Enter patient name to administer vaccine: ");
            String patientName = scanner.nextLine();
            System.out.println("Vaccine administered successfully to: " + patientName);
        }
    }

}
