package model;

public class Vote {
	
	private String id;
	private int candidateId;

	public Vote(String id, int candidateId) {
		this.id = id;
		this.candidateId = candidateId;

	}

	public String getId() {
		return this.id;

	}

	public int getCandidateId() {
		return this.candidateId;
	}
}
