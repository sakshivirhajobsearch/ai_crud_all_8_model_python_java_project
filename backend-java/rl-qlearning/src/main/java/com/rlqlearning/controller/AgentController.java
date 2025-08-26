package com.rlqlearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rlqlearning.model.AgentState;
import com.rlqlearning.service.AgentService;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

	@Autowired
	private AgentService service;

	@PostMapping("/next-action")
	public String nextAction(@RequestBody AgentState state) {
		return service.getNextAction(state.getX(), state.getY());
	}
}