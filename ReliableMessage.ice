module messageReliable {

    ["java:serializable:model.Vote"]
    sequence<byte> VoteMessage;

    interface ACKService {
        void ack(string voteID);
    }

    interface RMDestination {
        void reciveMessage(VoteMessage vote, ACKService* prx);
    }

    interface RMSource {
        void setServerProxy(RMDestination* destination);
        void sendMessage(VoteMessage vote);
    }
}
