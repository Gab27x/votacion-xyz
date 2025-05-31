public class VotingTable {

	public static void main(String[] args) {
		
		try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "VotingTable.cfg")) {

			System.out.println("Voting Table is starting...");

			//sendVotePrx =


			communicator.waitForShutdown();
			
		}
		
	}
}
