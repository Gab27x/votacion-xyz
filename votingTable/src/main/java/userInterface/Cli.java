package userInterface;

import java.util.Scanner;

import controller.VotationController;
import model.Vote;

public class Cli {

	private final Scanner scanner;
    private VotationController controller;

    public Cli(VotationController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void startVotingProcess() {
        while (true) {
            
            System.out.println("Welcome to Cli voting system");
            System.out.println("Enter [id , candidate id] ignore []"); // Mensaje con la instrucción de ignorar corchetes

            showCandidates();
            
            System.out.print("Your vote: ");
            String voteInput = scanner.nextLine();

            processAndSendVote(voteInput);

        }
        //scanner.close(); 
    }
    
    public void showCandidates() {
        // Asume que controller.getCandidates() devuelve un array de Strings como {"Gabriel", "Juan", "Rony"}
        String[] candidates = controller.getCandidates();
        System.out.println("\n--- Candidates Available ---");
        for (int i = 0; i < candidates.length; i++) {
            System.out.println((i + 1) + ". " + candidates[i]);
        }
        System.out.println("-----------------------------\n");
    }

    public void processAndSendVote(String voteString) {
        // No se usan .replace("[", "") ni .replace("]", "") porque la instrucción es "ignore []"
        String[] parts = voteString.trim().split(",");

        if (parts.length == 2) {
            String voterId = parts[0].trim();
            int candidateId = -1; // Valor por defecto

            try {
                candidateId = Integer.parseInt(parts[1].trim());
                Vote newVote = new Vote(voterId, candidateId);
                
                controller.sendVote(newVote); // Delega la lógica de negocio al controlador

            } catch (NumberFormatException e) {
                System.err.println("Error: Candidate ID must be a number.");
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        } else {
            System.err.println("Invalid vote format. Use: VoterID,CandidateID (e.g., UUID123,1)");
        }
	}



}
