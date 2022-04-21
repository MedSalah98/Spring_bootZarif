package tn.esprit.spring.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String adress;
	private String lastName;
	@Column(nullable=false ,length=12)
	private String phone;
	@Column(nullable=false)
	private String userLogin;
	@Column(nullable=false)
	private String userPasword;
	@Column(nullable=false)
	private String email;
	@Column(nullable=false ,length=8)
	private String cin;
	@Column(nullable=false)
	private double latitude;
	@Column(nullable=false)
	private double longitude;
	@Enumerated(EnumType.STRING)
    private Role role;
	
     @OneToMany( cascade=CascadeType.PERSIST,mappedBy="user")
     @JsonIgnore
     private List<OrderDelivery> orderDeliverys;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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



	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPasword() {
		return userPasword;
	}

	public void setUserPasword(String userPasword) {
		this.userPasword = userPasword;
	}


	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin; 
	}

	public List<OrderDelivery> getOrderDeliverys() {
		return orderDeliverys;
	}

	public void setOrderDeliverys(List<OrderDelivery> orderDeliverys) {
		this.orderDeliverys = orderDeliverys;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

}
