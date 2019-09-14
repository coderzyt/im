package com.clay.im.access;

/**
 * @author clay
 */
public class AccessServer {

    public static void main(String[] args) {
        try {
            int port = 8888;
            new AccessServerBootstrap(port).bind();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
