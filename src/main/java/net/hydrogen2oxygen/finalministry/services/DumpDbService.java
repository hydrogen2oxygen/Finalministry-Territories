package net.hydrogen2oxygen.finalministry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Use this to create a dump of the H2 database for use with flyway
 */
@Service
public class DumpDbService {

    private final Object monitor = new Object();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DumpDbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dumpDb() throws IOException {
        synchronized (this.monitor) {
            File dump = new File("dump.sql");
            if (dump.exists()) {
                System.out.println("File dump.sql alread exist!");
            }
            this.jdbcTemplate.execute("SCRIPT TO 'db-dump.sql'");
        }
    }
}
