package org.example.system;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.ReadFileException;
import org.example.exceptions.RootException;
import org.example.filelogic.ReaderXML;
import org.example.managers.CommandManager;
import org.example.recources.Request;
import org.example.recources.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static org.example.system.Main.LOGGER;

public class Server {
    private int port;
    private DatagramChannel datagramChannel;
    private static String dataPath;

    public Server(int serverPort, String path) throws IOException, RootException, ReadFileException {
        dataPath = path;
        ReaderXML.read(path);

        port = serverPort;
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(port));
        new CommandManager();
    }

    public void start(){
        while (true){
            try{
                ByteBuffer buffer = ByteBuffer.allocate(50000);
                InetSocketAddress clientAddress = (InetSocketAddress) datagramChannel.receive(buffer);
                buffer.flip();
                LOGGER.info("Получен новый запрос " + clientAddress);

                Request request = getRequest(buffer);
                Response response = execute(request);
                sendResponse(response, clientAddress);

            } catch (Exception e){
                LOGGER.warn(e.getMessage());
            }
        }
    }

    public Request getRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (Request) oi.readObject();
    }

    public Response execute(Request request) throws NoCommandException {
        return new Response(CommandManager.startExecuting(request));
    }

    public void sendResponse(Response response, InetSocketAddress clientAddress) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        datagramChannel.send(buffer, clientAddress);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static void setDataPath(String dataPath) {
        Server.dataPath = dataPath;
    }
}
