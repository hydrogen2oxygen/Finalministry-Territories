package net.hydrogen2oxygen.finalministry;

import net.hydrogen2oxygen.finalministry.services.DumpDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Autowired
	private DumpDbService dumpDbService;

	@Override
	public void run(String... args) {
		logger.info("EXECUTING : command line runner");

		for (int i = 0; i < args.length; ++i) {
			if (i==0 && "dumpDB".equals(args[0])) {
				try {
					dumpDbService.dumpDb();
				} catch (IOException e) {
					logger.error("Error while trying to create database dump", e);
				}
			}
		}
	}
}
