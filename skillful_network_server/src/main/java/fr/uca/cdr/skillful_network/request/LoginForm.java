package fr.uca.cdr.skillful_network.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;


public class LoginForm{

	@NotNull
	@Size(min=8,max=20)
	private String password;
	
	@NotNull
	@Email
	private String email;

	public LoginForm() {
	}

	public LoginForm(@Size(min = 8, max = 20) String password, @Email String email) {
		this.password = password;
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
}
