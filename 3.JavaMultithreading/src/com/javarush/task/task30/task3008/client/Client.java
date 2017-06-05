package com.javarush.task.task30.task3008.client;

/**
 * Created by DmRG on 04.06.2017.
 */

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;


/**
 * Created by DmRG on 04.06.2017.
 */


public class Client {
    protected Connection connection;

    /*
    * boolean clientConnected будет устанавливаться в true, если клиент
    * подсоединен к серверу или в false в противном случае.
    * */
    private volatile boolean clientConnected = false;

    /*
    * класс SocketThread унаследованный от Thread в классе
    * Client. Он будет отвечать за поток, устанавливающий сокетное соединение и
    * читающий сообщения сервера.
    * */
    public class SocketThread extends Thread {
        @Override
        public void run() {
            try {

                /*
                * адрес и порт сервера с помощью методов getServerAddress() и
                * getServerPort().
                *
                * новый объект класса java.net.Socket
                * */
                Socket socket = new Socket(getServerAddress(), getServerPort());


                /*
                * объект класса Connection
                * */
                connection = new Connection(socket);

                /*
                * метод, реализующий "рукопожатие" клиента с сервером
                * clientHandshake()
                * */
                clientHandshake();

                /*
                *  метод, реализующий основной цикл обработки сообщений сервера.
                * */
                clientMainLoop();

            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
                ConsoleHelper.writeMessage(e.getMessage());
            }
        }


        /*
        * void processIncomingMessage(String message) – должен выводить текст message в
        * консоль
        * */
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        /*
        * void informAboutAddingNewUser(String userName) – должен выводить в консоль
        * информацию о том, что участник с именем userName присоединился к чату
        * */
        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage(userName + " присоединился к чату.");
        }

        /*
        * void informAboutDeletingNewUser(String userName) – должен выводить в
        * консоль, что участник с именем userName покинул чат
        * */
        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage(userName + " покинул чат.");
        }

        /* void notifyConnectionStatusChanged(boolean clientConnected) – этот метод
        * должен:
        * 1.	Устанавливать значение поля clientConnected класса Client в соответствии с
        * переданным параметром.
        * 2.	Оповещать (пробуждать ожидающий) основной поток класса Client. Для
        * класса SocketThread внешним классом является класс Client.
        * */
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        /*
        * Этот метод будет представлять клиента серверу.
        * */
        protected void clientHandshake() throws IOException, ClassNotFoundException {

            Message message;

            /*
            * В цикле получать сообщения, используя соединение connection
            * */
            while (true) {
                message = connection.receive();

                /*
                * Если тип полученного сообщения NAME_REQUEST (сервер запросил имя),
                * запросить ввод имени пользователя с помощью метода getUserName(), создать
                * новое сообщение с типом USER_NAME и введенным именем, отправить
                * сообщение серверу.
                * */
                if (message.getType() == MessageType.NAME_REQUEST) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));

                /*
                * Если тип полученного сообщения NAME_ACCEPTED (сервер принял имя), значит
                * сервер принял имя клиента, нужно об этом сообщить главному потоку, он этого
                * очень ждет. Сделай это с помощью метода notifyConnectionStatusChanged()
                * */
                }else if (message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    break;
                }else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        /*
        *  Этот метод будет реализовывать главный цикл обработки
        *  сообщений сервера.
        * */
        protected void clientMainLoop() throws IOException, ClassNotFoundException {

            while (true) {

                /*
                * Получи сообщение от сервера, используя соединение connection.
                * */
                Message message = connection.receive();

                /*
                * Если это текстовое сообщение (тип TEXT), обработай его с помощью метода
                * processIncomingMessage().
                * */
                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());

                /*
                * Если это сообщение с типом USER_ADDED, обработай его с помощью метода
                * informAboutAddingNewUser().
                * */
                } else if (message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());

                /*
                * Если это сообщение с типом USER_REMOVED, обработай его с помощью метода
                * informAboutDeletingNewUser().
                * */
                } else if (message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }
    }

    /*
    * String getServerAddress() – должен запросить ввод адреса сервера у
    * пользователя и вернуть введенное значение. Адрес может быть строкой, содержащей
    * ip, если клиент и сервер запущен на разных машинах или ‘localhost’, если клиент и
    * сервер работают на одной машине.
    * */
    protected String getServerAddress() {
        ConsoleHelper.writeMessage("ввод адреса сервера");
        return ConsoleHelper.readString();
    }

    /*
    * int getServerPort() – должен запрашивать ввод порта сервера и возвращать его.
    * */
    protected int getServerPort() {
        ConsoleHelper.writeMessage("ввод порта сервера");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("имя пользователя");
        return ConsoleHelper.readString();
    }

    /*
    * boolean shouldSentTextFromConsole() – в данной реализации клиента всегда
    * должен возвращать true (мы всегда отправляем текст введенный в консоль). Этот
    * метод может быть переопределен, если мы будем писать какой-нибудь другой
    * клиент, унаследованный от нашего, который не должен отправлять введенный в
    * консоль текст.
    * */
    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    /*
    * SocketThread getSocketThread() – должен создавать и возвращать новый объект
    * класса SocketThread.
    * */
    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    /*
    * void sendTextMessage(String text) – создает новое текстовое сообщение,
    * используя переданный текст и отправляет его серверу через соединение connection.
    * Если во время отправки произошло исключение IOException, то необходимо вывести
    * информацию об этом пользователю и присвоить false полю clientConnected.
    * */
    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage(e.toString());
            clientConnected = false;
        }
    }

    /*
    * метод void run() должен создавать вспомогательный поток
    * SocketThread, ожидать пока тот установит соединение с сервером, а после этого
    * в цикле считывать сообщения с консоли и отправлять их серверу. Условием выхода
    * из цикла будет отключение клиента или ввод пользователем команды 'exit'.
    * */
    public void run() {

        SocketThread socketThread = getSocketThread();

        /*
        * 	Помечать созданный поток как daemon, это нужно для того, чтобы при выходе
        * 	из программы вспомогательный поток прервался автоматически.
        * */
        socketThread.setDaemon(true);
        socketThread.start();

        /*
        * Заставить текущий поток ожидать, пока он не получит нотификацию из другого
        * потока.
        * */
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Wait error");
            return;
        }

        /*
        * После того, как поток дождался нотификации, проверь значение
        * clientConnected. Если оно true – выведи "Соединение установлено. Для выхода
        * наберите команду 'exit'.". Если оно false – выведи "Произошла ошибка во время
        * работы клиента.".
        * */
        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        } else {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        }

        /*
        * Считывай сообщения с консоли пока клиент подключен. Если будет введена
        * команда 'exit', то выйди из цикла.
        * */
        String message;
        while (clientConnected) {

           /*
           * После каждого считывания, если метод shouldSentTextFromConsole()
           * возвращает true, отправь считанный текст с помощью метода  sendTextMessage().
            */
            if (!(message = ConsoleHelper.readString()).equalsIgnoreCase("exit")) {
                if (shouldSendTextFromConsole()) sendTextMessage(message);
            } else
                break;
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}

