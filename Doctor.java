package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addDoctor() {
        System.out.println("Enter Doctor's Name: ");
        scanner.nextLine();
        String name = scanner.nextLine().trim();

        System.out.println("Enter Doctor's Specialization: ");
        String specialization = scanner.nextLine().trim();

        try {
            String query = "INSERT INTO doctors(name, specialization) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor added successfully!");
            } else {
                System.out.println("Failed to add doctor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void viewDoctors(){
        String query = "SELECT * FROM doctors";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------------------+-------+-----------+");
            System.out.println("| Doctor Id |                Name            |    Specialization  |");
            System.out.println("+------------+--------------------------------+-------+-----------+");

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");

                System.out.printf("|%-12s|%-32s|%-20s|\n", id, name, specialization);
                System.out.println("+------------+--------------------------------+-------+-----------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
