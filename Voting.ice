module Demo{

    interface VotingSite{
        public void send(int candidateId)
    }

    interface VotingTable{
        public bool vote(string document, int candidateId);
    }



}
