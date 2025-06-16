

import Reliable.ReliableQuery;

public class ReliableQueryI implements ReliableQuery {
    @Override
    public String getVotingTableId(String voterId, com.zeroc.Ice.Current current) {

        return ReliableServer.getVotingTableByVoterId(voterId);
    }
}

