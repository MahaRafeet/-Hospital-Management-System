import Entities.*;
import Services.*;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Integer userChoice = 0;
        while (userChoice != 8) {
            System.out.println("*******Hospital Management System MENU*******");
            System.out.println("1- Patient Management");
            System.out.println("2- Doctor Management");
            System.out.println("3- Nurse Management");
            System.out.println("4- Appointment Management");
            System.out.println("5- Medical Records Management");
            System.out.println("6- Department Management");
            System.out.println("7- Reports and Statistics");
            System.out.println("8- Exit the Program");
            System.out.println("**********************************************");
            System.out.print("Please Enter your choice: ");
            userChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (userChoice) {
                case 1 -> patientMenu();
                case 2 -> doctorMenu();
                case 3 -> nurseMenu();
                case 4 -> appointmentMenu();
                case 5 -> medicalRecordMenu();
                case 6 -> departmentMenu();
                case 7 -> reportsMenu();
                case 8 -> System.out.println("Exiting program... Goodbye!");
                default -> System.out.println("Invalid choice! Try again.");
            }
        }

        scanner.close();
    }

    // ================= PATIENT MANAGEMENT =================
    private static void patientMenu() {
        Integer patientChoice = 0;
        while (patientChoice != 10) {
            System.out.println("\n--- Patient Management Menu ---");
            System.out.println("1- Register New Patient");
            System.out.println("2- Register InPatient");
            System.out.println("3- Register OutPatient");
            System.out.println("4- Register Emergency Patient");
            System.out.println("5- View All Patients");
            System.out.println("6- Search Patient");
            System.out.println("7- Update Patient Information");
            System.out.println("8- Remove Patient");
            System.out.println("9- View Patient Medical History");
            System.out.println("10- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            patientChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (patientChoice) {
                case 1 -> {
                    Patient newPatient = new Patient();
                    PatientService.addPatient(newPatient);

                }
                case 2 -> {
                    InPatient inPatient=new InPatient();
                    PatientService.addPatient(inPatient);
                }

                    case 3 ->{
                    OutPatient outPatient=new OutPatient();
                    PatientService.addOutPatient(outPatient);
                }

                case 4 -> {
                    EmergencyPatient emergencyPatient=new EmergencyPatient();
                    PatientService.addEmergencyPatient(emergencyPatient);
                }
                case 5 -> {
                    PatientService service=new PatientService();
                    service.getAll();
                }
                case 6 -> {
                    PatientService service = new PatientService();
                    service.searchPatientMenu();
                }


                case 7 -> {
                    PatientService service=new PatientService();
                    service.editPatient();
                }
                case 8 -> {
                    PatientService service=new PatientService();
                    service.remove();
                }

                case 9 -> {
                    MedicalRecordService medicalRecordService = new MedicalRecordService();
                    MedicalRecordService.getRecordsByPatientIdInteractive();
                }
                case 10 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= DOCTOR MANAGEMENT =================
    private static void doctorMenu() {
        Integer doctorChoice = 0;
        while (doctorChoice != 11) {
            System.out.println("\n--- Doctor Management Menu ---");
            System.out.println("1- Add Doctor");
            System.out.println("2- Add Surgeon");
            System.out.println("3- Add Consultant");
            System.out.println("4- Add General Practitioner");
            System.out.println("5- View All Doctors");
            System.out.println("6- Search Doctor by Specialization");
            System.out.println("7- View Available Doctors");
            System.out.println("8- Assign Patient to Doctor");
            System.out.println("9- Update Doctor Information");
            System.out.println("10- Remove Doctor");
            System.out.println("11- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            doctorChoice = scanner.nextInt();
            scanner.nextLine();

            switch (doctorChoice) {
                case 1 -> {
                    DoctorService service=new DoctorService();
                    Doctor newDoctor = new Doctor();
                    DoctorService.addDoctor(newDoctor);
                    service.save(newDoctor);

                }
                case 2 -> {
                    DoctorService service=new DoctorService();
                    Surgeon newSurgeon = new Surgeon() {
                    };
                    DoctorService.addSurgeon(newSurgeon);
                    service.save(newSurgeon);
                }
                case 3 -> {
                    DoctorService service=new DoctorService();
                    Consultant newConsultant = new Consultant();
                    DoctorService.addConsultant(newConsultant);
                    service.save(newConsultant);
                }
                case 4 -> {
                    DoctorService service=new DoctorService();
                    GeneralPractitioner newGP = new GeneralPractitioner();
                    DoctorService.addGeneralPractitioner(newGP);
                    service.save(newGP);
                }
                case 5 ->{
                    DoctorService service=new DoctorService();
                    service.getAll();
                }
                case 6 -> {
                    DoctorService service = new DoctorService();
                    service.displayDoctors();
                }
                case 7 -> {
                    DoctorService service = new DoctorService();
                    service.getAvailableDoctors();
                }
                case 8 -> {
                    DoctorService service = new DoctorService();
                    service.assignPatient();
                }
                case 9 -> {
                    DoctorService service=new DoctorService();
                    service.editDoctor();
                }
                case 10 -> {
                    DoctorService service=new DoctorService();
                    service.remove();
                }
                case 11 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= NURSE MANAGEMENT =================
    private static void nurseMenu() {
        Integer nurseChoice = 0;
        while (nurseChoice != 8) {
            System.out.println("\n--- Nurse Management Menu ---");
            System.out.println("1- Add Nurse");
            System.out.println("2- View All Nurses");
            System.out.println("3- View Nurses by Department");
            System.out.println("4- View Nurses by Shift");
            System.out.println("5- Assign Nurse to Patient");
            System.out.println("6- Update Nurse Information");
            System.out.println("7- Remove Nurse");
            System.out.println("8- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            nurseChoice = scanner.nextInt();
            scanner.nextLine();

            switch (nurseChoice) {
                case 1 -> {
                    NurseService service = new NurseService();
                    Nurse newNurse = NurseService.addNurse(); // collect input
                    service.save(newNurse);
                }
                case 2 -> {
                    NurseService service=new NurseService();
                    service.getAll();
                }
                case 3 -> {
                    NurseService service = new NurseService();
                    service.getNursesByDepartment();
                }
                case 4 ->{
                    NurseService service = new NurseService();
                    service.getNursesByShift();
                }
                case 5 -> {
                    NurseService service = new NurseService();
                    service.assignNurseToPatient();
                }
                case 6 -> {
                    NurseService service=new NurseService();
                    service.editNurse();
                }
                case 7 -> {
                    NurseService service=new NurseService();
                    service.remove();
                }
                case 8 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= APPOINTMENT MANAGEMENT =================
    private static void appointmentMenu() {
        Integer choice = 0;
        while (choice != 10) {
            System.out.println("\n--- Appointment Management Menu ---");
            System.out.println("1- Schedule New Appointment");
            System.out.println("2- View All Appointments");
            System.out.println("3- View Appointments by Patient");
            System.out.println("4- View Appointments by Doctor");
            System.out.println("5- View Appointments by Date");
            System.out.println("6- Reschedule Appointment");
            System.out.println("7- Cancel Appointment");
            System.out.println("8- Complete Appointment");
            System.out.println("9- View Upcoming Appointments");
            System.out.println("10- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    AppointmentService service=new AppointmentService();
                    service.scheduleAppointment();

                }
                case 2 -> {
                    AppointmentService service=new AppointmentService();
                    service.getAll();
                }
                case 3 -> AppointmentService.getAppointmentsByPatientIdInteractive();
                case 4 -> AppointmentService.getAppointmentsByDoctor();
                case 5 -> AppointmentService.getAppointmentsByDate();
                case 6 -> AppointmentService.rescheduleAppointment();
                case 7 -> {
                    AppointmentService service=new AppointmentService();
                    service.cancelAppointment();
                }
                case 8 -> {
                    AppointmentService service=new AppointmentService();
                    service.completeAppointment();
                }
                case 9 ->  AppointmentService.viewUpcomingAppointments();
                case 10 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= MEDICAL RECORD MANAGEMENT =================
    private static void medicalRecordMenu() {
        Integer choice = 0;
        while (choice != 8) {
            System.out.println("\n--- Medical Records Management Menu ---");
            System.out.println("1- Create Medical Record");
            System.out.println("2- View All Records");
            System.out.println("3- View Records by Patient");
            System.out.println("4- View Records by Doctor");
            System.out.println("5- Update Medical Record");
            System.out.println("6- Delete Medical Record");
            System.out.println("7- Generate Patient History Report");
            System.out.println("8- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    MedicalRecord newRecord = new MedicalRecord();
                    MedicalRecordService service = new MedicalRecordService();
                    service.addMedicalRecord(newRecord);
                    service.saveNewRecord(newRecord);
                }
                case 2 -> {
                    MedicalRecordService service=new MedicalRecordService();
                    service.getAll();
                }
                case 3 -> MedicalRecordService.getRecordsByPatientIdInteractive();
                case 4 -> MedicalRecordService.getRecordsByDoctorId();
                case 5 -> {
                            MedicalRecord medicalRecord=new MedicalRecord();
                            MedicalRecordService service=new MedicalRecordService();
                            service.editMedicalRecord(medicalRecord);
                }
                case 6 -> {
                    MedicalRecordService service=new MedicalRecordService();
                    service.remove();
                }
                case 7 -> {
                    MedicalRecordService service=new MedicalRecordService();
                    service.displayPatientHistory();
                }
                case 8 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= DEPARTMENT MANAGEMENT =================
    private static void departmentMenu() {
        Integer choice = 0;
        while (choice != 8) {
            System.out.println("\n--- Department Management Menu ---");
            System.out.println("1- Add Department");
            System.out.println("2- View All Departments");
            System.out.println("3- View Department Details");
            System.out.println("4- Assign Doctor to Department");
            System.out.println("5- Assign Nurse to Department");
            System.out.println("6- Update Department Information");
            System.out.println("7- View Department Statistics");
            System.out.println("8- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    Department newDepartment = new Department("DEP-001", "Cardiology", 20);
                    DepartmentService service = new DepartmentService();
                    service.addDepartment();
                }
                case 2 ->
                        {
                            DepartmentService service=new DepartmentService();
                            service.getAll();
                        }
                case 3 -> {
                    System.out.print("Enter Department ID to view details:");
                    String deptId = scanner.nextLine();
                    DepartmentService service = new DepartmentService();
                    service.search(deptId);
                }
                case 4 -> {
                    DepartmentService service = new DepartmentService();
                    service.assignDoctorToDepartment();
                }
                case 5 ->  {
                    DepartmentService service = new DepartmentService();
                    service.assignNurseToDepartment();
                }
                case 6 -> {
                    DepartmentService service=new DepartmentService();
                    service.editDepartment();
                }
                case 7 -> {
                    DepartmentService service = new DepartmentService();
                    service.viewDepartmentStatistics();
                }
                case 8 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= REPORTS AND STATISTICS =================
    private static void reportsMenu() {
        Integer choice = 0;
        while (choice != 6) {
            System.out.println("\n--- Reports and Statistics Menu ---");
            System.out.println("1- Daily Appointments Report");
            System.out.println("2- Doctor Performance Report");
            System.out.println("3- Department Occupancy Report");
            System.out.println("4- Patient Statistics");
            System.out.println("5- Emergency Cases Report");
            System.out.println("6- Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    ReportsService service = new ReportsService();
                    service.generateDailyAppointmentsReport();
                }
                case 2 -> {
                    ReportsService service = new ReportsService();
                    service.generateDoctorPerformanceReport();
                }
                case 3 -> {
                    ReportsService service = new ReportsService();
                    service.generateDepartmentOccupancyReport();
                }
                case 4 -> {
                    ReportsService service = new ReportsService();
                    service.generatePatientStatistics();
                }
                case 5 -> {
                    ReportsService service = new ReportsService();
                    service.generateEmergencyCasesReport();
                }
                case 6 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
