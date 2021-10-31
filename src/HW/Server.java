package HW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {

    private static final long ERROR_CODE = 4;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(37554);
        String text;

        while (true) {

            try (Socket socket = serverSocket.accept();
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    while ((text = input.readLine()) != null) {

                        // Получаем ответ от вычислительной функции
                        long answer = getNFibonacciMember(Integer.parseInt(text));

                        // Проверка на error code
                        if (answer != ERROR_CODE) {
                            output.println(answer);
                        }
                        else {
                            output.println("Ошибка ввода, повторите попытку");
                        }

                        // Проверка на end
                        if (text.equals("end")) {
                            break;
                        }
                    }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static long getNFibonacciMember(int member) {
        List<Long> sequence = new LinkedList<>();
        sequence.add(1l);
        sequence.add(1l);

        if (member > 2) {
            // Сначала заполним последовательность, зная что нулевой и первый члены - единицы
            for (int i = 2; i < member; i++) {
                long temp = sequence.get(i-1) + sequence.get(i-2);
                sequence.add(temp);
            }
            return sequence.get(member - 1);
        }
        else if (member == 1 || member == 2) {
            return 1;
        }
        else {
            return ERROR_CODE;
        }
    }
}