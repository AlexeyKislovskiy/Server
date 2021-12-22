package fertdt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket(InetAddress.getLocalHost(), 10034);
        OutputStream out = s.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        RequestMessage requestMessage = RequestMessage.createStartRequestMessage(34);
        byte[] data = requestMessage.getData();
        RequestMessage requestMessage1 = RequestMessage.createStopWaitingStartRequestMessage();
        byte[] data1 = requestMessage1.getData();
        int[] characters = new int[]{1, 2, 3}, skills = new int[]{1, 2, 3};
        RequestMessage requestMessage2 = RequestMessage.createCharacterAndSkillSelectMessage(characters, skills);
        byte[] data2 = requestMessage2.getData();
        RequestMessage requestMessage3 = RequestMessage.createExitRequestMessage();
        byte[] data3 = requestMessage3.getData();
        RequestMessage requestMessage4 = RequestMessage.createNormalSkillMessage(1, new int[0], new int[0], new int[0], new int[0], new int[]{1}, new int[0]);
        byte[] data4 = requestMessage4.getData();
//        RequestMessage requestMessage4 = RequestMessage.createNormalSkillMessage(0, new int[]{0}, new int[]{0}, new int[0], new int[0], new int[0], new int[0]);
//        byte[] data4 = requestMessage4.getData();
        read(s);
        while (true) {
            dos.write(data);
            dos.flush();
            Thread.sleep(3000);
            dos.write(data2);
            dos.flush();
            Thread.sleep(3000);
            dos.write(data4);
            dos.flush();
            Thread.sleep(3000);
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
            } catch (IOException e) {

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
