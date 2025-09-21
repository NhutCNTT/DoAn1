package Project1.com.LibraryManagement.Entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "fullname",nullable = false)
    private String fullName;
    @Column(name = "phonenumber",nullable = false)
    private int phoneNumber;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "DateOfBirth",nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    public Users(String address, LocalDate dateOfBirth, String email, String fullName, Long id, String password, int phoneNumber, Roles roles) {
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.fullName = fullName;
        Id = id;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
    public Users(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
