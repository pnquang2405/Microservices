package com.example.testjwt.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.testjwt.Service.TutorialService;
import com.example.testjwt.model.TutorialUser;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/auth")
public class TutorialController {
	
	@Autowired
	private Environment environment;
	
	@EventListener({
        ApplicationReadyEvent.class,
        RefreshScopeRefreshedEvent.class
    })
    public void onEvent() {
		Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("New secret token:" + environment.getProperty("token.string_secret"));
    }
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private Environment env;

	@RequestMapping(value = "/tutorials", method = RequestMethod.GET)
	@CircuitBreaker(name = "userService", fallbackMethod = "getAllAvailableTutorials")
	public ResponseEntity<List<TutorialUser>> getAllCustomerReport(@RequestParam(name = "name", required=false) String title) {
		List<TutorialUser> tutorialUserList = tutorialService.getAllTutorials(title);
		return new ResponseEntity<List<TutorialUser>>(tutorialUserList, HttpStatus.OK);
	}

	@GetMapping("/tutorials/{id}")
	@CircuitBreaker(name = "userService", fallbackMethod = "getAllAvailableTutorials")
	public ResponseEntity<TutorialUser> getTutorialById(@PathVariable("id") long id) {
		TutorialUser tutorialUser = tutorialService.getTutorialById(id);
		return new ResponseEntity<TutorialUser>(tutorialUser, HttpStatus.OK);
	}

	@PostMapping("/tutorials")
//	@PreAuthorize("hasAnyAuthority('USER_CREATE')")
	public ResponseEntity<TutorialUser> createTutorial(@RequestBody TutorialUser tutorial) {
		TutorialUser _tutorial = tutorialService.createTutorial(tutorial);

		return new ResponseEntity<TutorialUser>(_tutorial, HttpStatus.CREATED);
	}
	
	@PutMapping("/tutorials/{id}")
//	@PreAuthorize("hasAnyAuthority('USER_UPDATE')")
	public ResponseEntity<TutorialUser> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialUser tutorial) {
		TutorialUser _tutorial = tutorialService.updateTutorial(id, tutorial);
		
		return new ResponseEntity<TutorialUser>(_tutorial, HttpStatus.OK);
	}
	
	@DeleteMapping("/tutorials/{id}")
//	@PreAuthorize("hasAnyAuthority('USER_DELETE')")
	public ResponseEntity<String> deleteTutorialById(@PathVariable("id") long id) {
//		List<TutorialUser> list = tutorialService.deleteTutorialById(id);
		String status = tutorialService.deleteTutorialById(id);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
	@RequestMapping("/")
	public String home() {
		return "Tutorial Service running at port: " + env.getProperty("local.server.port");
	}
	
	@RequestMapping("/testPort")
	public String testPort() {
		return tutorialService.testPort();
	}

	public ResponseEntity<ErrorDetails> getAllAvailableTutorials(Exception e) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "khong the dung thao tac nay!");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
}
