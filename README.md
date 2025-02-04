# 🏥 Hospital Management System  

A **Java-based hospital management system** that allows efficient **patient, doctor, and appointment management** using **MySQL** and **JDBC**.  

---

## 🚀 Features  
🔹 **Add & View Patients** 👨‍⚕️  
🔹 **Add & View Doctors** 🩺  
🔹 **Book Appointments** 📅  
🔹 **Check Doctor Availability** ✅  
🔹 **View Appointments** with Specialization Details 🏷️  
🔹 **Uses MySQL for Data Storage** 🗄️  

---

## 🛠️ Installation & Setup  

### **1️⃣ Clone the Repository**  
```sh
git clone https://github.com/your-username/hospital-management-system.git
cd hospital-management-system
```

### **2️⃣ Configure the Database**  
Create a **MySQL database** and required tables:  

```sql
CREATE DATABASE hospital;
USE hospital;

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);

CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
```

### **3️⃣ Configure `config.properties`**  
Inside `src/HospitalManagementSystem/config.properties`, update:  
```
db.url=jdbc:mysql://localhost:3306/hospital
db.user=root
db.password=your_mysql_password
```

### **4️⃣ Compile & Run the Application**  

#### **On Terminal or Command Prompt:**
```sh
javac src/HospitalManagementSystem/*.java
java HospitalManagementSystem.HospitalManagementSystem
```

#### **On IntelliJ IDEA:**  
1️⃣ Open the project in **IntelliJ IDEA**  
2️⃣ Ensure you have **MySQL JDBC Driver** installed  
3️⃣ **Run `HospitalManagementSystem.java`**  

---

## 📂 Project Structure  

```
hospital-management-system/
│── src/
│   ├── HospitalManagementSystem/
│   │   ├── HospitalManagementSystem.java   # Main application
│   │   ├── Patient.java                    # Patient management
│   │   ├── Doctor.java                     # Doctor management
│   │   ├── config.properties               # Database configuration
│── out/ (Generated Files)
│── .gitignore
│── README.md
│── pom.xml (if using Maven)
```

---

## 📌 Example Output  

```
HOSPITAL MANAGEMENT SYSTEM
1. Add Patient
2. View Patients
3. Add Doctor
4. View Doctors
5. Book Appointment
6. View Appointments
7. Exit
Enter your choice:
```

---

