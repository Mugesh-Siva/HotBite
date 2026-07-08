package com.hexaware.hotbyte.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password_hash")
    private String passwordHash;
    
    @Column(name = "contact_number")
    private String contactNumber;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "reset_otp")
    private String resetOtp;
    
    @Column(name = "reset_otp_expiry")
    private LocalDateTime resetOtpExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> userAddresses;

    @OneToMany(mappedBy = "ownerUser", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

}
