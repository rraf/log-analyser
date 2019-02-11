package com.test.model;

import com.test.io.LogLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    private final Map<String, LogLine> unpaired = new HashMap<>();

    public Entry pair(LogLine line) {
        LOGGER.debug("Processing line: {}", line);
        if(unpaired.containsKey(line.getId())) {
            LogLine otherSide = unpaired.get(line.getId());
            LOGGER.debug("Found matching: {}", otherSide);
            Entry entry = createEntry(otherSide, line);
            LOGGER.debug("Created entry: {}", entry);
            if(entry.alert()) {
                LOGGER.warn("Found log with alert=true: {}", entry);
            }
            return entry;
        } else {
            unpaired.put(line.getId(), line);
            LOGGER.debug("New unpaired line. Putting in unpaired bucket: {}", line);
            return null;
        }
    }

    private Entry createEntry(LogLine line1, LogLine line2) {
        State state1 = createState(line1);
        State state2 = createState(line2);
        State startState, endState;
        if(state1.getName() == State.Name.STARTED) {
            startState = state1;
            endState = state2;
        } else {
            startState = state2;
            endState = state2;
        }
        return new Entry(line1.getId(), startState, endState);
    }

    private State createState(LogLine logLine) {
        return new State(logLine.getState(),
                logLine.getType(),
                logLine.getTimestamp(),
                logLine.getHost()
        );
    }
}
