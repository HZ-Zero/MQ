import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����������Ϣ��������
 */
public class BrokerServer implements Runnable {

    public static int SERVICE_PORT = 9999;

    private final Socket socket;
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
//        System.out.println(Thread.currentThread().getName());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()))) {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                    String str = in.readLine();
                    if (str == null) {
                        return;
                    }
//                    System.out.println("���յ���־���ݣ�" + str);
//                   ����ʱ���ַ����� send����consume��־ �ټ�key��Ӧ��list �磺SEND:KEY:CONTENT
                    String[] words=str.split(":");
                    if (words[0].equals("CONSUME")) { //CONSUME ��ʾҪ����һ����Ϣ
                        //����Ϣ����������һ����Ϣ
                        String message = Broker.consume(words[1]);
                        out.println(message);
                        out.flush();
//                        System.out.println("ȡ��"+message);
                    } else if (words[0].equals("SEND")) {
                        Broker.produce(words[2],words[1]);
//                        System.out.println(words[2]+words[1]);
                    } else if (words[0].equals("HEARTBEAT"))
                    {
                        out.println(Broker.heartbeat(words[1]));
//                        System.out.println("����");
                    }
                    else {
                        System.out.println("ԭʼ����:" + str + "û����ѭЭ��,���ṩ��ط���");
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }



    public static void main(String[] args) throws Exception {
//        ServerSocket server = new ServerSocket(SERVICE_PORT).accept();
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(SERVICE_PORT);
            System.out.println("������Ѿ�����...");
            while (true) {
                executor.execute(new BrokerServer(ss.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
