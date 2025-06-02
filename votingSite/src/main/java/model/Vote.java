package model;

import java.io.Serializable;

public class Vote implements Serializable {
    private String voterId;
    private String candidate;

    public Vote(String voterId, String candidate) {
        this.voterId = voterId;
        this.candidate = candidate;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getCandidate() {
        return candidate;
    }

    @Override
    public String toString() {
        return "Voto{" + "voterId='" + voterId + "', candidate='" + candidate + "'}";
    }
}

