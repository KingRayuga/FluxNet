package org.example;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;

import java.util.ArrayList;
import java.util.List;

public class TCPHandshakeServer {
    public static void TCPHandShake(){
        PcapIf device = getNetworkDevice();
        if(device==null){
            System.out.println("Cannot find any networking device");
        }
        Pcap pcap = openDevice(device);

        waitForSyn(pcap);
        sendSynAck(pcap);
        waitForFinalAck(pcap);

        pcap.close();
    }

    private static PcapIf getNetworkDevice() {
        List<PcapIf> allDevs = new ArrayList<>();
        StringBuilder errBuff = new StringBuilder();
        int result = Pcap.findAllDevs(allDevs, errBuff);
        if (result != Pcap.OK || allDevs.isEmpty()) {
            System.out.println("Error: " + errBuff);
            return null;
        }
        return allDevs.get(0);
    }

    private static Pcap openDevice(PcapIf device) {
        StringBuilder errBuf = new StringBuilder();
        int snaplen = 64 * 1024;
        int flag = Pcap.MODE_NON_PROMISCUOUS;
        int timeout = 10 * 1000;

        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flag, timeout, errBuf);
        return pcap;
    }

    private static void waitForSyn(Pcap pcap){
        PcapPacketHandler<PcapPacket> pcapPacketHandler = (pcapPacket, jHeaders) -> {
            if(pcapPacket.hasHeader(Tcp.ID)){
                Tcp tcp = pcapPacket.getHeader(new Tcp());
                if(tcp.flags_SYN() && !tcp.flags_ACK()){
                    System.out.println();
                    pcap.breakloop();
                }
            }
        };
        //final int counts = 1;
        //pcap.loop(counts,pcapPacketHandler,"");
    }

    private static void sendSynAck(Pcap pcap) {
        // Construct and send SYN-ACK packet
        PcapPacket synAckPacket = new PcapPacket(54);
        synAckPacket.setUByte(14 + 12, 0x08);
        synAckPacket.setUByte(23, 0x06);

        // Set source and destination IP addresses
        synAckPacket.setByteArray(26, new byte[]{(byte) 192, (byte) 168, 1, 2});
        synAckPacket.setByteArray(30, new byte[]{(byte) 192, (byte) 168, 1, 1});

        // Set source and destination ports
        synAckPacket.setUShort(34, 80);
        synAckPacket.setUShort(36, 1234);

        // Set sequence and acknowledgment numbers
        synAckPacket.setUInt(38, 123456);
        synAckPacket.setUInt(42, 123457);

        synAckPacket.setUShort(46, 0x5012);

        synAckPacket.scan(Tcp.ID);

        pcap.sendPacket(synAckPacket);

        System.out.println("SYN-ACK sent. Waiting for final ACK...");
    }

    private static void waitForFinalAck(Pcap pcap){
        PcapPacketHandler<PcapPacket> pcapPacketHandler = (pcapPacket, jHeaders) -> {
            if(pcapPacket.hasHeader(Tcp.ID)){
                Tcp tcp = pcapPacket.getHeader(new Tcp());

                if(!tcp.flags_SYN() && tcp.flags_ACK()){
                    System.out.println("Received final ACK");
                    pcap.breakloop();
                }
            }
        };
        //final int counts = 1;
        //pcap.loop(counts,pcapPacketHandler,"");
    }

}
