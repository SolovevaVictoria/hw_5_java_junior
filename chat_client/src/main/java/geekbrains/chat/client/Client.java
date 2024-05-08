package geekbrains.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public Client(Socket socket, String userName){
        this.socket = socket;
        this.name = userName;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
        }

        // будем слушать входящие сообщения от сервера:
        public void listenForMessage(){
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    while (socket.isConnected()) {
                        try {
                            message = bufferedReader.readLine();
                            System.out.println(message);
                        } catch (IOException e) {
                            closeEverything(socket, bufferedReader, bufferedWriter);
                        }
                    }
                }
            }).start();
        }

        // отправка сообщений:
        public void sendMessage() {
            try {
                bufferedWriter.write(name);
                bufferedWriter.newLine(); // переход на новую строку
                bufferedWriter.flush(); // все данные, подготовленные в потоке записи отправить на сервер немедленно

                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()) {
                    String message = scanner.nextLine();
                    bufferedWriter.write(name + ": " + message);
                    bufferedWriter.newLine(); // переход на новую строку
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }

        private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }


}
