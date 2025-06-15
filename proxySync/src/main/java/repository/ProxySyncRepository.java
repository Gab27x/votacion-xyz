package repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.ReliableMessage;
import model.Vote;

public class ProxySyncRepository {
    private final File file;

    public ProxySyncRepository(String path) {
        this.file = new File(path);
    }

    public synchronized void persistVote(Vote vote) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true))) {
            out.writeObject(vote);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void save(ReliableMessage rmessage) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true))) {
            out.writeObject(rmessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
