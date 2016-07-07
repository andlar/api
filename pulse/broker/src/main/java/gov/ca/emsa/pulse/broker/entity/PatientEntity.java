package gov.ca.emsa.pulse.broker.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="patient")
public class PatientEntity {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id", nullable = false )
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "dob")
	private Date dateOfBirth;
	
	@Column(name = "ssn")
	private String ssn;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "address_id")
	private Long addressId;
	
	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "address_id", unique=true, nullable = true, insertable=false, updatable= false)
	private AddressEntity address;
	
	@Column(name = "alternate_care_facility_id")
	private Long acfId;
	
	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "alternate_care_facility_id", unique=true, nullable = true, insertable=false, updatable= false)
	private AlternateCareFacilityEntity acf;
	
	@Column( name = "creation_date", insertable = false, updatable = false)
	private Date creationDate;
	
	@Column( name = "last_modified_date", insertable = false, updatable = false)
	private Date lastModifiedDate;
	
	@Column(name = "last_read_date")
	private Date lastReadDate;
	
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "patientId"  )
	@Fetch(FetchMode.JOIN)
	@Column( name = "patient_id", nullable = false  )
	private Set<PatientOrganizationMapEntity> orgMaps = new HashSet<PatientOrganizationMapEntity>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public Long getAcfId() {
		return acfId;
	}

	public void setAcfId(Long acfId) {
		this.acfId = acfId;
	}

	public AlternateCareFacilityEntity getAcf() {
		return acf;
	}

	public void setAcf(AlternateCareFacilityEntity acf) {
		this.acf = acf;
	}

	public Date getLastReadDate() {
		return lastReadDate;
	}

	public void setLastReadDate(Date lastReadDate) {
		this.lastReadDate = lastReadDate;
	}

	public Set<PatientOrganizationMapEntity> getOrgMaps() {
		return orgMaps;
	}

	public void setOrgMaps(Set<PatientOrganizationMapEntity> orgMaps) {
		this.orgMaps = orgMaps;
	}
}