package controller;

import model.Vote;
import userInterface.Cli;

public class VotationController implements Runnable {

	 private Cli cli; // Referencia a la interfaz de usuario

    public VotationController() {
        this.cli = new Cli(this); // Inicializa Cli, pasándole esta instancia del controlador
    }

    @Override
    public void run() {
        System.out.println("VotationController: Inicializando...");
        cli.startVotingProcess(); // Inicia el proceso de votación a través de la CLI
        System.out.println("VotationController: Finalizado.");
    }

    /**
     * Obtiene los nombres de los candidatos para mostrarlos en la CLI.
     * En esta versión simplificada, se devuelve un array fijo directamente.
     * @return Un array de Strings con los nombres de los candidatos.
     */
    public String[] getCandidates() {
        // Devuelve un array fijo de nombres de candidatos, sin necesidad de objetos Candidate ni Map.
        return new String[]{"Gabriel", "Juan", "Rony"};
    }

    /**
     * Procesa y "registra" un voto.
     * En esta versión simplificada, solo imprime que el voto fue recibido.
     * @param vote El objeto Vote que contiene el ID del votante y el ID del candidato.
     */
    public void sendVote(Vote vote) {
        // Aquí iría tu lógica real para enviar el voto a otro componente o sistema,
        // por ejemplo, usando Ice, o simplemente imprimiéndolo para simular el procesamiento.
        System.out.println("VotationController: Voto recibido: " + vote.getId() + " para candidato ID: " + vote.getCandidateId());
        // No hay validaciones ni almacenamiento interno de votos en esta versión simplificada.
    }





	
}
