package com.clay.im.logic.server;

/**
 * @author clay
 */
public class LogicServer {
    public static void main(String[] args) {
        try {
            int port = 8989;
            new LogicServerBootstrap(port).bind();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
