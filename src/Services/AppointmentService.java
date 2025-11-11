package Services;

import java.time.LocalDate;
import java.util.Scanner;

import Entities.Appointment;
import Entities.Doctor;
import Entities.Patient;
import Interfaces.Appointable;
import Interfaces.Manageable;
import Interfaces.Searchable;
import Utils.InputHandler;
import Utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentService implements Manageable, Searchable, Appointable {

    public static Scanner scanner = new Scanner(System.in);
    public static List<Appointment> appointmentList = new ArrayList<>();

    public static Appointment addAppointment() {
        Appointment appointment = new Appointment();
        System.out.println("\n===== Add New Appointment =====");
        appointment.setPatientId();
        appointment.setDoctorId();
        appointment.setAppointmentDate();
        appointment.setAppointmentTime();
        String reason = InputHandler.getStringInput("Enter Reason for Visit: ");
        appointment.setReason(reason);
        String status = InputHandler.getStringInput("Enter Status (Scheduled, Completed, Canceled): ");
        appointment.setStatus(status);
        String notes = InputHandler.getStringInput("Enter any Notes: ");
        appointment.setNotes(notes);
        System.out.println("Appointment added successfully with ID: " + appointment.getAppointmentId());
        return appointment;

    }

    public static Appointment editAppointment(Appointment appointment) {
        String appointmentIdToEdit = InputHandler.getStringInput("Enter the appointment ID to edit:");
        if (!checkIfIdExist(appointmentIdToEdit)) {
            System.out.println("The appointment id is not exist ");
        } else {
            System.out.println("Editing Appointment ID : " + appointment.getAppointmentId());
            System.out.println("Enter what do you want to update? ");
            System.out.println("1-Appointment Date");
            System.out.println("2-Appointment Time");
            System.out.println("3-Reason for Visit");
            System.out.println("4-Status");
            System.out.println("5-Notes");
            System.out.println("6-Exit");
            int choice = InputHandler.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> {
                    System.out.println("The old appointment date is : " + appointment.getAppointmentDate());
                    appointment.setAppointmentDate();
                    System.out.println("The appointment date updated successfully");
                }
                case 2 -> {
                    System.out.println("The old appointment time was : " + appointment.getAppointmentTime());
                    appointment.setAppointmentTime();
                    System.out.println("The appointment time updated successfully");
                }
                case 3 -> {
                    System.out.println("The old Reason of this appointment was : " + appointment.getReason());
                    String newReason = InputHandler.getStringInput("Enter the new reason ");
                    appointment.setReason(newReason);
                }
                case 4 -> {
                    System.out.println("The old status of this appointment was : " + appointment.getStatus());
                    String newStatus = InputHandler.getStringInput("Enter the new status :");
                    appointment.setStatus(newStatus);
                    System.out.println("The status updated Successfully");
                }
                case 5 -> {
                    System.out.println("The old notes was " + appointment.getNotes());
                    String newNotes = InputHandler.getStringInput("Enter the new notes : ");
                    appointment.setNotes(newNotes);
                    System.out.println("The Notes are updated Successfully");
                }
                case 6 -> System.out.println("Exiting edit menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        return appointment;
    }

    // ✅ Version 1: used when you want to ask the user for the ID (menu use)
    public static List<Appointment> getAppointmentsByPatientIdInteractive() {
        String patientId = InputHandler.getStringInput("Please enter Patient ID to view appointments: ");
        return getAppointmentsByPatientId(patientId);
    }

    // ✅ Version 2: used internally (like inside Patient.displayInfo)
    public static List<Appointment> getAppointmentsByPatientId(String patientId) {
        List<Appointment> appointments = new ArrayList<>();

        for (Appointment app : appointmentList) {
            if (app.getPatientId().equalsIgnoreCase(patientId)) {
                appointments.add(app);
            }
        }

        return appointments;
    }

    public static void getAppointmentsByDoctor() {
        List<Appointment> doctorAppointments = new ArrayList<>();
        String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        boolean isFound = false;

        System.out.println("===== Appointments for Doctor ID: " + doctorId + " =====");
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId)) {
                appointment.displayInfo();
                System.out.println("-------------------");
                doctorAppointments.add(appointment);
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("There are no appointments for this doctor.");
        }

    }

    public static void getAppointmentsByDate() {
        String inputDate = InputHandler.getStringInput("Enter Appointment Date (yyyy-mm-dd): ");
        LocalDate appointmentDate = LocalDate.parse(inputDate);
        List<Appointment> appointmentsOnDate = new ArrayList<>();
        boolean isFound = false;

        System.out.println("===== Appointments on: " + appointmentDate + " =====");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate().equals(appointmentDate)) {
                appointment.displayInfo();
                System.out.println("-------------------");
                appointmentsOnDate.add(appointment);
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println(" There are no appointments on this date.");
        }

    }

    public static void rescheduleAppointment() {
        String appointmentId = InputHandler.getStringInput("Enter appointment ID to reschedule:").trim();

        Appointment foundAppointment = null;

        // 🔍 Search for the appointment
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                foundAppointment = appointment;
                break;
            }
        }

        // If not found
        if (foundAppointment == null) {
            System.out.println("Appointment ID not found in the system.");
            return;
        }

        // ✅ If found — reschedule
        System.out.println("\n--- Rescheduling Appointment ID: " + foundAppointment.getAppointmentId() + " ---");

        System.out.print("Enter the new Date (yyyy-MM-dd): ");
        foundAppointment.setAppointmentDate(); // this uses your Appointment’s own setter (with validation & retry loop)

        foundAppointment.setAppointmentTime();

        System.out.println("✅ Appointment rescheduled successfully!");
    }

    public static void viewUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        boolean isFound = false;

        System.out.println("===== Upcoming Appointments =====");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate().isAfter(today) || appointment.getAppointmentDate().isEqual(today)) {
                appointment.displayInfo();
                System.out.println("-------------------");
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("⚠️ There are no upcoming appointments.");
        }
    }


    public void createAppointment(String patientId, String doctorId, LocalDate date) {
        Appointment appointment = new Appointment();
        appointment.setPatientId();
        appointment.setDoctorId();
        appointment.setAppointmentDate();
        appointment.setStatus("Scheduled");
        appointmentList.add(appointment);
        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
    }

    public void createAppointment(String patientId, String doctorId, LocalDate date, String time) {
        Appointment appointment = new Appointment();
        appointment.setPatientId();
        appointment.setDoctorId();
        appointment.setAppointmentDate();
        appointment.setAppointmentTime();
        appointment.setStatus("Scheduled");
        appointmentList.add(appointment);
        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
    }

    public void createAppointment(Appointment appointment) {
        appointment.setStatus("Scheduled");
        appointmentList.add(appointment);
        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
    }

    @Override
    public void scheduleAppointment() {
        Appointment newAppointment = addAppointment();
        // Now add it to the list
        appointmentList.add(newAppointment);

        // Confirm scheduling
        System.out.println("Appointment scheduled successfully with ID: " + newAppointment.getAppointmentId());

    }


    @Override
    public void cancelAppointment() {
        String appointmentId = InputHandler.getStringInput("Enter appointment ID to cancel: ").trim();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setStatus("Canceled");
                System.out.println("Appointment with ID " + appointmentId + " has been canceled.");
                return;
            }
        }
        System.out.println("Appointment ID not found.");

    }

    public void rescheduleAppointment(String appointmentId, LocalDate newDate) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setAppointmentDate();
                System.out.println("Appointment rescheduled successfully to " + newDate);
                return;
            }
        }
        System.out.println("Appointment ID not found.");
    }

    public void rescheduleAppointment(String appointmentId, LocalDate newDate, String newTime) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setAppointmentDate();
                appointment.setAppointmentTime();
                System.out.println("Appointment rescheduled successfully to " + newDate + " at " + newTime);
                return;
            }
        }
        System.out.println("Appointment ID not found.");
    }

    public static void completeAppointment() {
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID to mark as completed: ").trim();

        // Check for null or empty input
        if (!ValidationUtils.isValidString(appointmentId)) {
            System.out.println("Appointment ID cannot be null or empty.");
            return;
        }

        boolean found = false;

        // Search for appointment
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                //  Update status
                appointment.setStatus("Completed");
                System.out.println("Appointment " + appointmentId + " marked as Completed successfully!");
                found = true;
                break;
            }
        }

        //  Not found case
        if (!found) {
            System.out.println("Appointment ID not found in the system.");
        }
    }

    public void rescheduleAppointment(Appointment appointment, LocalDate newDate, String newTime, String reason) {
        appointment.setAppointmentDate();
        appointment.setAppointmentTime();
        appointment.setReason(reason);
        System.out.println("Appointment rescheduled successfully to " + newDate + " at " + newTime);
    }

    public void displayAppointments(LocalDate date) {
        System.out.println("Appointments on " + date + ":");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate().equals(date)) {
                appointment.displayInfo();
            }
        }
    }

    public void displayAppointments(String doctorId, LocalDate startDate, LocalDate endDate) {
        System.out.println("Appointments for Doctor ID " + doctorId + " from " + startDate + " to " + endDate + ":");
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equals(doctorId) &&
                    (appointment.getAppointmentDate().isEqual(startDate) || appointment.getAppointmentDate().isAfter(startDate)) &&
                    (appointment.getAppointmentDate().isEqual(endDate) || appointment.getAppointmentDate().isBefore(endDate))) {
                appointment.displayInfo();
            }
        }

    }

    public static boolean checkIfIdExist(String appointmentId) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return true;
            }
        }
        return false;
    }

    //Interface method implementations
    @Override
    public void add(Object entity) {
        if (entity instanceof Appointment) {
            appointmentList.add((Appointment) entity);
            System.out.println("Appointment added successfully.");
        } else {
            System.out.println("Invalid entity type. Expected an Appointment.");
        }
    }

    @Override
    public void remove() {

        System.out.print("Enter appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();

        Appointment toRemove = null;

        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                toRemove = appointment; // mark for removal
                break;
            }
        }

        if (toRemove != null) {
            appointmentList.remove(toRemove);
            System.out.println(" Appointment with ID " + appointmentId + " has been cancelled.");
        } else {
            System.out.println("Appointment ID not found.");
        }


    }

    @Override
    public void getAll() {
        System.out.println("\n===== All Appointments =====");

        if (appointmentList.isEmpty()) {
            System.out.println("There are no appointments in the system.");
        } else {
            for (Appointment appointment : appointmentList) {
                System.out.println("------------------");
                appointment.displayInfo();
            }
            System.out.println("------------------");
        }


    }

    @Override
    public void search(String keyword) {
        System.out.println("\n===== Search Appointments =====");
        boolean found = false;
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().equalsIgnoreCase(keyword) ||
                    appointment.getDoctorId().equalsIgnoreCase(keyword) ||
                    appointment.getStatus().equalsIgnoreCase(keyword)) {
                appointment.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found matching the keyword.");
        }


    }

    @Override
    public void searchById(String id) {
        System.out.println("\n===== Search Appointment by ID =====");
        boolean found = false;
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equalsIgnoreCase(id)) {
                appointment.displayInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Appointment ID not found.");
        }

    }

    public static void addSampleAppointments() {
        String[] reasons = {
                "General Checkup", "Follow-up Visit", "Lab Results", "Flu Symptoms",
                "Injury Treatment", "Routine Exam", "Medication Review",
                "Post-surgery Check", "Vaccination", "Consultation"
        };

        String[] statuses = {"Scheduled", "Completed", "Canceled"};

        for (int i = 0; i < 15; i++) {
            Appointment appointment = new Appointment();

            // 🔹 Pick patient and doctor safely
            String patientId;
            String doctorId;

            if (!PatientService.patientList.isEmpty()) {
                Patient patient = PatientService.patientList.get(i % PatientService.patientList.size());
                patientId = patient.getPatientId();
            } else {
                patientId = "PAT-" + String.format("%04d", i + 1);
            }

            if (!DoctorService.doctorList.isEmpty()) {
                Doctor doctor = DoctorService.doctorList.get(i % DoctorService.doctorList.size());
                doctorId = doctor.getDoctorId();
            } else {
                doctorId = "DOC-" + String.format("%04d", i + 1);
            }

            // 🔹 Assign IDs and other details
            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDate(LocalDate.now().minusDays(5).plusDays(i));
            appointment.setAppointmentTime(String.format("%02d:00", 9 + (i % 8)));
            appointment.setReason(reasons[i % reasons.length]);
            appointment.setNewStatus(statuses[i % statuses.length]);
            appointment.setNotes("Auto-generated sample appointment for patient " + patientId);

            appointmentList.add(appointment);
        }
    }


}
