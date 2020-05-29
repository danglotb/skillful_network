package fr.uca.cdr.skillful_network.request;

import javax.validation.constraints.Size;

public class PasswordForm {
	
	@Size(min=2, max=20 , message = "Le mot de passe doit comporter au minimum 2 caractères")
	public final String password;

	public PasswordForm(@Size(min = 2, max = 20, message = "Le mot de passe doit comporter au minimum 2 caractères") String password) {
		this.password = password;
	}
}
