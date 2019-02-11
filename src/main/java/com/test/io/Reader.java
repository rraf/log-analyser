package com.test.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Reader {

    Logger LOGGER = LoggerFactory.getLogger(Reader.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final LineIterator iterator;

    /**
     * not converting file -> string first to ensure memory optimisation but this compromises ease of testing somewhat
     * @param file
     * @throws IOException
     */
    public Reader(File file) throws IOException {
        iterator = FileUtils.lineIterator(file, "UTF-8");
    }

    public boolean hasMore() {
        return iterator.hasNext();
    }

    public LogLine next() throws IOException {
        String line = iterator.next();
        LOGGER.debug("Next string line: {}", line);
        LogLine logLine = readJson(line);
        LOGGER.debug("Into POJO: {}", logLine);
        return logLine;
    }

    private LogLine readJson(String line) throws IOException {
        return OBJECT_MAPPER.readValue(line, LogLine.class);
    }
}
