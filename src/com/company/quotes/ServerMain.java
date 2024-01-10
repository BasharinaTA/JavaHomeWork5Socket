package com.company.quotes;

public class ServerMain {
    private static final int PORT = 8000;
    private static final String HOST = "25.58.36.44";

    public static void main(String[] args) {
        Server server = new Server(PORT, HOST);
        server.run();
    }
}
