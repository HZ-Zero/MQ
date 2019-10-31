import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * ������Ϣ���еĿͻ���
 */
public class MqClient {
//    String  static key ="asdad";
    //������Ϣ
    public static int heartbeat() throws Exception {
        //���صĵ�BrokerServer.SERVICE_PORT ����SOCKET
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())

        ) {
            out.println("HEARTBEAT");
            out.flush();
        }
        return 1;
    }
    public static void produce(String message) throws Exception {
        //���صĵ�BrokerServer.SERVICE_PORT ����SOCKET
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            System.out.println(message);
            out.println(message);
            out.flush();
        }
    }

    //������Ϣ
    public static String consume(String key) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            //������Ϣ���з�������
            out.println("CONSUME:"+key);
            out.flush();

            //�ٴ���Ϣ���л�ȡһ����Ϣ
//            while(in.)in
            String message = in.readLine();

            return message;
        }
    }

}
