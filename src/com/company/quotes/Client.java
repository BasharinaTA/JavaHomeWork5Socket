package com.company.quotes;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static com.company.quotes.Server.END;
import static com.company.quotes.Server.OK;

public class Client {
    final int port;
    final String host;

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        try (Socket client = new Socket(host, port)) {
            System.out.println("Клиент> инициализирован");

            String textLogin = receiveMessage(client);
            System.out.println(textLogin);
            sendMessage(client);

            String textPassword = receiveMessage(client);
            System.out.println(textPassword);
            sendMessage(client);

            String answer = receiveMessage(client);
            if (!OK.equals(answer)) {
                System.out.println(answer);
                return;
            }

            System.out.println("Вы вошли");
            while (true) {
                sendMessage(client);
                String message = receiveMessage(client);
                if (message == null) {
                    System.out.println("Клиент отключен");
                    break;
                }
                if (END.equals(message)) {
                    System.out.println("Лимит цитат достигнут");
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
