package com.company.quotes;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    final int port;
    final String host;
    Scanner sc = new Scanner(System.in);

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        try (Socket client = new Socket(host, port)) {
            System.out.println("Клиент> инициализирован");

            String login = receiveMessage(client);
            System.out.println(login);
            sendMessage(client);

            String password = receiveMessage(client);
            System.out.println(password);
            sendMessage(client);

            while (true) {
                sendMessage(client);
                String message = receiveMessage(client);
                if (message == null) {
                    System.out.println("Соединение было прервано");
                    break;
                }
                System.out.println(message);
            }
        }
        catch (SocketException e) {

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket client) throws IOException {
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(client.getOutputStream())
                ), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        out.println(reader.readLine());
    }

    private String receiveMessage(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );
        return in.readLine();
    }
}
