package fr.uca.cdr.skillful_network.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;


public class LoginForm{

	@NotNull
	@Size(min=8,max=20)
	public final String password;
	
	@NotNull
	@Email
	public final String email;

	public LoginForm(@Size(min = 8, max = 20) String password, @Email String email) {
		this.password = password;
		this.email = email;
	}
}
