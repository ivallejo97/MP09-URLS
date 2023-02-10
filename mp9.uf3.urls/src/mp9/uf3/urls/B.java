package mp9.uf3.urls;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class B {
    public static void escribirForm(URL url, String string, boolean bol) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
        if(bol){
            writer.write("entry.835030737="+string+"&entry.1616686619=Si");
        }else {
            writer.write("entry.835030737="+string+"&entry.1616686619=No");
        }
        writer.flush();
        writer.close();
        urlConnection.getInputStream();
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("INTRODUCE LA URL DEL FORMULARIO:");
        String link = sc.nextLine();
        System.out.println("INTRODUCE EL NOMBRE: ");
        String nom = sc.nextLine();
        System.out.println("Introduce si o no: ");
        String bo = sc.nextLine();
        boolean pe = bo.equals("si") | bo.equals("Si") | bo.equals("sI") | bo.equals("SI");
        B.escribirForm(new URL(link),nom,pe);
    }
}
