import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

/**
 * ������Ϣ���еĿͻ���
 */
public class MqClient {
    //������Ϣ
    public static int heartbeat(String key) throws Exception {
        //���صĵ�BrokerServer.SERVICE_PORT ����SOCKET
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
            if(JDBC.keys.contains(key))
//       �����ʶ�������ݿ���
            {
                try (
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                socket.getInputStream()));
//                ��ȡ��������д������ socket.getInputStream()
                        PrintWriter out = new PrintWriter(socket.getOutputStream())

                ) {
                    out.println("HEARTBEAT:"+key);
                    out.flush();
                }
                return 1;
            }
        }
        catch (ConnectException e)
        {
            return -1;
        }
        return 0;
    }
    public static void produce(String key,String message) throws Exception {
        try{
            Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
            if(JDBC.keys.contains(key)){
                //���صĵ�BrokerServer.SERVICE_PORT ����SOCKET

                try (
                        PrintWriter out = new PrintWriter(socket.getOutputStream())
                ) {
//                System.out.println(message);
                    out.println("SEND:"+key+":"+message);
                    out.flush();
                    System.out.println("��Ϣ�Ѿ�����");
                }
            }
        }catch (ConnectException e)
        {
            System.out.println("������û��");
        }


    }
    //������Ϣ
    public static String consume(String key) throws Exception {
        try
        {
            Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
            if(JDBC.keys.contains(key)){
                try (
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream())
                ) {
                    //������Ϣ���з�������
                    out.println("CONSUME:"+key);
                    out.flush();
                    //�ٴ���Ϣ���л�ȡһ����Ϣ
                    String message = in.readLine();
                    JDBC.Addrecorde(key,message);
                    System.out.println("ȡ��"+message);
                    return message;
                }
            }
        }catch (ConnectException e)
        {
            System.out.println("������û��");
        }
        return null;
    }
    public static String registration() throws SQLException
    {
       String key = JDBC.registration();
        return key;
    }

}
