package fertdt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket(InetAddress.getLocalHost(), 10034);
        OutputStream out = s.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        int[] x = new int[100], y = new int[100];
        for (int i = 0; i < 100; i++) {
            x[i] = i / 10;
            y[i] = i % 10;
        }
        Map<Integer, RequestMessage> map = Map.ofEntries(
                Map.entry(1, RequestMessage.createStartRequestMessage(34)),
                Map.entry(2, RequestMessage.createStopWaitingStartRequestMessage()),
                Map.entry(3, RequestMessage.createCharacterAndSkillSelectMessage(new int[]{1, 2, 3}, new int[]{1, 2, 3})),
                Map.entry(4, RequestMessage.createNormalSkillMessage(0, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0])),
                Map.entry(5, RequestMessage.createNormalSkillMessage(1, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0])),
                Map.entry(6, RequestMessage.createNormalSkillMessage(2, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0])),
                Map.entry(7, RequestMessage.createNormalMoveMessage(0, x, y)),
                Map.entry(8, RequestMessage.createNormalMoveMessage(1, x, y)),
                Map.entry(9, RequestMessage.createNormalMoveMessage(2, x, y)),
                Map.entry(10, RequestMessage.createSpecialSkillMessage(0, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0])),
                Map.entry(11, RequestMessage.createSpecialSkillMessage(1, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0])),
                Map.entry(12, RequestMessage.createSpecialSkillMessage(2, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0])),
                Map.entry(13, RequestMessage.createExitRequestMessage())
        );
        read(s);
        while (true) {
            int n = sc.nextInt();
            dos.write(map.get(n).getData());
            dos.flush();
        }

    }

    public static void read(Socket s) {
        Runnable runnable = () -> {
            try {
                InputStream is = s.getInputStream();
                List<Byte> list = new ArrayList<>();
                while (true) {
                    int n = is.read();
                    if (n != -1) {
                        list.add((byte) n);
                        try {
                            ResponseMessage responseMessage = ResponseMessage.readMessage(list);
                            System.out.println(responseMessage);
                            list.clear();
                        } catch (MessageReadingException ignored) {
                        }
                    }
                }
            } catch (IOException ignored) {
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
