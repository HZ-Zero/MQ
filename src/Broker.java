import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Broker {
    // ���д洢��Ϣ���������
    private final static int MAX_SIZE = 200;

    // ������Ϣ���ݵ�����
//    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);
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
    public static void deleteList(String key)
    {
//        ����key�Ƿ���ڴ���һ��list
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
            System.out.println("�ɹ�����Ϣ��������Ͷ����Ϣ��" + msg + " "+key+"��ǰ�ݴ����Ϣ�����ǣ�" + mq.size());
        } else {
            System.out.println("��Ϣ�����������ݴ����Ϣ�ﵽ��󸺺ɣ����ܼ���������Ϣ��");
        }
        System.out.println("=======================");
    }

    // ������Ϣ
    public static String consume(String key) {
        ArrayBlockingQueue<String> mq=creatList(key);
        String msg = mq.poll();
        if (msg != null) {
            // ���������������������Ϣ������ȡ��һ����Ϣ
            System.out.println("�Ѿ�������Ϣ��" + msg + "����ǰ�ݴ����Ϣ�����ǣ�" + mq.size());
        } else {
            System.out.println("��Ϣ����������û����Ϣ�ɹ����ѣ�");
        }
        System.out.println("=======================");

        return msg;
    }

}
