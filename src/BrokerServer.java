import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于启动消息处理中心
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
//                    System.out.println("接收到标志数据：" + str);
//                   发送时给字符串加 send或者consume标志 再加key对应的list 如：SEND:KEY:CONTENT
                    String[] words=str.split(":");
                    if (words[0].equals("CONSUME")) { //CONSUME 表示要消费一条消息
                        //从消息队列中消费一条消息
                        String message = Broker.consume(words[1]);
                        out.println(message);
                        out.flush();
//                        System.out.println("取出"+message);
                    } else if (words[0].equals("SEND")) {
                        Broker.produce(words[2],words[1]);
//                        System.out.println(words[2]+words[1]);
                    } else if (words[0].equals("HEARTBEAT"))
                    {
                        out.println(Broker.heartbeat(words[1]));
//                        System.out.println("心跳");
                    }
                    else {
                        System.out.println("原始数据:" + str + "没有遵循协议,不提供相关服务");
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
            System.out.println("服务端已经启动...");
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
