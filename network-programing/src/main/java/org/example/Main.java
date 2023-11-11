package org.example;

public class Main {
    public static void main(String[] args) {
        for(String filename : args){
            Thread t = new DigestThread(filename);
            t.start();
        }
    }
}