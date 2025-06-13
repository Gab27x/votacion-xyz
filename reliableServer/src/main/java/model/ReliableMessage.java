package model;

import java.io.Serializable;

public class ReliableMessage implements Serializable{
    
    private String uuid;
    private long numberMessage;
    private String state;

    private Vote vote;

    public ReliableMessage(String uuid, long numberMessage, String state,  Vote vote) {
        this.uuid = uuid;
        this.numberMessage = numberMessage;
        this.state = state;
        this.vote = vote;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getNumberMessage() {
        return numberMessage;
    }

    public void setNumberMessage(long numberMessage) {
        this.numberMessage = numberMessage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
    
    
    
    
}
