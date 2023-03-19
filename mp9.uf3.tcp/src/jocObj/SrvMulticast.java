package jocObj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SrvMulticast {

    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    Tauler tauler;

    public SrvMulticast(int portValue, String strIp, Tauler tauler1) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        tauler = tauler1;
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        System.out.println("MM");


        while(continueRunning){

            // Crear un objeto ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Crear un objeto ObjectOutputStream a partir del ByteArrayOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            // Escribir el objeto en el ObjectOutputStream
            oos.writeObject(tauler);

            // Convertir el ByteArrayOutputStream en un array de bytes
            sendingData = baos.toByteArray();

            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }


        }
        socket.close();
    }
}
