package org.example;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;

import java.util.ArrayList;
import java.util.List;

public class TCPHandshakeClient {
    public static void TCPHandshake() {
        PcapIf device = getNetworkDevice();

        if (device == null) {
            System.out.println("cannot find any networking device");
            System.exit(1);
        }

        Pcap pcap = openDevice(device);

        PcapPacket synPacket = sendSynPacket(pcap);
        PcapPacket synAckPacket = waitForSynAck(pcap);
        sendAckPacket(pcap, synAckPacket);

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

    private static PcapPacket sendSynPacket(Pcap pcap){
        PcapPacket synPacket = new PcapPacket(54);
        //ethertype,protocol = tcp
        synPacket.setUByte(14+12,0x08);
        synPacket.setUByte(23,0x06);

        //ipaddress
        synPacket.setByteArray(26,new byte[]{(byte)192,(byte)168,1,1});
        synPacket.setByteArray(30,new byte[]{(byte)192,(byte)168,1,2});

        //port
        synPacket.setUShort(34,46969);
        synPacket.setUShort(36,80);

        //flag (syn)
        synPacket.setUShort(46,0x5002);

        //checksum
        synPacket.scan(Tcp.ID);

        //sending syn packet
        pcap.sendPacket(synPacket);

        return synPacket;
    }

    private static PcapPacket waitForSynAck(Pcap pcap){
        final PcapPacket[] lastPacket = {null};
        PcapPacketHandler<PcapPacket> pcapPacketHandler = (pcapPacket, jHeaders) -> {
            if(pcapPacket.hasHeader(Tcp.ID)){
                Tcp tcp = pcapPacket.getHeader(new Tcp());
                if(tcp.flags_SYN() && tcp.flags_ACK()){
                    System.out.println("Received Syn Ack");
                    //stop capturing packet
                    lastPacket[0] = pcapPacket;
                    pcap.breakloop();
                }
            }
        };
        //capture packet until syn-ack or timeout occurs
        //pcap.loop(1, pcapPacketHandler, "TCP packets");
        return lastPacket[0];
    }

    private static void sendAckPacket(Pcap pcap, PcapPacket synAckPacket){
        PcapPacket ackPacket = new PcapPacket(synAckPacket.getTotalSize());
        ackPacket.transferFrom(synAckPacket);
        Tcp ackTcp = ackPacket.getHeader(new Tcp());
        ackTcp.flags_ACK(true);
        ackPacket.scan(Tcp.ID);
        pcap.sendPacket(ackPacket);
    }
}
