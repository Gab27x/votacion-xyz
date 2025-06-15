module Query {

    interface queryDevice {
        string query(string id);
    }
    interface queryProxy {
        string getVotingTableById(string id);
    }
    interface queryServer {
        string getVotingTableById(string id);
    }

}
