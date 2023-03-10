import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Timer;
import java.util.TimerTask;


public class ServidorMulticast {

    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    static String[] palabras;

    public int randomNum(){
        int palabraAleatoria = (int)(Math.random() * 7);
        return palabraAleatoria;
    }

    public ServidorMulticast(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = palabras[randomNum()].getBytes();
            packet = new DatagramPacket(sendingData,sendingData.length,multicastIP,port);
            socket.send(packet);

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }


        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        //Canvieu la X.X per un número per formar un IP.
        //Que no sigui la mateixa que la d'un altre company
        palabras = new String[]{"Coche", "Moto", "Quad", "Avion", "Barco", "Avioneta", "Lancha"};


        ServidorMulticast servidorPalabras = new ServidorMulticast(5557, "224.11.0.111");
        servidorPalabras.runServer();
        System.out.println("Parat!");

    }

}
