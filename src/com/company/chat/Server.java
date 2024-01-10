package com.company.chat;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int port;
    final String host;
    static final String[] arrQuestions = {"Добрый день. Представьтесь, пожалуйста.",
            "Что такое ООП? Назовите принципы с примерами.",
            "Какие элементы языка отвечают за инкапсуляцию?",
            "Какие элементы языка отвечают за наследование?",
            "Какие элементы языка отвечают за полиморфизм?",
            "Что такое перегрузки (overloading) метода?",
            "Что такое переопределение (override) метода?",
            "Какие элементы могут содержать класс?",
            "Что такое модификаторы доступа в Java? Назовите их. Для чего используются?"
    };

    public Server(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port, 10, InetAddress.getByName(host))) {
            System.out.println("Сервер> инициализирован.");
            try (Socket client = server.accept()) {
                System.out.println("Сервер> клиент подключен.");
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(client.getOutputStream())
                        ), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(client.getInputStream())
                );

                for (String item : arrQuestions) {
                    out.println(item);
                    in.readLine();
                }
                out.println("Ваши ответы будут обработаны.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
