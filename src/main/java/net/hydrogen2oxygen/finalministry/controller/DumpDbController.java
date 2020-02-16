package net.hydrogen2oxygen.finalministry.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Use this to create a dump of the H2 database for use with flyway
 */
@Controller
public class DumpDbController {

    private final Object monitor = new Object();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DumpDbController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping("/dumpDb")
    @ResponseBody
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
