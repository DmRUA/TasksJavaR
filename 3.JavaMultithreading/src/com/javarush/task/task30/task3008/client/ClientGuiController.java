package com.javarush.task.task30.task3008.client;

/**
 * Created by DmRG on 04.06.2017.
 */
public class ClientGuiController extends Client {
    /*
   *  поле, отвечающее за модель ClientGuiModel model.
   * */
    private ClientGuiModel model = new ClientGuiModel();

    /*
    * поле, отвечающее за представление ClientGuiView view.
    * */
    private ClientGuiView view = new ClientGuiView(this);

    /*
    * внутренний класс GuiSocketThread унаследованный от SocketThread.
    * */
    public class GuiSocketThread extends SocketThread {

        /*
        * void processIncomingMessage(String message) – должен устанавливать новое
        * сообщение у модели и вызывать обновление вывода сообщений у
        * представления.
        * */
        @Override
        protected void processIncomingMessage(String message) {
            model.setNewMessage(message);
            view.refreshMessages();
        }

        /*
        * void informAboutAddingNewUser(String userName) – должен добавлять нового
        * пользователя в модель и вызывать обновление вывода пользователей у
        * отображения.
        * */
        @Override
        protected void informAboutAddingNewUser(String userName) {
            model.addUser(userName);
            view.refreshUsers();
        }

        /*
        * void informAboutDeletingNewUser(String userName) – должен удалять
        * пользователя из модели и вызывать обновление вывода пользователей у
        * отображения.
        * */
        @Override
        protected void informAboutDeletingNewUser(String userName) {
            model.deleteUser(userName);
            view.refreshUsers();
        }

        /*
        * void notifyConnectionStatusChanged(boolean clientConnected) – должен вызывать
        * аналогичный метод у представления.
        * */
        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected);
        }
    }

    /*
    * SocketThread getSocketThread() – должен создавать и возвращать объект типа
    * GuiSocketThread.
    * */
    @Override
    protected SocketThread getSocketThread() {
        return new GuiSocketThread();
    }

    /*
    * void run() – должен получать объект SocketThread через метод getSocketThread()
    * и вызывать у него метод run().
    * */
    @Override
    public void run() {
        getSocketThread().run();
    }

    /*
    * 	getServerAddress(), getServerPort(),getUserName(). Они должны вызывать
    * 	одноименные методы из представления (view).
    * */

    @Override
    protected String getServerAddress() {
        return view.getServerAddress();
    }

    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }

    @Override
    protected String getUserName() {
        return view.getUserName();
    }

    /*
    * метод ClientGuiModel getModel(), который должен возвращать модель.
    * */
    public ClientGuiModel getModel() {
        return model;
    }

    public static void main(String[] args) {
        ClientGuiController clientGuiController = new ClientGuiController();
        clientGuiController.run();
    }
}