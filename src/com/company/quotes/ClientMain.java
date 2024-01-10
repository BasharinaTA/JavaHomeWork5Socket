package com.company.quotes;

public class ClientMain {
    private static final int PORT = 8000;
    private static final String HOST = "25.58.36.44";

    public static void main(String[] args) {
        Client client1 = new Client(PORT, HOST);
        client1.run();
    }
}
