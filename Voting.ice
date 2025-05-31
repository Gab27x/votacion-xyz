module Demo{

    interface VotingSite{
        void send(int candidateId);
    }

    interface VotingTable{
        bool vote(string document, int candidateId);
    }



}
