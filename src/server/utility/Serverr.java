package server.utility;

import common.Request;
import common.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serverr {
    private int port;
    private RequestManager requestManager;
    private DatagramSocket socket;
    private InetAddress address;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    private Request request;
    private Scanner scanner;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public Serverr(int port, RequestManager requestManager) {
        this.port = port;
        this.requestManager = requestManager;
    }

    public void run() {
        do_CTRL_C_Thread();
        scanner = new Scanner(System.in);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    if (!userCommand[0].equals("exit")) {
                        System.out.println("Сервер не может сам принимать такую команду!");
                        return;
                    }
                    if (userCommand[0].equals("exit")){
                        System.out.println("Сервер закончил работу");
                        System.exit(0);
                    }

                }
            } catch (Exception e) {}
        };
        Thread thread = new Thread(userInput);
        thread.start();

        try {
            System.out.println("Сервер запущен.");
            socket = new DatagramSocket(this.port);
            while (true) {
                try {
                    if (!cachedThreadPool.submit(() -> {
                        try {
                            request = getRequest();
                            System.out.println("Получена команда '" + request.getCommandName() + "'");
                            return true;
                        } catch (ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).get()) break;
                    new RequestProcessingThread(requestManager, request, address, port, socket).start();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("При чтении запроса произошла ошибка многопоточности!");
                }
            }
        } catch (SocketException e) {
            System.out.println("Произошла ошибка при работе с сокетом!");
        }
    }

    private Request getRequest() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket, getBuffer);
    }

    private Request deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    private void do_CTRL_C_Thread() {
        scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Завершаю программу.");
        }));
    }
}
