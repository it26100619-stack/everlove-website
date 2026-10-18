package wd44.booking;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/** File handling for bookings.txt: every read and write of this file happens here. One record per line, fields split by '|'. */
public class BookingFileHandler {
    private final Path file = Paths.get(System.getProperty("wd44.data", "data"), "bookings.txt");

    public BookingFileHandler() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            if (!Files.exists(file)) Files.createFile(file);
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized List<Booking> readAll() {                           // READ
        List<Booking> out = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) if (!line.isBlank()) out.add(Booking.fromLine(line));
        } catch (IOException e) { throw new UncheckedIOException(e); }
        return out;
    }

    public Booking findById(String id) {
        for (Booking x : readAll()) if (x.getId().equals(id)) return x;
        return null;
    }

    public List<Booking> search(String q) {                                 // READ with search
        String k = "";
        if (q != null) k = q.toLowerCase();
        List<Booking> result = new ArrayList<>();
        for (Booking x : readAll()) {
            if (x.toLine().toLowerCase().contains(k)) result.add(x);
        }
        return result;
    }

    public synchronized void add(Booking x) {                               // CREATE
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            w.write(x.toLine()); w.newLine();
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized void update(Booking x) {                            // UPDATE
        List<Booking> all = readAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(x.getId())) all.set(i, x);
        }
        saveAll(all);
    }

    public synchronized void delete(String id) {                          // DELETE
        List<Booking> all = readAll();
        List<Booking> kept = new ArrayList<>();
        for (Booking o : all) {
            if (!o.getId().equals(id)) kept.add(o);
        }
        saveAll(kept);
    }

    public String newId() { return "b" + (System.currentTimeMillis() % 100000); }

    private void saveAll(List<Booking> all) {
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            for (Booking x : all) { w.write(x.toLine()); w.newLine(); }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
}
