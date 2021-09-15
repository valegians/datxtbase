package com.enrisoftware.datxtbase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

class IOController {

    private static final String HEADER = "{Version: 0.2}\n";
    private static final String FILE_EXTENSION = ".dtxtb";

    private final File databaseFile;

    protected IOController(Database database, String path, String databaseName) throws IOException {
        databaseFile = new File(path, databaseName + FILE_EXTENSION);
        if(databaseFile.createNewFile()) {
            insertHeader();
        } else {
            database.populateInMemoryDatabaseFromFile(read());
        }
    }

    protected void write(List<String> inMemoryDatabase) throws IOException {
        Files.writeString(databaseFile.toPath(), HEADER);
        Files.write(databaseFile.toPath(), inMemoryDatabase, StandardOpenOption.APPEND);
    }

    protected List<String> read() throws IOException {
        List<String> databaseList = Files.readAllLines(databaseFile.toPath());
        databaseList.remove(0);
        return databaseList;
    }

    protected String getFilePath() {
        return databaseFile.getPath();
    }

    private void insertHeader() throws IOException {
        Files.writeString(databaseFile.toPath(), HEADER);
    }
}
