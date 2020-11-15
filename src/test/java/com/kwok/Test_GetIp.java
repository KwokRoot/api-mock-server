package com.kwok;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Test_GetIp {
    public static void main(String[] args) throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress.getHostAddress());
        
        System.out.println(inetAddress.getHostName());
        byte[] bytes = inetAddress.getAddress();

        for (byte aByte : bytes) {
            System.out.println(aByte & 0xff);
        }
    }
}
