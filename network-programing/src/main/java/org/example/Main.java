package org.example;

public class Main {
    public static void main(String[] args) {
        PoolWebLog poolWebLog = new PoolWebLog();
        for(String s : args){
           poolWebLog.logExecutor(s);
        }
    }
}