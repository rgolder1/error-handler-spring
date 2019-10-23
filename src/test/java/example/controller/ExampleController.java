package example.controller;

import javax.validation.Valid;

import example.api.SimpleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

	@RequestMapping(path = "/simple", method = RequestMethod.POST)
	public ResponseEntity<Void> simpleUpdate(@Valid @RequestBody SimpleRequest update){
		return ResponseEntity.ok().build();
	}
}
