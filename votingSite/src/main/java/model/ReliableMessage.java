package model;

import java.io.Serializable;

public class ReliableMessage implements Serializable {

    private String uuid;
    private long sequenceNumber;
    private String status;
    private Vote vote;

    public ReliableMessage(String uuid, long sequenceNumber, String status, Vote vote) {
        this.uuid = uuid;
        this.sequenceNumber = sequenceNumber;
        this.status = status;
        this.vote = vote;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "ReliableMessage{" +
                "uuid='" + uuid + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", status='" + status + '\'' +
                ", vote=" + vote +
                '}';
    }
}

