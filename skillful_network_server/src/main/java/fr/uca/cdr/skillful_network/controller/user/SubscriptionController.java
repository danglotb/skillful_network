package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.user.Subscription;
import fr.uca.cdr.skillful_network.services.user.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscriptions")
@Transactional
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "")
	public ResponseEntity<Subscription> create(@RequestParam(name="qualificationName") String qualificationName) {
		return new ResponseEntity<>(this.subscriptionService.createOrUpdate(qualificationName), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "")
	public ResponseEntity<List<Subscription>> getAll() {
		return new ResponseEntity<>(this.subscriptionService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Subscription>> getCandidates(@RequestParam(required=false , name="keyword") String keyword) {
		return new ResponseEntity<>(this.subscriptionService.getCandidates(keyword), HttpStatus.OK);
	}


}
