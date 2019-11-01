import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����������Ϣ��������
 */
public class BrokerServer implements Runnable {

    public static int SERVICE_PORT = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()))) {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                while (true) {
//                    System.out.println(in.readLine());
                    String str = in.readLine();
                    if (str == null) {
                        continue;
                    }
                    System.out.println("���յ���־���ݣ�" + str);
//                   ����ʱ���ַ����� send����consume��־ �ټ�key��Ӧ��list �磺SEND:KEY:CONTENT
                    String[] words=str.split(":");
                    if (words[0].equals("CONSUME")) { //CONSUME ��ʾҪ����һ����Ϣ
                        //����Ϣ����������һ����Ϣ
                        String message = Broker.consume(words[1]);
                        out.println(message);
                        out.flush();
                    } else if (words[0].equals("SEND")) {
                        //���ܵ����������SEND:�ַ��� ��ʾ������Ϣ�ŵ���Ϣ������
                        Broker.produce(words[2],words[1]);
                        System.out.println(words[2]+words[1]);
                    } else if (words[0].equals("HEARTBEAT"))
                    {
//                        Broker.produce(words[2],words[1]);
                        out.println(Broker.heartbeat());
                    }
                    else {
                        System.out.println("ԭʼ����:" + str + "û����ѭЭ��,���ṩ��ط���");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        while (true) {
            BrokerServer brokerServer = new BrokerServer(server.accept());
            new Thread(brokerServer).start();
            new Thread(brokerServer).start();
            new Thread(brokerServer).start();


        }
    }
}
