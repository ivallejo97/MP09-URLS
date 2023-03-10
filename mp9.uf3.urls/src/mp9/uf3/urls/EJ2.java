package mp9.uf3.urls;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class EJ2 {
    public static void escribirForm(URL url, String nombre, boolean si) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());

        if(si){
            writer.write("entry.835030737="+nombre+"&entry.1616686619=Si");
        }else {
            writer.write("entry.835030737="+nombre+"&entry.1616686619=No");
        }

        writer.flush();
        writer.close();
        con.getInputStream();
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String url = "https://docs.google.com/forms/d/e/1FAIpQLSdV5QvhChK0fBpAMo5pN7sIvktqwHGu1vdoWJFvBguCeMvYUw/viewform";
        System.out.println("Nombre: ");
        String nom = sc.nextLine();
        System.out.println("Escribe si o no: ");
        String respuesta = sc.nextLine();
        boolean si = respuesta.equals("Si") || respuesta.equals("si") || respuesta.equals("SI");

        try {
            EJ2.escribirForm(new URL(url),nom,si);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


