package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUsage {
    public static void getAddress(String s){
        try{
            InetAddress address = InetAddress.getByName(s);
            System.out.println("Ip address is " + address);
        }catch (UnknownHostException exception){
            System.out.println("Cannot find the ip address");
        }
    }

    public static void getHostName(String s){
        try{
            InetAddress address = InetAddress.getByName(s);
            System.out.println(address.getHostName());
        }catch (UnknownHostException e){
            System.out.println("Cannot find host name");
        }
    }

    public static void getAllAddress(String s){
        try{
            InetAddress[] address = InetAddress.getAllByName(s);
            System.out.println("IP addresses are");
            for(var ip : address){
                System.out.println(ip);
            }
        }catch (UnknownHostException e){
            System.out.println("cannot find any IP address");
        }
    }

    public static void getLocalHost(){
        try{
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Local Host is " + address);
        }catch (UnknownHostException e){
            System.out.println("cannot find local host");
        }
    }

    public static void getCanonicalHostName(String s){
        try{
            InetAddress address = InetAddress.getByName(s);
            System.out.println(address.getCanonicalHostName());
        }catch(UnknownHostException e){
            System.out.println("Cannot find host name");
        }
    }

    public static void IPcharecterstics(String s){
        try {
            InetAddress address = InetAddress.getByName(s);
            System.out.println("Addres is " + address);
            if (address.isAnyLocalAddress()) {
                System.out.println("Its local address");
            }
            if (address.isLoopbackAddress()) {
                System.out.println("Its loop back address");
            }
            if (address.isLinkLocalAddress()) {
                System.out.println("Its link local address");
            } else if (address.isSiteLocalAddress()) {
                System.out.println("Its site local address");
            } else {
                System.out.println("Its global address");
            }
            if (address.isMulticastAddress()) {
                if (address.isMCGlobal()) {
                    System.out.println("Its a global multicast address");
                } else if (address.isMCOrgLocal()) {
                    System.out.println("Its Org Local multicast");
                } else if (address.isMCSiteLocal()) {
                    System.out.println("Its Site local multicast");
                }
                else if (address.isMCLinkLocal()) {
                    System.out.println("Its a subnet wide multicast");
                } else if (address.isMCNodeLocal()) {
                    System.out.println("Its an interface-local multicast");
                } else {
                    System.out.println("Its an unknown multicast");
                }
            }else{
                System.out.println("its a unicast address");
            }

        }catch(UnknownHostException e){
            System.out.println("cannot resolve the IP Address");
        }
    }
}
