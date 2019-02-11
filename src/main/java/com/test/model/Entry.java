package com.test.model;

public class Entry {
    private String id;
    private State start;
    private State end;

    public Entry(String id, State start, State end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public long getDuration() {
        return end.getTimestamp() - start.getTimestamp();
    }

    public String getLogType() {
        String type = start.getType();
        if(type == null) {
            type = end.getType();
        }
        return type;
    }

    public String getHost() {
        String host = start.getHost();
        if(host == null) {
            host = end.getHost();
        }
        return host;
    }

    public boolean alert() {
        return getDuration() >= 8;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", duration=" + getDuration() +
                ", logType='" + getLogType() + '\'' +
                ", host='" + getHost() + '\'' +
                ", alert=" + alert() +
                '}';
    }
}

