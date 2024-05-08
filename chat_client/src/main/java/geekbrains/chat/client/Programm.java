package geekbrains.chat.client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Programm {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.printf("Enter your name: ");
            String name = scanner.nextLine();

            // Создадим сокет
            // Первый параметр - адрес машины, к которой хотим подключиться
            // Второй параметр - порт, на котором серверная часть слушает сообщения
            // по этому порту мы подключимся к серверу (порт сервера?)
            Socket socket = new Socket("localhost", 1400);
            Client client = new Client(socket, name);

//            При инициализации объекта типа Socket, клиент, которому тот принадлежит, объявляет в сети,
//            что хочет соединиться с сервером про определённому адресу и номеру порта.
//            Ниже представлены самые часто используемые методы класса Socket:

//            InetAddress getInetAddress() – возвращает объект содержащий данные о сокете.
//            В случае если сокет не подключен – null
              InetAddress inetAddress = socket.getInetAddress();
              System.out.println("InetAddress: " + inetAddress);

//            int getPort() – возвращает порт по которому происходит соединение с сервером

//            int getLocalPort() – возвращает порт к которому привязан сокет.
              System.out.println("LocalPort:" + socket.getLocalPort());

//            Дело в том, что «общаться» клиент и сервер могут по одному порту,
//            а порты, к которым они привязаны – могут быть совершенно другие

//            boolean isConnected() – возвращает true, если соединение установлено
//            void connect(SocketAddress адрес) – указывает новое соединение
//            boolean isClosed() – возвращает true, если сокет закрыт
//            boolean isBound() - возвращает true, если сокет действительно привязан к адресу

//            String getHostAddress() - возвращает строку, представляющую адрес хоста,
//            ассоциированного с объектом класса InetAddress.
              String remoteIp = inetAddress.getHostAddress();
              System.out.println("Remote IP: " + remoteIp);


              client.listenForMessage();
              client.sendMessage();



        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
