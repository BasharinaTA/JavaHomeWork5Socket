package com.company.chat;

public class Main {
    public static final int PORT = 8000;
    public static final String HOST = "25.58.36.44";

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            Server server = new Server(PORT, HOST);
            server.run();
        });

        Client client = new Client(PORT, HOST);
        thread.start();
        client.run();

    }
}
