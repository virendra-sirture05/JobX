package com.project.referral.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class JobSeeker extends BaseClass {
		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name="job_seeker_id",nullable= false)
		@MapsId
		User user;
		@Column(name="degree", nullable=false)
		String degree;
		public JobSeeker(User user, String degree) {
			super();
			this.user = user;
			this.degree = degree;
		}
}
