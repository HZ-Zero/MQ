import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

/**
 * 访问消息队列的客户端
 */
public class MqClient {
    //生产消息
    public static int heartbeat(String key) throws Exception {
        //本地的的BrokerServer.SERVICE_PORT 创建SOCKET
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
            if(JDBC.keys.contains(key))
//       如果标识符在数据库中
            {
                try (
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                socket.getInputStream()));
//                读取服务器回写的数据 socket.getInputStream()
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
                //本地的的BrokerServer.SERVICE_PORT 创建SOCKET

                try (
                        PrintWriter out = new PrintWriter(socket.getOutputStream())
                ) {
//                System.out.println(message);
                    out.println("SEND:"+key+":"+message);
                    out.flush();
                    System.out.println("消息已经发送");
                }
            }
        }catch (ConnectException e)
        {
            System.out.println("服务器没开");
        }


    }
    //消费消息
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
                    //先向消息队列发送命令
                    out.println("CONSUME:"+key);
                    out.flush();
                    //再从消息队列获取一条消息
                    String message = in.readLine();
                    JDBC.Addrecorde(key,message);
                    System.out.println("取出"+message);
                    return message;
                }
            }
        }catch (ConnectException e)
        {
            System.out.println("服务器没开");
        }
        return null;
    }
    public static String registration() throws SQLException
    {
       String key = JDBC.registration();
        return key;
    }

}
