package com.example.practice.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

// ==========================================
// PROVIDED COMPONENTS (Do not modify models)
// ==========================================

enum LogLevel {
    DEBUG, INFO, WARN, ERROR
}

class LogEntry {
    private final long timestampMs;
    private final LogLevel level;
    private final String message;

    public LogEntry(long timestampMs, LogLevel level, String message) {
        this.timestampMs = timestampMs;
        this.level = level;
        this.message = message;
    }

    public long getTimestampMs() {
        return timestampMs;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return timestampMs + " [" + level + "] " + message;
    }
}

class Query {
    private LogLevel level;
    private String contains;
    private Long startTsInclusive;
    private Long endTsInclusive;

    // Builder pattern provided for easy query construction in your tests
    public static class   Builder {
        private LogLevel level;
        private String contains;
        private Long startTsInclusive;
        private Long endTsInclusive;

        public Builder level(LogLevel level) {
            this.level = level;
            return this;
        }

        public Builder contains(String contains) {
            this.contains = contains;
            return this;
        }

        public Builder startTsInclusive(Long startTs) {
            this.startTsInclusive = startTs;
            return this;
        }

        public Builder endTsInclusive(Long endTs) {
            this.endTsInclusive = endTs;
            return this;
        }

        public Query build() {
            Query q = new Query();
            q.level = this.level;
            q.contains = this.contains;
            q.startTsInclusive = this.startTsInclusive;
            q.endTsInclusive = this.endTsInclusive;
            return q;
        }
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getContains() {
        return contains;
    }

    public Long getStartTsInclusive() {
        return startTsInclusive;
    }

    public Long getEndTsInclusive() {
        return endTsInclusive;
    }
}


// ==========================================
// YOUR IMPLEMENTATION (Complete this block)
// ==========================================

class LogStore {
    private List<LogEntry> existingLogs; // Example in-memory storage. You can choose a different structure if needed.

    public LogStore() {
        // TODO: Initialize your in-memory data structures here
        existingLogs = new ArrayList<>(); // Example: This will hold all logs in memory. You can choose a different structure if needed.
        // Note: Consider thread safety if your Phase 3 async writer will read concurrently!
    }

    public List<LogEntry> getExistingLogs() {
        return existingLogs;
    }

    public void setExistingLogs(List<LogEntry> existingLogs) {
        this.existingLogs = existingLogs;
    }

    /**
     * PHASE 1: Append
     * Adds a log entry to the store.
     * <p>
     * Requirements:
     * 1. Must reject null values for `level` and `message` (e.g., throw IllegalArgumentException).
     * 2. Must preserve insertion order.
     */
    public void append(LogEntry entry) {
        // TODO: Implement append logic
        if (entry.getLevel() == null || entry.getMessage() == null) {
            throw new IllegalArgumentException("Log level cannot be null");
        }
        existingLogs.add(entry);
    }

    /**
     * PHASE 1: Query
     * Returns all log entries that satisfy all provided filters.
     * <p>
     * Requirements:
     * 1. Level: Exact match.
     * 2. Contains: Case-sensitive substring match. If empty string (""), treat as no filter.
     * 3. Time range: Inclusive bounds. If startTsInclusive > endTsInclusive, return empty list.
     * 4. Results must preserve insertion order.
     * 5. Returned list must be immutable.
     * 6. If no filters are provided, return all logs.
     */
    public List<LogEntry> query(Query q) {
        // TODO: Implement query logic
        List<LogEntry> matchingLogs = existingLogs.stream().filter(logEntry -> {
            if (q.getLevel() != null && logEntry.getLevel() != q.getLevel()) {
                return false;
            }
            if (q.getContains() != null && !q.getContains().isEmpty() && !logEntry.getMessage().contains(q.getContains())) {
                return false;
            }
            if (q.getStartTsInclusive() != null && logEntry.getTimestampMs() < q.getStartTsInclusive()) {
                return false;
            }
            return q.getEndTsInclusive() == null || logEntry.getTimestampMs() <= q.getEndTsInclusive();
        }).toList();
        return matchingLogs; // Placeholder return
    }

