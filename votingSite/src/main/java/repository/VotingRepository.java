package repository;

import model.Vote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VotingRepository {
    private static final String FILE_PATH = "votos.db";

    public void saveVote(Vote vote) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            out.writeObject(vote);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteVote(Vote vote) {
        List<Vote> votes = loadVotes();
        votes.removeIf(v -> v.getVoterId().equals(vote.getVoterId())); // Basado en ID
        overwriteFile(votes);
    }

    public List<Vote> loadVotes() {
        List<Vote> votes = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            while (true) {
                votes.add((Vote) in.readObject());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return votes;
    }

    private void overwriteFile(List<Vote> votes) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            for (Vote v : votes) {
                out.writeObject(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

