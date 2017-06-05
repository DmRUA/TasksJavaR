package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DmRG on 04.06.2017.
 */
public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        int port = ConsoleHelper.readInt(); // Запрашивать порт сервера, используя ConsoleHelper


        try(ServerSocket serverSocket = new ServerSocket(port)) // Сервер создает серверное сокетное соединение try-with=resours
        {
            ConsoleHelper.writeMessage("Сервер запущен.");
            for (int i = 0;;i++)                                //  В цикле ожидает, когда какой-то клиент подключится к сокету
            {
                // Создает новый поток обработчик Handler,
                // в котором будет происходить обмен сообщениями с клиентом.

                Handler handler = new Handler(serverSocket.accept());
                handler.start();
            }
        }
        catch (Exception e)
        {
            ConsoleHelper.writeMessage("Ошибка сокета.");
        }
    }

    /*
    *
    * Статический метод void sendBroadcastMessage(Message message), который должен
    * отправлять сообщение  message по всем соединениям из connectionMap.
    * */

    public static void sendBroadcastMessage(Message message)
    {
        try
        {
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                pair.getValue().send(message);
            }
        }
        catch (IOException e)
        {
            ConsoleHelper.writeMessage("Не могу отправить сообщение.");
        }
    }

    /*
    * Класс Handler должен реализовывать протокол общения с клиентом
    * */

    private static class Handler extends Thread
    {
        private Socket socket;

        public Handler(Socket socket)
        {
            this.socket = socket;
        }

        /*
        * главный метод класса Handler, который будет вызывать все
        * вспомогательные методы, написанные ранее
        * */
        public void run()
        {
            /*
            * Выводить сообщение, что установлено новое соединение с удаленным
            * адресом, который можно получить с помощью метода getRemoteSocketAddress
            * */
            ConsoleHelper.writeMessage("Established new connection with remote address " + socket.getRemoteSocketAddress());
            String clientName = null;

            /*
            * Создавать Connection, используя поле Socket
            * */
            try (Connection connection = new Connection(socket))
            {
                ConsoleHelper.writeMessage("Connection with port " + connection.getRemoteSocketAddress());

                /*
                * Вызывать метод, реализующий рукопожатие с клиентом, сохраняя имя нового
                * клиента
                */
                clientName = serverHandshake(connection);

                /*
                * Рассылать всем участникам чата информацию об имени присоединившегося
                * участника (сообщение с типом USER_ADDED).
                * */
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, clientName));

                /*
                * Сообщать новому участнику о существующих участниках
                */
                sendListOfUsers(connection, clientName);

                /*
                * главный цикл обработки сообщений сервером
                * */
                serverMainLoop(connection, clientName);

            }
            catch (IOException | ClassNotFoundException e)
            {
                ConsoleHelper.writeMessage("An error occurred while communicating with the remote address");
            }
            finally
            {

                /*
                * После того как все исключения обработаны, если serverHandshake(connection) отработал и возвратил
                * нам имя, мы должны удалить запись для этого имени из connectionMap и разослать
                * всем остальным участникам сообщение с типом USER_REMOVED и сохраненным
                * именем.
                * */

                if (clientName != null) {
                    connectionMap.remove(clientName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, clientName));

                    /*
                    * сообщение,
                    * информирующее что соединение с удаленным адресом закрыто.
                    * */
                    ConsoleHelper.writeMessage(String.format("Connection with remote address (%s) is closed.", socket.getRemoteSocketAddress()));
                }
            }


        }

        /*
        * Этап первый – это этап рукопожатия (знакомства сервера с клиентом). Реализуем его с
          помощью приватного метода String serverHandshake(Connection connection) throws IOException,
          ClassNotFoundException. Метод в качестве параметра принимает соединение connection, а
          возвращает имя нового клиента.
        * */

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            String name = null; // имя пользователя-клиента
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));  // Сформировать и отправить команду запроса имени пользователя
                Message message = connection.receive(); // Получить ответ клиента
                if (message.getType() == MessageType.USER_NAME) { // Проверить, что получена команда с именем пользователя

                    /*
                    * Достать из ответа имя, проверить, что оно не пустое и пользователь с таким именем
                    * еще не подключен (используй connectionMap)
                    * */

                    name = message.getData();
                    if (!name.isEmpty() && !connectionMap.containsKey(name)) {

                        // Добавить нового пользователя и соединение с ним в connectionMap
                        connectionMap.put(name, connection);

                        /*
                        * Отправить клиенту команду информирующую, что его имя принято
                        * Если какая-то проверка не прошла, заново запросить имя клиента
                        * */
                        connection.send(new Message(MessageType.NAME_ACCEPTED));

                        /*
                        * Вернуть принятое имя в качестве возвращаемого значения
                        * */
                        return name;
                    }
                }
            }
        }

        /*
        * отправка клиенту (новому участнику) информации об
        * остальных клиентах (участниках) чата.
        *
        * connection – соединение с участником, которому будем слать
        * информацию, а userName – его имя.
        */
        private void sendListOfUsers(Connection connection, String userName)throws IOException
        {

            /*
            * Пройтись по connectionMap
            * У каждого элемента получить имя клиента, сформировать команду с типом
            * USER_ADDED и полученным именем
            * Отправить сформированную команду через connection
            * Команду с типом USER_ADDED и именем равным userName отправлять не нужно,
            * пользователь и так имеет информацию о себе
            */
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet())
            {
                String name = pair.getKey();
                if (!name.equals(userName))
                {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        /*
        * главный цикл обработки сообщений сервером.
        *
        *
        * */

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true)
            {
                /*
                *  Принимать сообщение клиента
                *
                * */
                Message message = connection.receive();

                /* Если принятое сообщение – это текст (тип TEXT), то формировать новое
                 * текстовое сообщение путем конкатенации: имени клиента, двоеточия, пробела и
                 * текста сообщения.
                 */
                if (message.getType() == MessageType.TEXT)
                {
                    String message1 = userName + ": " + message.getData();

                    /*
                    * Отправлять сформированное сообщение всем клиентам с помощью метода
                    * sendBroadcastMessage.
                    * */
                    Message message2 = new Message(MessageType.TEXT, message1);
                    sendBroadcastMessage(message2);
                }
                else
                {
                    ConsoleHelper.writeMessage("Error.");
                }
            }
        }

    }
}
