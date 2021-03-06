package fr.uca.cdr.skillful_network.tools.json;

import java.lang.reflect.Type;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import fr.uca.cdr.skillful_network.entities.user.User;

public class UserAdapter implements JsonDeserializer<User>{

	@Override
	public User deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// Convertie le json en user
		final User user = new GsonBuilder().setPrettyPrinting().create()
		    .fromJson(jsonElement, User.class);
		
		// Encode le password du user
		user.setPassword(encoder.encode(user.getPassword()));
		
		//retourne le user avec le password encodé
		return user;

	}

}
