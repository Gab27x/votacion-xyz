package userInterface;

import java.util.Scanner;

import controller.VotationController;
import model.TableVote;

public class Cli {

    private final Scanner scanner;
    private VotationController controller;

    public Cli(VotationController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void startVotingProcess() {
        System.out.println("Welcome to Cli voting system");

        while (true) {
            showCandidates();

            System.out.print("Enter Voter ID (or type 'exit' to quit): ");
            String voterId = scanner.nextLine().trim();
            if (voterId.equalsIgnoreCase("exit")) {
                System.out.println("Exiting voting process.");
                break;
            }
            if (voterId.isEmpty()) {
                System.err.println("Voter ID cannot be empty.");
                continue;
            }

            System.out.print("Enter Candidate ID: ");
            String candidateInput = scanner.nextLine().trim();

            int candidateId;
            try {
                candidateId = Integer.parseInt(candidateInput);
                String[] candidates = controller.getCandidates();
                if (candidateId < 1 || candidateId > candidates.length) {
                    System.err
                            .println("Invalid candidate ID. Please enter a number between 1 and " + candidates.length);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.err.println("Candidate ID must be a number.");
                continue;
            }

            TableVote vote = new TableVote(voterId, candidateId);
            controller.sendVote(vote);
        }
        scanner.close();
    }

    public void showCandidates() {
        String[] candidates = controller.getCandidates();
        System.out.println("\n--- Candidates Available ---");
        for (int i = 0; i < candidates.length; i++) {
            System.out.println((i + 1) + ". " + candidates[i]);
        }
        System.out.println("-----------------------------\n");
    }
}
