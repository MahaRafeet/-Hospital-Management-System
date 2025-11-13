package Services;

import Entities.*;
import Utils.InputHandler;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsService {

    private static final Scanner scanner = new Scanner(System.in);

    // ===================== 1. Daily Appointments Report =====================
    public static void generateDailyAppointmentsReport() {
        System.out.println("\n===== Daily Appointments Report =====");
        LocalDate date = InputHandler.getDateInput("Enter date (YYYY-MM-DD): ");

        List<Appointment> dailyAppointments = AppointmentService.appointmentList.stream()
                .filter(a -> a.getAppointmentDate().equals(date))
                .collect(Collectors.toList());

        if (dailyAppointments.isEmpty()) {
            System.out.println("No appointments found for " + date);
            return;
        }

        System.out.println("\nAppointments for " + date + ":");
        for (Appointment appt : dailyAppointments) {
            System.out.println("Appointment ID: " + appt.getAppointmentId() +
                    " | Patient: " + appt.getPatientId() +
                    " | Doctor: " + appt.getDoctorId() +
                    " | Time: " + appt.getAppointmentTime());
        }
    }

    // ===================== 2. Doctor Performance Report =====================
    public static void generateDoctorPerformanceReport() {
        System.out.println("\n===== Doctor Performance Report =====");

        if (DoctorService.doctorList.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }

        for (Doctor doctor : DoctorService.doctorList) {
            long appointmentCount = AppointmentService.appointmentList.stream()
                    .filter(a -> a.getDoctorId().equalsIgnoreCase(doctor.getDoctorId()))
                    .count();

            System.out.println("Doctor ID: " + doctor.getDoctorId() +
                    " | Name: " + doctor.getFirstName() + " " + doctor.getLastName() +
                    " | Appointments: " + appointmentCount);
        }
    }

    // ===================== 3. Department Occupancy Report =====================
    public static void generateDepartmentOccupancyReport() {
        System.out.println("\n===== Department Occupancy Report =====");

        if (DepartmentService.departmentList.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        for (Department dept : DepartmentService.departmentList) {
            double occupancyRate = (dept.getBedCapacity() == 0)
                    ? 0
                    : ((double) (dept.getBedCapacity() - dept.getAvailableBeds()) / dept.getBedCapacity()) * 100;

            System.out.printf("Department: %-20s | Capacity: %d | Available: %d | Occupancy: %.2f%%\n",
                    dept.getDepartmentName(),
                    dept.getBedCapacity(),
                    dept.getAvailableBeds(),
                    occupancyRate);
        }
    }

    // ===================== 4. Patient Statistics =====================
    public static void generatePatientStatistics() {
        System.out.println("\n===== Patient Statistics =====");

        int totalPatients = PatientService.patientList.size();
        long insuredPatients = PatientService.patientList.stream()
                .filter(p -> p.getInsuranceId() != null && !p.getInsuranceId().isBlank())
                .count();

        System.out.println("Total Registered Patients: " + totalPatients);
        System.out.println("Patients with Insurance: " + insuredPatients);
        System.out.println("Patients without Insurance: " + (totalPatients - insuredPatients));
    }

    // ===================== 5. Emergency Cases Report =====================
    public static void generateEmergencyCasesReport() {
        System.out.println("\n===== Emergency Cases Report =====");

        // 1. Create a list for emergency patients
        List<EmergencyPatient> emergencyList = new ArrayList<>();

        // 2. Loop through patientList and find only EmergencyPatient
        for (Patient p : PatientService.patientList) {
            if (p instanceof EmergencyPatient) {
                emergencyList.add((EmergencyPatient) p);
            }
        }

        // 3. Check if list is empty
        if (emergencyList.isEmpty()) {
            System.out.println("No emergency cases recorded.");
            return;
        }

        // 4. Display each emergency patient
        int count = 1;
        for (EmergencyPatient ep : emergencyList) {
            System.out.println("\n---- Emergency Case #" + count + " ----");
            System.out.println("Patient Name      : " + ep.getFirstName() + " " + ep.getLastName());
            System.out.println("Patient ID        : " + ep.getPatientId());
            System.out.println("Emergency Type    : " + ep.getEmergencyType());
            System.out.println("Arrival Mode      : " + ep.getArrivalMode());
            System.out.println("Triage Level      : " + ep.getTriageLevel());
            System.out.println("Admitted via ER   : " + (ep.isAdmittedViaER() ? "Yes" : "No"));
            System.out.println("Admitting Doctor  : " + ep.getAdmittingDoctorId());
            System.out.println("Room / Bed        : " + ep.getRoomNumber() + " / " + ep.getBedNumber());
            System.out.println("Admission Date    : " + ep.getAdmissionDate());
            System.out.println("Discharge Date    : " + ep.getDischargeDate());
            count++;
        }

        // 5. Summary section
        int accidentCount = 0;
        int heartAttackCount = 0;

        for (EmergencyPatient ep : emergencyList) {
            if (ep.getEmergencyType().equalsIgnoreCase("Accident")) {
                accidentCount++;
            }
            if (ep.getEmergencyType().equalsIgnoreCase("Heart Attack")) {
                heartAttackCount++;
            }
        }

        System.out.println("\n===== Emergency Summary =====");
        System.out.println("Total Emergency Cases : " + emergencyList.size());
        System.out.println("Accident Cases        : " + accidentCount);
        System.out.println("Heart Attacks         : " + heartAttackCount);
    }

}
