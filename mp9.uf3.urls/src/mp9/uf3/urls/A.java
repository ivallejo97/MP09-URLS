package mp9.uf3.urls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class A {

    private static void printContent(URL url,String etiqueta){
        InputStream in;

        try {
            in = url.openStream();
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader bf = new BufferedReader(inr);
            String linea = bf.readLine();
            while(linea!=null){
                if(linea.contains(etiqueta))System.out.println(linea);
            linea=bf.readLine();}
        } catch (IOException ex) {
            Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            String url = args[0];
            String etiqueta = args[1];
            A.printContent(new URL(url),etiqueta);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
