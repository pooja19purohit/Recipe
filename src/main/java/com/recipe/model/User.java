/*
*
*@Author: Keertana H S
*@version : 1.0
*Date: 04-01-2016
*/
package com.recipe.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable{

	private String id;
        private String uniqueId;
        private String email;
        private String password;
	private String mobile;
	private String firstName;
	private String lastName;
	private String addr1;
	private String addr2;
	private String city;
	private String state;
	private String country;
        
        private static final long serialVersionUID = 1L;


   
    public User() {
        
    }

    public User(String id, String firstName, String lastName, String addr1, String addr2, String city, String state, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.city = city;
        this.state = state;
        this.country = country;
        
    }

    public User(String email, String password) {
         this.email = email;
        this.password = password;
    }

    public User(String email, String password, String mobile, String firstName, String lastName, String addr1, String addr2, String city, String state, String country) {
         this.email = email;
        this.password = password;
        this.mobile = mobile;
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.city = city;
        this.state = state;
        this.country = country;
    }

  

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

}
