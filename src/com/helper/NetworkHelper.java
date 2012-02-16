package com.helper;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkHelper {

    public static InetAddress getBroadcast() {
        try {
            Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces();
            while (list.hasMoreElements()) {
                NetworkInterface nI = list.nextElement();
                if (nI.isLoopback())
                    continue;

                for (InterfaceAddress addr : nI.getInterfaceAddresses()) {
                    InetAddress broadcast = addr.getBroadcast();
                    if (broadcast != null)
                        return broadcast;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
