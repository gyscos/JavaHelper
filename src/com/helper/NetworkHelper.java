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
            Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces();
            while (list.hasMoreElements()) {
                NetworkInterface nI = list.nextElement();
                if (nI.isLoopback())
                    if (list.hasMoreElements())
                        continue;
                    else
                        return InetAddress.getByName("127.0.0.1");

                for (InterfaceAddress addr : nI.getInterfaceAddresses()) {
                    InetAddress broadcast = addr.getBroadcast();
                    if (broadcast != null)
                        return broadcast;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
