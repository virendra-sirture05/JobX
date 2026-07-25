package com.project.referral.user.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor // default constructor
@Getter // all getters
@Setter // all setters
@ToString(exclude = "hashedPassword")
//JPA annotations
@Entity // to declare Entity class - to tell Hibernate to manage entity life cycle
@Table(name = "users") // customizes table name
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseClass   implements UserDetails {

	@Column(length = 30, unique = true) // col name , varchar(30) , unique constraint
	private String email;
	@Column(length = 300, nullable = false) // NOT NULL constraint
	private String hashedPassword;
	@Column(length = 14, unique = true)
	private String phone;
	@Enumerated(EnumType.STRING) // col type - varchar | enum
	private UserRole role;

	public User(String email, String hashedPassword, UserRole role) {
		this.email = email;
		this.hashedPassword = hashedPassword;
		this.role = role;
	}
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
				new SimpleGrantedAuthority(
						role.name())
		);
	}

	@Override
	public String getPassword() {
		return hashedPassword;
	}
}
