package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getUserName(), DatabaseConfig.getPassword());
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection, scanner);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Add Doctor");
                System.out.println("4. View Doctors");
                System.out.println("5. Book Appointment");
                System.out.println("6. View Appointments");
                System.out.println("7. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.addDoctor();
                        System.out.println();
                        break;
                    case 4:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 5:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 6:
                        viewAppointments(connection);
                        System.out.println();
                        break;
                    case 7:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                        return;
                    default:
                        System.out.println("Please enter a valid choice");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter patient id: ");
        int patientId = scanner.nextInt();

        System.out.println("Enter doctor id: ");
        int doctorId = scanner.nextInt();

        System.out.println("Enter appointment date (YYYY/MM/DD): ");
        String appointmentDate = scanner.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery)) {
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to book Appointment");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date");
            }
        } else {
            System.out.println("Either doctor or patient doesn't exist");
        }
    }

    public static void viewAppointments(Connection connection) {
        String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, d.specialization, a.appointment_date " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "JOIN doctors d ON a.doctor_id = d.id " +
                "ORDER BY a.appointment_date ASC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Appointments: ");
            System.out.println("+----+----------------+----------------+----------------+----------------+");
            System.out.println("| ID | Patient Name   | Doctor Name    | Specialization | Date           |");
            System.out.println("+----+----------------+----------------+----------------+----------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patientName = resultSet.getString("patient_name");
                String doctorName = resultSet.getString("doctor_name");
                String specialization = resultSet.getString("specialization");
                String appointmentDate = resultSet.getString("appointment_date");

                System.out.printf("| %-2d | %-14s | %-14s | %-14s | %-14s |\n",
                        id, patientName, doctorName, specialization, appointmentDate);
            }

            System.out.println("+----+----------------+----------------+----------------+----------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
