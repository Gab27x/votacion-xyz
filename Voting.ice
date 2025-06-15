module Demo{

    interface VotingSite{
        void send(int candidateId);
    }

    interface VotingTable{
        int vote(string document, int candidateId);
    }



}
