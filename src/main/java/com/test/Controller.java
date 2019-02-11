package com.test;

import com.test.io.LogLine;
import com.test.io.Reader;
import com.test.io.Writer;
import com.test.model.Entry;
import com.test.model.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    public void process(String filename) throws IOException, SQLException {
        File inputFile = new File(filename);
        LOGGER.info("starting to process {}", filename);
        Reader reader = new Reader(inputFile);
        Processor processor = new Processor();
        Writer writer = new Writer(inputFile.getName());
        LOGGER.info("Initialised. Starting processing...");
        //This ensures we stream file through an iterator to enable processing of very large without causing mem spikes
        while(reader.hasMore()) {
            LogLine logLine = reader.next();
            LOGGER.debug("Log line: {}", logLine);

            Entry entry = processor.pair(logLine);
            if(entry != null) {
                LOGGER.debug("Paired. Will write: {}", entry);
                writer.write(entry);
            }
        }
        writer.close();
        LOGGER.info("Done");
    }

    public static void main(String []args) throws IOException, SQLException {
        if(args.length == 0) {
            LOGGER.warn("Usage: java -jar <projectJarName> <inputFile>");
            return;
        }
        LOGGER.info("Input filename: {}", args[0]);
        Controller c = new Controller();
        c.process(args[0]);
    }
}
