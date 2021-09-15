package com.enrisoftware.datxtbase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DatabaseTest {

    private static Database databaseNoAutocommit;
    private static Database databaseAutocommit;

    @BeforeAll
    static void setup() throws IOException {
        databaseNoAutocommit = new Database("./", "databaseNoAutocommit");
        databaseAutocommit = new Database("./", "databaseAutocommit", true);
    }

    @Test
    void test_getInfo_noAutocommit_noPendingCommits() {
        // Arrange
        List<String> expected = new ArrayList<>();
        expected.add("Database location: .\\databaseNoAutocommit.dtxtb");
        expected.add("Autocommit: false");
        expected.add("Waiting for commit: 0");

        // Act
        List<String> actual = databaseNoAutocommit.getInfo();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_getInfo_noAutocommit_2PendingCommits() throws IOException {
        // Arrange
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Database location: .\\databaseNoAutocommit.dtxtb");
        expected.add("Autocommit: false");
        expected.add("Waiting for commit: 2");

        // Act
        databaseNoAutocommit.insert("entry");
        databaseNoAutocommit.insert("entry");

        // Assert
        Assertions.assertEquals(expected, databaseNoAutocommit.getInfo());
    }

    @Test
    void test_getInfo_autocommit_2PendingCommits() throws IOException {
        // Arrange
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Database location: .\\databaseAutocommit.dtxtb");
        expected.add("Autocommit: true");
        expected.add("Waiting for commit: 0");

        // Act
        databaseAutocommit.insert("entry");
        databaseAutocommit.insert("entry");

        // Assert
        Assertions.assertEquals(expected, databaseAutocommit.getInfo());
    }

    @Test
    void commit() {
    }

    @Test
    void insert() {
    }

    @Test
    void lookup() {
    }

    @Test
    void testLookup() {
    }

    @Test
    void testLookup1() {
    }

    @Test
    void erase() {
    }
}