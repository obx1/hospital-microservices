package ma.you.hospital.doctors.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String firstName;
    @Column(nullable=false) private String lastName;
    @Column(nullable=false) private String specialty;
    @Column(nullable=false, unique=true) private String email;
    private String phone;
    private String address;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist void prePersist(){ createdAt = updatedAt = Instant.now(); }
    @PreUpdate  void preUpdate(){  updatedAt = Instant.now(); }

    // getters & setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public String getSpecialty(){ return specialty; }
    public void setSpecialty(String specialty){ this.specialty = specialty; }
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }
    public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }
    public String getAddress(){ return address; }
    public void setAddress(String address){ this.address = address; }
    public Instant getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Instant createdAt){ this.createdAt = createdAt; }
    public Instant getUpdatedAt(){ return updatedAt; }
    public void setUpdatedAt(Instant updatedAt){ this.updatedAt = updatedAt; }
}
