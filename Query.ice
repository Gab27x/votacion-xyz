module Query {

    interface QueryDeviceI {
        string query(string id);
    }
    interface QueryProxyI {
        string getVotingTableById(string id);
    }
    interface QueryServerI {
        string getVotingTableById(string id);
    }

}
