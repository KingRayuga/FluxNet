package org.example;

import java.io.*;
import java.net.Authenticator;
import java.net.URL;

public class SecureSourceViewer {

    public void viewAuthenticatedWebsite(String... args){
        Authenticator.setDefault(new DialogueAuthenticator());
        for(String s: args){
            try{
                URL u = new URL(s);
                try(InputStream in = new BufferedInputStream(u.openStream())){
                    Reader r = new InputStreamReader(in);
                    int c;
                    while((c = r.read()) != -1){
                        System.out.println((char) c);
                    }
                }
                System.out.println();
            } catch (IOException e) {
                System.out.println("Systum heng");
            }
        }
        System.exit(0);
    }

}
