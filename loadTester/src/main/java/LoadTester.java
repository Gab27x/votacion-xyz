import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import Demo.VotingTablePrx;

public class LoadTester {

	public static void main(String[] args) {
		File logFile = new File("loadtest.log");

		try {
			// Create the log file if it doesn't exist
			if (!logFile.exists()) {
				boolean created = logFile.createNewFile();
				if (!created) {
					throw new IOException("Failed to create the log file.");
				}
			}

			// Open the log file for writing (false = overwrite mode)
			try (Communicator communicator = Util.initialize(args, "LoadTester.cfg");
					BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, false))) {

				VotingTablePrx tablePrx = VotingTablePrx.checkedCast(
						communicator.propertyToProxy("VotingTable.Proxy"));

				if (tablePrx == null) {
					throw new RuntimeException("Failed to obtain proxy to VotingTable.");
				}

				System.out.println("Starting load test...");

				int totalVotes = 1000;

				for (int i = 0; i < totalVotes; i++) {
					String document = "doc-" + i;
					int candidateId = i % 3;

					try {
						tablePrx.vote(document, candidateId);
						String logEntry = "VOTE SENT: " + document + " -> candidate " + candidateId;
						logWriter.write(logEntry);
						logWriter.newLine();
						System.out.println(logEntry);
					} catch (Exception e) {
						String errorEntry = "FAILED TO SEND: " + document + " -> candidate " + candidateId;
						logWriter.write(errorEntry);
						logWriter.newLine();
						System.err.println(errorEntry);
						e.printStackTrace();
					}
				}

				System.out.println("Load test completed. Log saved to 'loadtest.log'.");

			}

		} catch (IOException e) {
			System.err.println("Log file error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
