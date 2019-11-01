import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Broker {
    // 队列存储消息的最大数量
    private final static int MAX_SIZE = 200;

    // 保存消息数据的容器
    private static Map<String,ArrayBlockingQueue<String> > map = new HashMap<String,ArrayBlockingQueue<String> >();
    // 生产消息
    public static ArrayBlockingQueue<String> creatList(String key)
    {
//        根据key是否存在创建一个list
        if(!map.containsKey(key))
        {
            ArrayBlockingQueue<String> mq = new ArrayBlockingQueue<String>(MAX_SIZE);
            map.put(key,mq);

        }
        return map.get(key);
    }
    public static void deleteList(String key) throws SQLException
    {
        if(JDBC.keys.contains(key))
        {
//            ArrayBlockingQueue<String> mq = new ArrayBlockingQueue<String>(MAX_SIZE);
            map.remove(key);

        }
    }
    public static int heartbeat(String key) {
        if(map.containsKey(key))
        {
            for (String k: map.keySet()
                 ) {
                System.out.println("发送心跳");
            }
            return 1;
        }
        return 0;
    }
    public static void produce(String msg,String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        if (mq.offer(msg)) {
            System.out.println("收到消息：" + msg + " "+key+"未取出数量：" + mq.size());
        } else {
            System.out.println("容量已满");
        }
    }
    // 消费消息
    public static String consume(String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        String msg = mq.poll();
        if (msg != null) {
            // 消费条件满足情况，从消息容器中取出一条消息
            System.out.println("取出消息：" + msg + "未取出数量：" + mq.size());
        } else {
            System.out.println("队列为空");
        }
        return msg;
    }

}
