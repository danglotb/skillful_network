package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.user.Perk.Subscription;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscriptions")
@Transactional
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
	@PostMapping(value = "")
	public Subscription createSubscription(@Valid @RequestBody Subscription subscription) {
		return this.subscriptionService.saveOrUpdateSubscription(subscription);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping("")
	public ResponseEntity<List<Subscription>> getAllSubscriptions() {
		List<Subscription> listSubscription = this.subscriptionService.getAllSubscription();
		return new ResponseEntity<>(listSubscription, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping("/{id}")
	public Subscription findById(@PathVariable("id") long id) {
		return this.subscriptionService.getSubscriptionById(id);
	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
	@GetMapping(value = "/{id}/users")
	public ResponseEntity<Set<User>> getAllUserBySubscriptionId(@PathVariable(value = "id") Long id) throws Throwable {
		Set<User> listUser = this.subscriptionService.getSubscriptionById(id).getUserList();
		return new ResponseEntity<>(listUser, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/{name}")
	public ResponseEntity<Subscription> getSubscriptionByName(@PathVariable(value = "name") String name) {
		Subscription subscriptionFromDb = this.subscriptionService.getSubscriptionByName(name);
		return new ResponseEntity<>(subscriptionFromDb, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Subscription>> getSubscriptionCandidate(@RequestParam(required=false , name="contain") String match) {
		final List<Subscription> subscriptionsByMatch = subscriptionService.getSubscriptionsByMatch(match);
		return new ResponseEntity<>(subscriptionsByMatch, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
	@DeleteMapping("/{id}")
	public void deleteSubscription(@PathVariable(value = "id") Long id) {
		this.subscriptionService.deleteSubscription(id);
	}


}
