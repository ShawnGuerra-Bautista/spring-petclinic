package org.springframework.samples.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.samples.petclinic.db.DatabaseForklift;
import org.springframework.samples.petclinic.db.SQLiteDatabase;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * The run function in this class runs once the application starts to run
 */
@Component
public class AppStartupRunner implements CommandLineRunner {

    private final SQLiteDatabase sqLiteDatabase;
    private final DatabaseForklift databaseForklift;

    @Autowired
    public AppStartupRunner(SQLiteDatabase sqLiteDatabase, DatabaseForklift databaseForklift) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.databaseForklift = databaseForklift;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create the db on startup
        sqLiteDatabase.createNewDatabase(SQLiteDatabase.defaultFileName);

        // Start the forklift async
        CompletableFuture.runAsync(databaseForklift::forklift)
            .thenRun(() -> System.out.println("Forklift has completed!"));
    }
}
