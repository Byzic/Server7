package server.utility;

import common.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.RecursiveAction;

public class ResponseWriteAction extends RecursiveAction {
    private Response response;
    Socket clientSocket;
    public ResponseWriteAction(Response response, Socket clientSocket){
        this.response=response;
        this.clientSocket=clientSocket;

    }
    @Override
    public void compute(){
        try(ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream());){
            clientWriter.writeObject(response);
            clientWriter.flush();
        }  catch (IOException e) {
        System.out.println("Произошла ошибка при отправке данных на клиент!");

    }

    }
}
