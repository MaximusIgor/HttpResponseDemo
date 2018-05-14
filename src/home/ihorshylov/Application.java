package home.ihorshylov;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class Application {
    static void getRawHttp(String saddr) throws IOException {
        InetAddress addr = InetAddress.getByName(saddr);
        Socket socket = new Socket(addr, 80);
        boolean autoflush = true;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
        // send an HTTP request to the web server
        out.println("GET / HTTP/1.1"); // строка запроса
        out.println("Host: " + saddr); // заголовки
        out.println("Connection: Close");
        out.println();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        // read the response
        boolean loop = true;
        StringBuilder sb = new StringBuilder(8096);
        while (loop) {
            if (in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
        }
        System.out.println(sb.toString());
        socket.close();
    }

    public static void main(String[] args) throws IOException {

        getRawHttp("wikipedia.org");
    }

    private static void getHtml(String saddr) {
        try {
            URL url = new URL(saddr);
            InputStream stream = url.openStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while((len = stream.read(buff)) > 0) {
                System.out.println(new String(buff, 0, len));
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
