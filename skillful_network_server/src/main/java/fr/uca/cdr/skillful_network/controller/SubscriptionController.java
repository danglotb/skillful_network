package fr.uca.cdr.skillful_network.controller;

import fr.uca.cdr.skillful_network.model.entities.Subscription;
import fr.uca.cdr.skillful_network.model.entities.User;
import fr.uca.cdr.skillful_network.model.services.SubscriptionService;
import fr.uca.cdr.skillful_network.tools.AutoCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscriptions")
@Transactional
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

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
	public ResponseEntity<Set<User>> getAllUserBySubscription(@PathVariable(value = "id") Long id) throws Throwable {
		Set<User> listUser = this.subscriptionService.getSubscriptionById(id).getUserList();
		return new ResponseEntity<>(listUser, HttpStatus.OK);

	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/{name}")
	public ResponseEntity<Subscription> getSubscriptionByName(@PathVariable(value = "name") String name) {
		Subscription subscriptionFromDb = this.subscriptionService.getSubscriptionByName(name);
		return new ResponseEntity<>(subscriptionFromDb, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
	@PostMapping(value = "")
	public Subscription save(@Valid @RequestBody Subscription subscription) {
		return this.subscriptionService.saveOrUpdateSubscription(subscription);

	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
	@DeleteMapping("/{id}")
	public void deleteSubscription(@PathVariable(value = "id") Long id) {
		this.subscriptionService.deleteSubscription(id);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Subscription>> getCandidatesByMatch(@RequestParam(required=false , name="contain") String match) {
		final List<Subscription> subscriptionsByMatch = subscriptionService.getSubscriptionsByMatch(match);
		return new ResponseEntity<>(subscriptionsByMatch, HttpStatus.OK);
	}
}
