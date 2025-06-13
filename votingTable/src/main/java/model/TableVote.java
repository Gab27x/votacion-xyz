package model;

public class TableVote {
	
	private String id;
	private int candidateId;

	public TableVote(String id, int candidateId) {
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
