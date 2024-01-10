package com.company.chat;

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
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(client.getOutputStream())
                    ), true
            );
            while (true) {
                String messageServer = in.readLine();
                if (messageServer == null) {
                    System.out.println("Соединение было прервано");
                    break;
                }
                System.out.println(messageServer);
                String message = sc.nextLine();
                out.println(message);
            }
        }
        catch (SocketException e) {

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
