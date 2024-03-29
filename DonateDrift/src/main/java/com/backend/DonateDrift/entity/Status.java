package com.backend.DonateDrift.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String description;

	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "status",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private List<StatusAttachment> statusAttachment = new ArrayList<>();



	@ManyToOne
	@JoinColumn(name = "fundraiser_id")
	@JsonIgnore
	private Fundraiser fundraiser;
}
//	public Status(long id, String Description, LocalDateTime UpdatedAt,String photoUrl, Fundraiser fundraiser) {
//		super();
//		this.id = id;
//		this.Description = Description;
//		this.UpdatedAt = UpdatedAt;
//		this.photoUrl = photoUrl;
//		this.fundraiser = fundraiser;
//	}
//
//	public Status() {
//
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getDescription() {
//		return Description;
//	}
//
//	public LocalDateTime getUpdatedAt() {
//		return UpdatedAt;
//	}
//
//	public void setUpdatedAt(LocalDateTime updatedAt) {
//		UpdatedAt = updatedAt;
//	}
//
//	public void setDescription(String description) {
//		this.Description = description;
//	}
//
//	public String getPhotoUrl() {
//		return photoUrl;
//	}
//
//	public void setPhotoUrl(String photoUrl) {
//		this.photoUrl = photoUrl;
//	}
//
//	public Fundraiser getFundraiser() {
//		return fundraiser;
//	}
//
//	public void setFundraiser(Fundraiser fundraiser) {
//		this.fundraiser = fundraiser;
//	}
//
//
//
//
//

