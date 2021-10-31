package HW;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 37554);
        String message;

        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Enter the number: ");
                message = scanner.nextLine();
                output.println(message);

                if ("end".equals(message)) {
                    break;
                }

                System.out.println("SERVER ANSWER: " + input.readLine());
            }
        }
    }
}
