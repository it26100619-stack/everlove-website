package wd44.user;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/** File handling for users.txt: every read and write of this file happens here. One record per line, fields split by '|'. */
public class UserFileHandler {
    private final Path file = Paths.get(System.getProperty("wd44.data", "data"), "users.txt");

    public UserFileHandler() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            if (!Files.exists(file)) Files.createFile(file);
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized List<Couple> readAll() {                           // READ
        List<Couple> out = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) if (!line.isBlank()) out.add(Couple.fromLine(line));
        } catch (IOException e) { throw new UncheckedIOException(e); }
        return out;
    }

    public Couple findById(String id) {
        for (Couple x : readAll()) if (x.getId().equals(id)) return x;
        return null;
    }

    public List<Couple> search(String q) {                                 // READ with search
        String k = "";
        if (q != null) k = q.toLowerCase();
        List<Couple> result = new ArrayList<>();
        for (Couple x : readAll()) {
            if (x.toLine().toLowerCase().contains(k)) result.add(x);
        }
        return result;
    }

    public synchronized void add(Couple x) {                               // CREATE
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            w.write(x.toLine()); w.newLine();
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized void update(Couple x) {                            // UPDATE
        List<Couple> all = readAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(x.getId())) all.set(i, x);
        }
        saveAll(all);
    }

    public synchronized void delete(String id) {                          // DELETE
        List<Couple> all = readAll();
        List<Couple> kept = new ArrayList<>();
        for (Couple o : all) {
            if (!o.getId().equals(id)) kept.add(o);
        }
        saveAll(kept);
    }

    public String newId() { return "c" + (System.currentTimeMillis() % 100000); }

    private void saveAll(List<Couple> all) {
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            for (Couple x : all) { w.write(x.toLine()); w.newLine(); }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
}
