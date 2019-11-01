import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Broker {
    // ���д洢��Ϣ���������
    private final static int MAX_SIZE = 200;

    // ������Ϣ���ݵ�����
    private static Map<String,ArrayBlockingQueue<String> > map = new HashMap<String,ArrayBlockingQueue<String> >();
    // ������Ϣ
    public static ArrayBlockingQueue<String> creatList(String key)
    {
//        ����key�Ƿ���ڴ���һ��list
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
                System.out.println("��������");
            }
            return 1;
        }
        return 0;
    }
    public static void produce(String msg,String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        if (mq.offer(msg)) {
            System.out.println("�յ���Ϣ��" + msg + " "+key+"δȡ��������" + mq.size());
        } else {
            System.out.println("��������");
        }
    }
    // ������Ϣ
    public static String consume(String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        String msg = mq.poll();
        if (msg != null) {
            // ���������������������Ϣ������ȡ��һ����Ϣ
            System.out.println("ȡ����Ϣ��" + msg + "δȡ��������" + mq.size());
        } else {
            System.out.println("����Ϊ��");
        }
        return msg;
    }

}
