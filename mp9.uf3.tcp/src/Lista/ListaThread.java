package Lista;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ListaThread implements Runnable{
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private boolean continueConnected;
    private Llista lista;
    public ListaThread(Socket clientSocket)throws IOException {
        this.clientSocket = clientSocket;
        continueConnected = false;
        //Enllacem els canals de comunicació
        in = clientSocket.getInputStream();
        out = clientSocket.getOutputStream();
        System.out.println("Establecidos los canales de entrada y salida con el cliente");
    }

    @Override
    public void run() {
        try {
            while(!continueConnected) {
                ObjectInputStream ois = new ObjectInputStream(in);
                try {
                    lista = (Llista) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("Jugador: " + lista.getNom());
                System.out.println("Lista de numeros desordenada: " + "->" + lista.getNumberList());
                Set<Integer> hashSet = new HashSet<>();
                hashSet.addAll(lista.getNumberList());
                lista.setNumberList(hashSet.stream().toList());
                continueConnected = true;
            }
        }catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(lista);
            oos.flush();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
