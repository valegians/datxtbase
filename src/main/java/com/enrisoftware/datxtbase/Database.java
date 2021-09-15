package com.enrisoftware.datxtbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String ROW_IDENTIFIER = "{i}";

    private boolean autoCommit;
    private int pendingCommits;

    private List<String> inMemoryDatabase;
    private IOController controller;

    public Database(String path, String databaseName) throws IOException {
        init(path, databaseName, false);
    }

    public Database(String path, String databaseName, boolean autoCommit) throws IOException {
        init(path, databaseName, autoCommit);
    }

    private void init(String path, String databaseName, boolean autoCommit) throws IOException {
        inMemoryDatabase = new ArrayList<>();
        controller = new IOController(this, path, databaseName);
        pendingCommits = 0;
        this.autoCommit = autoCommit;
    }

    public List<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Database location: " + controller.getFilePath());
        info.add("Autocommit: " + autoCommit);
        info.add("Waiting for commit: " + pendingCommits);
        return info;
    }

    public void commit() throws IOException {
        controller.write(inMemoryDatabase);
        inMemoryDatabase = controller.read();
        pendingCommits = 0;
    }

    public void insert(String entry) throws IOException {
        String currentRow = ROW_IDENTIFIER.charAt(0) + Integer.toString(inMemoryDatabase.size()) + ROW_IDENTIFIER.charAt(2);
        inMemoryDatabase.add(currentRow + entry);
        if(autoCommit) {
            commit();
        } else {
            pendingCommits += 1;
        }
    }

    public String lookup(int row) {
        return inMemoryDatabase.get(row);
    }

    public List<String> lookup(int rowStart, int rowEnd) {
        return inMemoryDatabase.subList(rowStart, rowEnd);
    }

    public List<String> lookup(String searchSequence) {
        List<String> matchingEntries = new ArrayList<>();
        for (String entry : inMemoryDatabase) {
            if(entry.contains(searchSequence)) {
                matchingEntries.add(entry);
            }
        }
        return matchingEntries;
    }

    public void erase(int row) throws IOException {
        inMemoryDatabase.remove(row);
        if(autoCommit) {
            commit();
        } else {
            pendingCommits += 1;
        }
    }

    protected void populateInMemoryDatabaseFromFile(List<String> fileDatabase) throws IOException {
        inMemoryDatabase = fileDatabase;
    }
}
