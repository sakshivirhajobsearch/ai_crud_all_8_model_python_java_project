package com.alphafold.model;

public class ProteinSequence {
	
	private String id;
	private String sequence;

	public ProteinSequence() {
	}

	public ProteinSequence(String id, String sequence) {
		this.id = id;
		this.sequence = sequence;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}