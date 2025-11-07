package Services;

import Entities.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsService {

    private static final Scanner scanner = new Scanner(System.in);

    // ===================== 1. Daily Appointments Report =====================
    public static void generateDailyAppointmentsReport() {
        System.out.println("\n===== Daily Appointments Report =====");
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

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

        if (AppointmentService.appointmentList.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        List<Appointment> emergencyCases = new ArrayList<>();

        // Go through each appointment
        for (Appointment appointment : AppointmentService.appointmentList) {

            // Find the doctor of this appointment
            Doctor doctorFound = null;
            for (Doctor doctor : DoctorService.doctorList) {
                if (doctor.getDoctorId().equalsIgnoreCase(appointment.getDoctorId())) {
                    doctorFound = doctor;
                    break;
                }
            }

            // If doctor is found and belongs to Emergency department
            if (doctorFound != null && doctorFound.getDepartmentId().equalsIgnoreCase("DEP-006")) {
                emergencyCases.add(appointment);
            }
        }

        // Show results
        if (emergencyCases.isEmpty()) {
            System.out.println("No emergency cases found.");
        } else {
            System.out.println("Emergency Cases:");
            for (Appointment appt : emergencyCases) {
                System.out.println("Appointment ID: " + appt.getAppointmentId()
                        + " | Patient ID: " + appt.getPatientId()
                        + " | Doctor ID: " + appt.getDoctorId()
                        + " | Date: " + appt.getAppointmentDate());
            }
        }
    }
}
