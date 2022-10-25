package com.example.testjwt.Service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.testjwt.model.TutorialUser;

@FeignClient(name = "tutorials-service")
public interface TutorialService {

	@GetMapping("api/tutorials")
	public List<TutorialUser> getAllTutorials(@RequestParam(name = "name", required=false) String title);

	@GetMapping("api/tutorials/{id}")
	public TutorialUser getTutorialById(@PathVariable("id") long id);

	@PostMapping("api/tutorials")
	public TutorialUser createTutorial(@RequestBody TutorialUser tutorial);
	
	@PutMapping("api/tutorials/{id}")
	public TutorialUser updateTutorial(@PathVariable("id") long id, @RequestBody TutorialUser tutorial);
	
	@DeleteMapping("api/tutorials/{id}")
	public String deleteTutorialById(@PathVariable("id") long id);
	
	@GetMapping("/api/testPort")
	public String testPort();
}
