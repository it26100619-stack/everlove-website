package wd44.vendor;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/** File handling for vendors.txt: every read and write of this file happens here. One record per line, fields split by '|'. */
public class VendorFileHandler {
    private final Path file = Paths.get(System.getProperty("wd44.data", "data"), "vendors.txt");

    public VendorFileHandler() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            if (!Files.exists(file)) Files.createFile(file);
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized List<Vendor> readAll() {                           // READ
        List<Vendor> out = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) if (!line.isBlank()) out.add(Vendor.fromLine(line));
        } catch (IOException e) { throw new UncheckedIOException(e); }
        return out;
    }

    public Vendor findById(String id) {
        for (Vendor x : readAll()) if (x.getId().equals(id)) return x;
        return null;
    }

    public List<Vendor> search(String q) {                                 // READ with search
        String k = "";
        if (q != null) k = q.toLowerCase();
        List<Vendor> result = new ArrayList<>();
        for (Vendor x : readAll()) {
            if (x.toLine().toLowerCase().contains(k)) result.add(x);
        }
        return result;
    }

    public synchronized void add(Vendor x) {                               // CREATE
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            w.write(x.toLine()); w.newLine();
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public synchronized void update(Vendor x) {                            // UPDATE
        List<Vendor> all = readAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(x.getId())) all.set(i, x);
        }
        saveAll(all);
    }

    public synchronized void delete(String id) {                          // DELETE
        List<Vendor> all = readAll();
        List<Vendor> kept = new ArrayList<>();
        for (Vendor o : all) {
            if (!o.getId().equals(id)) kept.add(o);
        }
        saveAll(kept);
    }

    public String newId() { return "v" + (System.currentTimeMillis() % 100000); }

    private void saveAll(List<Vendor> all) {
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            for (Vendor x : all) { w.write(x.toLine()); w.newLine(); }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
}
