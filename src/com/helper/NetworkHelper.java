package com.helper;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkHelper {

    public static InetAddress getBroadcast() {
        try {
            NetworkInterface nI = getInterface();
            if (nI.isLoopback())
                return InetAddress.getByName("127.0.0.1");
            else
                for (InterfaceAddress addr : nI.getInterfaceAddresses()) {
                    InetAddress broadcast = addr.getBroadcast();
                    if (broadcast != null)
                        return broadcast;
                }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NetworkInterface getInterface() {
        try {
            Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces();
            while (list.hasMoreElements()) {
                NetworkInterface nI = list.nextElement();
                if (nI.isLoopback())
                    if (list.hasMoreElements())
                        continue;
                    else
                        return nI;

                return nI;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InetAddress getLocalIp() {
        NetworkInterface nI = getInterface();
        for (InterfaceAddress addr : nI.getInterfaceAddresses()) {
            InetAddress broadcast = addr.getBroadcast();
            if (broadcast != null)
                return addr.getAddress();
        }
        return null;
    }
}
