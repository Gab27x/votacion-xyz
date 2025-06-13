package model;

import java.io.Serializable;

public class Vote implements Serializable {
    
    public String vote;

    public Vote(String vote) {
        this.vote = vote;

    }
    
    public String getVote() {
        return vote;
    }

}
