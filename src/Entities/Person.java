package Entities;

import Interfaces.Displayable;
import Utils.ValidationUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class Person implements Displayable {
    private final String id;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;
    String gender;
    private String phoneNumber;
    private String email;

    public Person(String firstName, String lastName,
                  String address, LocalDate dateOfBirth, String phoneNumber,
                  String email, String gender) {
        this.id = ValidationUtils.generateId("PER");
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setGender();
    }


    // Default constructor with auto-generated ID
    public Person() {
        this.id = ValidationUtils.generateId("PER");
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    // Setters with validation
    public void setFirstName(String firstName) {
        Scanner scanner = new Scanner(System.in);
        while (!ValidationUtils.isValidString(firstName)) {
            System.out.print("Invalid first name. Please Enter invalid name :");
            firstName = scanner.nextLine();
        }
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        Scanner scanner = new Scanner(System.in);
        while (!ValidationUtils.isValidString(lastName)) {
            System.out.print("Invalid last name. Please Enter invalid name :");
            lastName = scanner.nextLine();
        }
        this.lastName = lastName.trim();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (ValidationUtils.isNull(dateOfBirth)) {
            System.out.println("Date of birth cannot be null.");
            return;
        }
        if (ValidationUtils.isFutureDate(dateOfBirth)) {
            System.out.println("Date of birth cannot be in the future.");
            return;
        }
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender() {
        Scanner scanner = new Scanner(System.in);
        String gender = null;

        while (gender == null ||
                !(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
            System.out.print("Enter gender (Male/Female):");
            gender = scanner.nextLine().trim();
            if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
                System.out.println("Invalid input. Please enter 'Male' or 'Female'.");
            }
        }

        this.gender = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!ValidationUtils.isValidString(phoneNumber)) {
            System.out.println("Invalid phone number.");
            return;
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Scanner scanner = new Scanner(System.in);

        // Keep asking until a valid email is entered
        while (email == null || !ValidationUtils.isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email (e.g., name@example.com): ");
            email = scanner.nextLine();
        }

        this.email = email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!ValidationUtils.isValidString(address)) {
            System.out.println("Invalid address.");
            return;
        }
        this.address = address.trim();
    }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
    }

    @Override
    public void displaySummery() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + firstName + " " + lastName);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}