package HW2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 25781);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {

            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String message;
            while (true) {
                System.out.print("Enter message for server: ");
                message = scanner.nextLine();
                if ("end".equals(message)) {
                    break;
                }

                socketChannel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(2000);

                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();

            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            socketChannel.close();
        }
    }
}