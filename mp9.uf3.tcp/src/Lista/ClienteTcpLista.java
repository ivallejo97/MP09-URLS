package Lista;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ClienteTcpLista extends Thread{
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private boolean continueConnected;
    private Llista llista;

    private ClienteTcpLista(String hostname, int puerto, Llista lista) {
        try {
            socket = new Socket(InetAddress.getByName(hostname), puerto);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (UnknownHostException ex) {
            System.out.println("Error de conexión. No existe ningún equipo con esa IP: " + ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        continueConnected = true;
        this.llista=lista;
    }

    public void run() {
        while(continueConnected) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(llista);
                objectOutputStream.flush();
                this.llista=getRequest();
                break;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        close(socket);

    }
    private Llista getRequest() {
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            llista = (Llista) ois.readObject();
            System.out.println("Lista ordenada ->" + llista.getNumberList());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return llista;
    }


    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClienteTcpLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {


        System.out.println("Ip del servidor: ");
        Scanner scanner = new Scanner(System.in);
        String ipSrv = scanner.next();
        System.out.println("Nombre del jugador: ");
        String jugador = scanner.next();
        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            numeros.add( (int) (Math.random() * 30)+1);
        }
        Llista lista = new Llista(jugador,numeros);

        ClienteTcpLista clientTcp = new ClienteTcpLista(ipSrv,8091,lista);
        clientTcp.llista.setNom(jugador);
        clientTcp.start();
    }
}