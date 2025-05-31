import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

public class VotingSite {

	public static void main(String[] args) {
		
		try (Communicator communicator = Util.initialize(args, "VotingSite.cfg")) {

			System.out.println("Voting Site is starting...");

			



			
			communicator.waitForShutdown();

		}
		
	}


}

