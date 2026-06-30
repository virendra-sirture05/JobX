package com.jobx.server.user.entity;

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
public class Referrer extends BaseClass {
		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name="referrer_id")
		@MapsId
		User user;
		
		@Column(name="degree", nullable=false)
		String companyName;
		public Referrer(User user, String companyName) {
			super();
			this.user = user;
			this.companyName = companyName;
		}
		
}
