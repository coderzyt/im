package com.clay.im.access.server;

/**
 * @author clay
 */
public class AccessServer {

    public static void main(String[] args) {
        try {
            int port = 8888;
            new AccessServerBootstrap(port).bind();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
