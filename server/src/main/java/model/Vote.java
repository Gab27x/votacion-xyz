package model;

import java.io.Serializable;

public class Vote implements Serializable {
    private String voterId;
    private String candidate;

    public Vote(String candidate) {
        this.candidate = candidate;
    }

    public String getCandidate() {
        return candidate;
    }

    @Override
    public String toString() {
        return "Voto{ candidate='" + candidate + "'}";
    }
}

