package multicast;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class ClientMulticast {
    /* secretNum.Client afegit al grup multicast SrvVelocitats.java que representa un velocímetre */

    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    NetworkInterface netIf;
    InetSocketAddress group;

    HashMap<String, Integer> map;


    public ClientMulticast(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        //netIf = NetworkInterface.getByName("enp1s0");
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException{
        DatagramPacket packet;
        map = new HashMap<>();
        byte [] receivedData = new byte[24];

        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning){
            packet = new DatagramPacket(receivedData, receivedData.length);
            socket.setSoTimeout(5000);
            try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(), packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group,netIf);
        socket.close();
    }

    protected  boolean getData(byte[] data, int lenght) {
       String palabraAleatoria = new String(data,0,lenght);

        if (!map.containsKey(palabraAleatoria)) {
            map.put(palabraAleatoria, 1);
            System.out.println(palabraAleatoria +": "+map.get(palabraAleatoria));

        } else {
            map.put(palabraAleatoria, map.get(palabraAleatoria) + 1 );
            System.out.println(palabraAleatoria +": "+map.get(palabraAleatoria));
        }

        return  true;
    }

    public static void main(String[] args) throws IOException {
        ClientMulticast clientMulticast = new ClientMulticast(5557, "224.11.0.111");
        clientMulticast.runClient();
        System.out.println("Parat!");

    }
}
