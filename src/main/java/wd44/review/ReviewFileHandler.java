package wd44.review;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/** File handling for reviews.txt: every read and write of this file happens here. One record per line, fields split by '|'. */
public class ReviewFileHandler {
    private final Path file = Paths.get(System.getProperty("wd44.data", "data"), "reviews.txt");

    public ReviewFileHandler() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            if (!Files.exists(file)) Files.createFile(file);
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized List<Review> readAll() {                           // READ
        List<Review> out = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) if (!line.isBlank()) out.add(Review.fromLine(line));
        } catch (IOException e) { throw new UncheckedIOException(e); }
        return out;
    }

    public Review findById(String id) {
        for (Review x : readAll()) if (x.getId().equals(id)) return x;
        return null;
    }

    public List<Review> search(String q) {                                 // READ with search
        String k = "";
        if (q != null) k = q.toLowerCase();
        List<Review> result = new ArrayList<>();
        for (Review x : readAll()) {
            if (x.toLine().toLowerCase().contains(k)) result.add(x);
        }
        return result;
    }

    public synchronized void add(Review x) {                               // CREATE
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            w.write(x.toLine()); w.newLine();
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized void update(Review x) {                            // UPDATE
        List<Review> all = readAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(x.getId())) all.set(i, x);
        }
        saveAll(all);
    }

    public synchronized void delete(String id) {                          // DELETE
        List<Review> all = readAll();
        List<Review> kept = new ArrayList<>();
        for (Review o : all) {
            if (!o.getId().equals(id)) kept.add(o);
        }
        saveAll(kept);
    }

    public String newId() { return "r" + (System.currentTimeMillis() % 100000); }

    private void saveAll(List<Review> all) {
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            for (Review x : all) { w.write(x.toLine()); w.newLine(); }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
}
