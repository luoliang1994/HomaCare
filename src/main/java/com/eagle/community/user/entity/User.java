package com.eagle.community.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.eagle.community.health_profile.entity.HealthProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "HBC_USERS")
public class User implements Serializable{

	@Id
	// @GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id", unique = true, nullable = false, length = 20)
	@NotNull
	private String id;

	@Column(name = "real_name", length = 20)
	@NotNull(message = "姓名不能为空")
	private String realName;

	@Column(name = "password", length = 20)
	@NotNull(message = "密码不能设置为空")
	private String password;

	@Column(name = "sex", length = 2)
	@NotNull(message = "性别不能为空")
	private String sex;

	@Column(name = "ethnicity", length = 20)
	@NotNull(message = "名族不能为空")
	private String ethnicity;// 名族

	@Column(name = "bir")
	@NotNull(message = "出生日期不能为空")
	private Date bitrhday;

	@Column(name = "native_place")
	@NotNull(message = "籍贯不能为空")
	private String nativePlace;// 籍贯

	@Column(name = "birth_place")
	private String birthPlace;// 出生地

	@Column(name = "marital_status")
	@NotNull(message = "婚姻状况需要填写")
	private String maritalStatus;// 婚姻状况

	@Column(name = "occupation")
	private String cooupation;// 职业

	@Column(name = "educated_level")
	private String educatedLevel;// 受教育水平

	@Column(name = "address")
	@NotNull(message = "住址需要填写")
	private String address;

	@Column(name = "phone_num", length = 14)
	private String phoneNum;

	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JsonProperty
	private Set<Child> children;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JsonIgnore
	private HealthProfile healthProfile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBitrhday() {
		return bitrhday;
	}

	public void setBitrhday(Date bitrhday) {
		this.bitrhday = bitrhday;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCooupation() {
		return cooupation;
	}

	public void setCooupation(String cooupation) {
		this.cooupation = cooupation;
	}

	public String getEducatedLevel() {
		return educatedLevel;
	}

	public void setEducatedLevel(String educatedLevel) {
		this.educatedLevel = educatedLevel;
	}

	public Set<Child> getChildren() {
		return children;
	}

	public void setChildren(Set<Child> children) {
		this.children = children;
	}

	
	
	public HealthProfile getHealthProfile() {
		return healthProfile;
	}

	public void setHealthProfile(HealthProfile healthProfile) {
		this.healthProfile = healthProfile;
	}
	
	

	@Override
	public String toString() {
		return "User [id=" + id + ", realName=" + realName + ", password="
				+ password + ", sex=" + sex + ", ethnicity=" + ethnicity
				+ ", bitrhday=" + bitrhday + ", nativePlace=" + nativePlace
				+ ", birthPlace=" + birthPlace + ", maritalStatus="
				+ maritalStatus + ", cooupation=" + cooupation
				+ ", educatedLevel=" + educatedLevel + ", address=" + address
				+ ", phoneNum=" + phoneNum + ", children=" + children
				+ ", healthProfile=" + healthProfile + "]";
	}

	public User() {
		super();
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