    /**
     * PHASE 2: Query Count
     * Adds a new feature to return just the count of matching logs.
     * <p>
     * Requirements:
     * 1. Apply the exact same filtering rules as `query(...)`.
     * 2. Return the integer count of matching logs.
     * 3. Optimize if possible (e.g., avoid building the whole list in memory if not needed).
     */
    public int queryCount(Query q) {
        // TODO: Implement query count logic
        int count = (int) existingLogs.stream().filter(logEntry -> {
            if (q.getLevel() != null && logEntry.getLevel() != q.getLevel()) {
                return false;
            }
            if (q.getContains() != null && !q.getContains().isEmpty() && !logEntry.getMessage().contains(q.getContains())) {
                return false;
            }
            if (q.getStartTsInclusive() != null && logEntry.getTimestampMs() < q.getStartTsInclusive()) {
                return false;
            }
            return q.getEndTsInclusive() == null || logEntry.getTimestampMs() <= q.getEndTsInclusive();
        }).count();
        return count; // Placeholder return
    }

    // TODO: Add any helper methods you might need for Phase 3 here
}


// ==========================================
// RUNNER & PHASE 3
// ==========================================

public class Application {
    static class LogEngine {
        private final LogStore store;
        private final ScheduledExecutorService scheduler;
        private final String filePath; // Path to the file where logs will be flushed
        private int flushCount = 0; // count already read

        public LogEngine(LogStore store, String filePath) {
            this.store = store;
            this.scheduler = Executors.newSingleThreadScheduledExecutor();
            this.filePath = filePath;
        }

        public void start() {
            scheduler.scheduleAtFixedRate(this::flushLogsToFile, 10, 10, java.util.concurrent.TimeUnit.SECONDS);
            System.out.println("Async log flusher started, flushing every 10 seconds to: " + filePath);
        }

        private void flushLogsToFile() {
            List<LogEntry> logsToFlush = store.getExistingLogs();
            if (logsToFlush.size() <= flushCount) {
                System.out.println("No new logs to flush.");
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                for (int i = flushCount; i < logsToFlush.size(); i++) {
                    writer.write(logsToFlush.get(i).toString());
                    writer.newLine();
                }
                System.out.println("Flushed " + (logsToFlush.size() - flushCount) + " logs to file.");
                flushCount = logsToFlush.size();
            } catch (Exception e) {
                System.err.println("Error flushing logs to file: " + e.getMessage());
            }
        }
        public void stop() {
            scheduler.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // TODO: Write your Phase 1 and Phase 2 basic tests here to verify correctness.
        // Example:
        // store.append(new LogEntry(1000, LogLevel.INFO, "System started"));
        // Query q = new Query.Builder().level(LogLevel.INFO).build();
        // System.out.println(store.query(q));

        System.out.println("Ready to implement Log Engine!");
        File file = new File("logs.txt");
        System.out.println("The log file is located at: " + file.getAbsolutePath());
        LogStore store = new LogStore();
        store.append(new LogEntry(1000, LogLevel.INFO, "Server started"));
        store.append(new LogEntry(1050, LogLevel.DEBUG, "Initializing DB connection"));
        store.append(new LogEntry(2000, LogLevel.ERROR, "DB connection failed"));
        store.append(new LogEntry(2500, LogLevel.WARN, "Retrying DB connection"));
        try {
            store.append(new LogEntry(100, null, "Msg"));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for null level: " + e.getMessage());
            /* Expected */
        }
        // Phase 1: Query Test

        /**
         * PHASE 3: Async File Writer
         *
         * Requirements:
         * 1. Write an async scheduler that reads from the local storage.
         * 2. Writes to a file every 10 seconds.
         * 3. Must not block the main thread or `append` operations unnecessarily.
         * 4. Have the code commented out here. Uncomment to run.
         */
        // DO: Implement your scheduler (e.g., using ScheduledExecutorService or Thread)
        // and uncomment the below lines to test it.

        LogEngine logEngine = new LogEngine(store, "logs.txt");
        logEngine.start();

        /*
        // TO
        // Start the scheduler
        // scheduler.scheduleAtFixedRate(logFlusherTask, 10, 10, TimeUnit.SECONDS);
        */
        for (int i = 0; i < 5; i++) {
            Thread.sleep(3000); // Wait 3 seconds
            store.append(new LogEntry(System.currentTimeMillis(), LogLevel.INFO, "App heartbeat " + i));
            System.out.println("Appended new log. Total logs: " + store.queryCount(new Query.Builder().build()));
        }

        Thread.sleep(12000); // Wait long enough for the 10-second scheduler to trigger
        logEngine.stop();
        System.out.println("Application shutdown.");
        Query q1 = new Query.Builder()
                .level(LogLevel.ERROR)
                .startTsInclusive(1500L)
                .build();
        System.out.println("Query 1 result: " + store.query(q1));

        // Phase 2: Query Count Test
        Query q2 = new Query.Builder()
                .contains("DB")
                .build();
        System.out.println("Query 2 (contains 'DB') count: " + store.queryCount(q2));
    }

}
