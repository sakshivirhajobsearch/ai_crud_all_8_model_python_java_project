package com.alphafold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alphafold.model.ProteinSequence;
import com.alphafold.service.ProteinService;

@RestController
@RequestMapping("/api/protein")
public class ProteinController {

	@Autowired
	private ProteinService proteinService;

	@PostMapping("/predict")
	public String predictStructure(@RequestBody ProteinSequence protein) {
		return proteinService.getPredictedStructure(protein);
	}
}