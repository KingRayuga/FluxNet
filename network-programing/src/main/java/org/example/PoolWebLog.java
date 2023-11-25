package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class PoolWebLog {
    private final int NUM_THREADS;

    public PoolWebLog() {
        NUM_THREADS = 32;
    }

    public void logExecutor(String s){
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        Queue<LogEntry> results = new LinkedList<>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(s), StandardCharsets.UTF_8))) {
            for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
                LookUpTask task = new LookUpTask(entry);
                Future<String> future = executor.submit(task);
                LogEntry result = new LogEntry(entry, future);
                results.add(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (LogEntry result : results) {
            try {
                System.out.println(result.future.get());
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println(result.original);
            }
        }

        executor.shutdown();

    }

    private static class LogEntry {
        String original;
        Future<String> future;

        LogEntry(String original, Future<String> future) {
            this.original = original;
            this.future = future;
        }
    }

}
