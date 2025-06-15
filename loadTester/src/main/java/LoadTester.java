import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import Demo.VotingTablePrx;

public class LoadTester {

	public static void main(String[] args) {
		File csvFile = new File("loadtest.csv");

		try {
			// Create the CSV file if it doesn't exist
			if (!csvFile.exists()) {
				boolean created = csvFile.createNewFile();
				if (!created) {
					throw new IOException("Failed to create the CSV file.");
				}
			}

			// Open the CSV file for writing (false = overwrite mode)
			try (Communicator communicator = Util.initialize(args, "LoadTester.cfg");
					BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, false))) {

				VotingTablePrx tablePrx = VotingTablePrx.checkedCast(
						communicator.propertyToProxy("VotingTable.Proxy"));

				if (tablePrx == null) {
					throw new RuntimeException("Failed to obtain proxy to VotingTable.");
				}

				System.out.println("Starting load test...");

				int totalVotes = 1000;

				// Write CSV header
				writer.write("candidateId,result");
				writer.newLine();

				for (int i = 0; i < totalVotes; i++) {
					String document = "doc-" + i;
					int candidateId = i % 3;

					try {
						int result = tablePrx.vote(document, candidateId);
						writer.write(candidateId + "," + result);
						writer.newLine();
						System.out.println("VOTE SENT: " + document + " -> candidate " + candidateId);
					} catch (Exception e) {
						writer.write(candidateId + ",ERROR");
						writer.newLine();
						System.err.println("FAILED TO SEND: " + document + " -> candidate " + candidateId);
						e.printStackTrace();
					}
				}

				System.out.println("Load test completed. CSV saved to 'loadtest.csv'.");

			}

		} catch (IOException e) {
			System.err.println("CSV file error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
