package wd44.plan;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/** File handling for weddings.txt: every read and write of this file happens here. One record per line, fields split by '|'. */
public class PlanFileHandler {
    private final Path file = Paths.get(System.getProperty("wd44.data", "data"), "weddings.txt");

    public PlanFileHandler() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            if (!Files.exists(file)) Files.createFile(file);
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized List<WeddingPlan> readAll() {                           // READ
        List<WeddingPlan> out = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) if (!line.isBlank()) out.add(WeddingPlan.fromLine(line));
        } catch (IOException e) { throw new UncheckedIOException(e); }
        return out;
    }

    public WeddingPlan findById(String id) {
        for (WeddingPlan x : readAll()) if (x.getId().equals(id)) return x;
        return null;
    }

    public List<WeddingPlan> search(String q) {                                 // READ with search
        String k = "";
        if (q != null) k = q.toLowerCase();
        List<WeddingPlan> result = new ArrayList<>();
        for (WeddingPlan x : readAll()) {
            if (x.toLine().toLowerCase().contains(k)) result.add(x);
        }
        return result;
    }

    public synchronized void add(WeddingPlan x) {                               // CREATE
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            w.write(x.toLine()); w.newLine();
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized void update(WeddingPlan x) {                            // UPDATE
        List<WeddingPlan> all = readAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(x.getId())) all.set(i, x);
        }
        saveAll(all);
    }

    public synchronized void delete(String id) {                          // DELETE
        List<WeddingPlan> all = readAll();
        List<WeddingPlan> kept = new ArrayList<>();
        for (WeddingPlan o : all) {
            if (!o.getId().equals(id)) kept.add(o);
        }
        saveAll(kept);
    }

    public String newId() { return "w" + (System.currentTimeMillis() % 100000); }

    private void saveAll(List<WeddingPlan> all) {
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            for (WeddingPlan x : all) { w.write(x.toLine()); w.newLine(); }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
}
