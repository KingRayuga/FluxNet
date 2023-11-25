package org.example;

import java.net.InetAddress;
import java.util.concurrent.Callable;

public class LookUpTask implements Callable<String> {
    private String line;

    LookUpTask(String line) {
        this.line = line;
    }

    @Override
    public String call() throws Exception {
        int index = line.indexOf(' ');
        String address = line.substring(0, index);
        String theRest = line.substring(index);
        String hostName = InetAddress.getByName(address).getHostName();
        return hostName + " " + theRest;
    }
}
