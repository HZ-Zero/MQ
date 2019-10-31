import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 用于启动消息处理中心
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
                    System.out.println("接收到标志数据：" + str);
//                   发送时给字符串加 send或者consume标志 再加key对应的list 如：SEND:KEY:CONTENT
                    String[] words=str.split(":");
                    if (words[0].equals("CONSUME")) { //CONSUME 表示要消费一条消息
                        //从消息队列中消费一条消息
                        String message = Broker.consume(words[1]);
                        out.println(message);
                        out.flush();
                    } else if (words[0].equals("SEND")) {
                        //接受到的请求包含SEND:字符串 表示生产消息放到消息队列中
                        Broker.produce(words[2],words[1]);
                        System.out.println(words[2]+words[1]);
                    } else if (words[0].equals("HEARTBEAT"))
                    {
//                        Broker.produce(words[2],words[1]);
                        out.println(Broker.heartbeat());
                    }
                    else {
                        System.out.println("原始数据:" + str + "没有遵循协议,不提供相关服务");
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
