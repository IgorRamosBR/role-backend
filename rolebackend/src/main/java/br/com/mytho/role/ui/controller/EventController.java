package br.com.mytho.role.ui.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mytho.role.domain.model.Event;
import br.com.mytho.role.domain.model.dao.EventDAO;

@RestController
@RequestMapping("/event")
public class EventController {
	private EventDAO events;
	
	@Autowired
	public EventController(EventDAO events) {
		this.events = events;
	}

	@Transactional
	@RequestMapping(method=RequestMethod.POST, 
			    consumes=MediaType.APPLICATION_JSON_VALUE, 
			    produces=MediaType.APPLICATION_JSON_VALUE)
	public Event create(@RequestBody @Valid Event event) {
		events.create(event);
		return event; 
	}
	
	@RequestMapping(method=RequestMethod.GET, 
		    produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Event> events() { 
		return events.list();
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.GET, 
		    produces=MediaType.APPLICATION_JSON_VALUE)
	public Event findBy(@PathVariable Long id){
		 return events.findBy(id);
	}
}
