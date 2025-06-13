module Demo{

    interface VotingSite{
        void send(int candidateId);
    }

    interface VotingTable{
        void vote(string document, int candidateId);
    }



}
