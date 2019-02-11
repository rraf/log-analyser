package com.test.model;

public class State {
    private Name name;
    private String type;
    private long timestamp;
    private String host;

    public State(String name, String type, Long timestamp, String host) {
        this.name = Name.valueOf(name);
        this.type = name;
        this.timestamp = timestamp;
        this.host = host;
    }

    public Name getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public enum Name {
        STARTED,
        FINISHED;
    }

    @Override
    public String toString() {
        return "State{" +
                "name=" + name +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", host='" + host + '\'' +
                '}';
    }
}
