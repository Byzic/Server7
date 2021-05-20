package server.utility;

import common.Request;
import common.Response;
import common.ResponseCode;
import common.User;


import java.net.Socket;
import java.util.concurrent.ForkJoinPool;

public class RequestProcessingThread extends Thread {
    Request request;
    private RequestManager requestManager;
    private Response response;
    Socket clientSocket;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();


    public RequestProcessingThread(RequestManager requestManager, Request request, Socket clientSocket){
        this.requestManager=requestManager;
        this.request=request;
        this.clientSocket=clientSocket;

    }
    @Override
    public void run() {
        response= requestManager.manage(request);
        forkJoinPool.invoke(new ResponseWriteAction(response,clientSocket));





    }

}
