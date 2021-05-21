package server.utility;

import common.Request;
import common.Response;
import common.ResponseCode;
import common.User;


import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ForkJoinPool;

public class RequestProcessingThread extends Thread{
    private RequestManager requestManager;
    private Request request;
    private InetAddress address;
    private int port;
    private DatagramSocket socket;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    public RequestProcessingThread(RequestManager requestManager, Request request, InetAddress address, int port, DatagramSocket socket) {
        this.requestManager = requestManager;
        this.request = request;
        this.address = address;
        this.port = port;
        this.socket = socket;
    }
    @Override
    public void run() {
        Response response= requestManager.manage(request);
        forkJoinPool.invoke(new ResponseWriteAction(response, address, port, socket));





    }

}
