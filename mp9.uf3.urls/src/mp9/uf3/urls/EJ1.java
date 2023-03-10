package mp9.uf3.urls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EJ1 {

    private static void printContent(URL url, String etiqueta){
        InputStream in;
        BufferedReader br;

        try {
            in = url.openStream();
            InputStreamReader inr = new InputStreamReader(in);

            br = new BufferedReader(inr);

            String line = br.readLine();

            while (line != null){
                if (line.contains(etiqueta)){
                    System.out.println(line);
                }
                line = br.readLine();
            }


            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(EJ1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args){

        String url = args[0];
        String etiqueta = args[1];


        try {
            EJ1.printContent(new URL(url), etiqueta);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}