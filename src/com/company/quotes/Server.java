package com.company.quotes;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server {
    final int port;
    final String host;
    static final String[] arrQuotes = {
            "Сложнее всего начать действовать, все остальное зависит только от упорства.",
            "Логика может привести Вас от пункта А к пункту Б, а воображение — куда угодно.",
            "Если вы думаете, что на что-то способны, вы правы; если думаете, что у вас ничего не получится - вы тоже правы.",
            "Стоит только поверить, что вы можете – и вы уже на полпути к цели.",
            "Единственный способ сделать что-то очень хорошо – любить то, что ты делаешь.",
            "Не бойтесь неудач, потому что это ваш путь к успеху.",
            "Никогда не ошибается тот, кто ничего не делает.",
            "Все наши мечты могут сбыться, если у нас хватит смелости их осуществить.",
            "Недостаточно просто знать, нужно использовать знания. Мало хотеть чего-то, нужно делать.",
            "Неудача — это просто возможность начать снова, но уже более мудро.",
            "Вы можете быть кем угодно, если вы уделите этому время.",
            "На самом деле жизнь проста, но мы настойчиво ее усложняем.",
            "Вы никогда не будете слишком стары, чтобы ставить другую цель или выбрать новую мечту.",
            "Никогда не поздно стать тем, кем вы могли бы быть.",
            "Когда мы больше не в состоянии изменить ситуацию, нам надо менять себя.",
            "Вам не хватит времени, чтобы сделать всё, однако у вас достаточно времени, чтобы сделать самое важное.",
            "Секрет успеха в том, чтобы приступить к действиям.",
            "Искусство быть счастливым заключается в способности радоваться обычным вещам.",
            "Не столь важно, как медленно ты идешь, как то, как долго ты идешь, не останавливаясь.",
            "Единственный способ достичь невозможного — это поверить в то, что оно возможно."
    };
    Map<String, String> users = new HashMap<>();
    Map<Integer, Integer> requests = new HashMap<>();

    public Server(int port, String host) {
        this.port = port;
        this.host = host;
    }

    private void listUser() {
        String user = "admin";
        for (int i = 0; i < 5; i++) {
            users.put(user + (i + 1), user + (i + 1));
        }
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port, 10, InetAddress.getByName(host))) {
            listUser();
            System.out.println("Сервер> инициализирован");
            try (Socket client = server.accept()) {

                // Проверка логина
                sendMessageToClient(client, "Введите имя пользователя");
                String userName = receiveMessageFromClient(client);
                if (!checkLogin(userName)) {
                    sendMessageToClient(client, "Указан неверный логин. Клиент подключиться не может");
                    return;
                }

                // Проверка пароля
                sendMessageToClient(client, "Введите пароль пользователя");
                String password = receiveMessageFromClient(client);
                if (!checkPassword(userName, password)) {
                    sendMessageToClient(client, "Указан неверный пароль. Клиент подключиться не может");
                    return;
                }

                System.out.println("Сервер> клиент " + client.getPort() + " подключен " + new Date());
                requests.put(client.getPort(), 5);
                while (true) {
                    String messageClient = receiveMessageFromClient(client).toLowerCase();
                    if (messageClient.equals("bye")) {
                        break;
                    }
                    if (requests.get(client.getPort()) <= 0) {
                        sendMessageToClient(client, "Максимально количество цитат достигнуто");
                        break;
                    }
                    String messageServer = arrQuotes[new Random().nextInt(0, 19)];
                    sendMessageToClient(client, messageServer);
                    System.out.println("Сервер> клиенту " + client.getPort() + " отправлено сообщение: " + messageServer);
                    requests.put(client.getPort(), requests.get(client.getPort()) - 1);
                }
                System.out.println("Сервер> клиент " + client.getPort() + " отсоединился " + new Date());
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToClient(Socket client, String message) throws IOException {
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(client.getOutputStream())
                ), true
        );
        out.println(message);
    }

    private String receiveMessageFromClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        return in.readLine();
    }

    private boolean checkLogin(String login) {
        return users.containsKey(login);
    }

    private boolean checkPassword(String login, String password) {
        return users.get(login).equals(password);
    }
}
