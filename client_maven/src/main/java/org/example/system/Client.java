package org.example.system;

import org.example.exceptions.RootException;
import org.example.recources.MusicBand;
import org.example.recources.Request;
import org.example.recources.Response;
import org.example.recources.generators.BandGenerator;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    private InetSocketAddress serverAddress;

    public Client(String address, int port) throws IOException {
        this.socket = new DatagramSocket();
        this.serverAddress = new InetSocketAddress(address, port);
        socket.connect(serverAddress);
    }

    public void start() {
        System.out.println("Добро пожаловать!");
        Scanner sc = new Scanner(System.in);
        while (true){
            try {
                String input = sc.nextLine();
                MusicBand musicBand = null;
                if (input.equals("add") || input.contains("update_by_id") || input.contains("add_if_max")){
                    musicBand = BandGenerator.generateBand();
                    sendRequest(new Request(input, musicBand));
                    Thread.sleep(200);
                    Response response = getResponse();
                    System.out.println(response.getMessage());
                } else if (input.contains("execute_script")){
                    ScriptExecutor.execute(input, this);
                } else{
                    sendRequest(new Request(input, musicBand));
                    Thread.sleep(200);
                    Response response = getResponse();
                    System.out.println(response.getMessage());
                }
            } catch (IOException e){
                System.out.println("Server is not available");
            } catch (ClassNotFoundException e){
                System.out.println("Something wrong with Response");
            } catch (InterruptedException ignored){

            } catch (RootException e) {
                System.out.println("You do not have enough rules");
            }

        }
    }

    public void sendRequest(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);

        DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), serverAddress);
        socket.send(sendPacket);
    }

    public Response getResponse() throws ClassNotFoundException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(50000);
        DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.array().length);
        socket.receive(packet);
        buffer.flip();
        ByteArrayInputStream bi = new ByteArrayInputStream(packet.getData());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (Response) oi.readObject();
    }

}
