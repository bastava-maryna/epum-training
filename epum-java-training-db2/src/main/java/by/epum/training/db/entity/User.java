package by.epum.training.db.entity;

import java.util.Objects;

public class User extends Entity<Long> {

	private static final long serialVersionUID = 9115582957198707075L;

	private String lastName;
	private String firstName;
	private String passport;
	private Role role;
	private String login;
	private String password;
	private String email;
	

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, login, passport, password, role);
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
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(login, other.login)
				&& Objects.equals(passport, other.passport) && Objects.equals(password, other.password)
				&& role == other.role;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [lastName=" + lastName + ", firstName=" + firstName + ", passport=" + passport + ", role=" + role
				+ ", login=" + login + ", password=" + password + ", email=" + email + ", idUser=" + getId() + "]";
	}
	
	
	
	
	
	
	
}
