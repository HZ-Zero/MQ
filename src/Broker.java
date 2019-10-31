import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Broker {
    // 队列存储消息的最大数量
    private final static int MAX_SIZE = 200;

    // 保存消息数据的容器
//    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);
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
    public static void deleteList(String key)
    {
//        根据key是否存在创建一个list
        if(map.containsKey(key))
        {
//            ArrayBlockingQueue<String> mq = new ArrayBlockingQueue<String>(MAX_SIZE);
            map.remove(key);

        }
//        return map.get(key);
    }
    public static int heartbeat() {
        return 1;
    }
    public static void produce(String msg,String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        if (mq.offer(msg)) {
            System.out.println("成功向消息处理中心投递消息：" + msg + " "+key+"当前暂存的消息数量是：" + mq.size());
        } else {
            System.out.println("消息处理中心内暂存的消息达到最大负荷，不能继续放入消息！");
        }
        System.out.println("=======================");
    }

    // 消费消息
    public static String consume(String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        String msg = mq.poll();
        if (msg != null) {
            // 消费条件满足情况，从消息容器中取出一条消息
            System.out.println("已经消费消息：" + msg + "，当前暂存的消息数量是：" + mq.size());
        } else {
            System.out.println("消息处理中心内没有消息可供消费！");
        }
        System.out.println("=======================");

        return msg;
    }

}
