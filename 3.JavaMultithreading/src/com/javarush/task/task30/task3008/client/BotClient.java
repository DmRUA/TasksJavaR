package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DmRG on 04.06.2017.
 */
public class BotClient extends Client {
    /*
   * внутренний класс BotSocketThread унаследованный от SocketThread.
   * */
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {

            /*
            * С помощью метода sendTextMessage() отправь сообщение с текстом
            * "Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды."
            * */
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {

            // Вывести в консоль текст полученного сообщения message
            ConsoleHelper.writeMessage(message);

            // Получить из message имя отправителя и текст сообщения. Они разделены ": "
            String senderName = "";
            String senderMessageText;

            if (message.contains(": ")) {
                senderName = message.substring(0, message.indexOf(": "));
                senderMessageText = message.substring(message.indexOf(": ") + 2);
            }
            else {
                senderMessageText = message;
            }


            SimpleDateFormat format = null;
            // Отправить ответ в зависимости от текста принятого сообщения. Если текст сообщения:
            if ("дата".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("d.MM.YYYY");
            }
            else if ("день".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("d");
            }
            else if ("месяц".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("MMMM");
            }
            else if ("год".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("YYYY");
            }
            else if ("время".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("H:mm:ss");
            }
            else if ("час".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("H");
            }
            else if ("минуты".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("m");
            }
            else if ("секунды".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("s");
            }

            if (format != null)
            {
                /*
                * Для получения текущей даты необходимо использовать класс Calendar и метод
                * getTime().
                *
                * Ответ должен содержать имя клиента, который прислал запрос и ожидает ответ,
                * например, если Боб отправил запрос "время", мы должны отправить ответ
                * "Информация для Боб: 12:30:47".
                * */
                sendTextMessage("Информация для " + senderName + ": " + format.format(Calendar.getInstance().getTime()));
            }

        }
    }


    /*
    * getUserName(), метод должен генерировать новое имя бота, например:
    * date_bot_XX, где XX – любое число от 0 до 99. Этот метод должен возвращать
    * каждый раз новое значение, на случай, если на сервере захотят
    * зарегистрироваться несколько ботов, у них должны быть разные имена.
    * */
    @Override
    protected String getUserName() {
        return "date_bot_" + (int)(Math.random() * 100);
    }

    /*
    * 	shouldSentTextFromConsole(). Он должен всегда возвращать false. Мы не хотим,
    * 	чтобы бот отправлял текст введенный в консоль.
    * */
    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    /*
    * getSocketThread(). Он должен создавать и возвращать объект класса
    * BotSocketThread.
    * */
    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public static void main(String[] args) {
        new BotClient().run();
    }
}
