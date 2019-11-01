//import Pro

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testclass{

    private ExecutorService rs;
    @Before
    public void setExe()
    {
        rs = Executors.newFixedThreadPool(6);
    }

    @Test
    public void  Ser_Reg() throws Exception
    {
        MqClient client = new MqClient();
        String key =client.registration();
        System.out.println(key);
//      8f8288c8639c4332985dc50f37e509e7
    }
//  服务注册方法 返回 key作为标识符
    @Test
    public void heartbeat() throws Exception
    {
        MqClient client = new MqClient();
        String key ="04aebcc91bfd48c596c3df98709f4b41";
        while(true)
        {
            Thread.sleep(2000);
            System.out.println(client.heartbeat(key));

        }
    }
//   服务生命检测
    @Test
    public void deleteSer()  throws Exception
    {
//        MqClient client = new MqClient();
        String key="04aebcc91bfd48c596c3df98709f4b41";
        Broker.deleteList(key);
    }
//  注销服务
    @Test
    public void produce() throws Exception
    {
        while(true)
        {
            Thread.sleep(2000);
            MqClient client = new MqClient();
            client.produce("04aebcc91bfd48c596c3df98709f4b41", UUID.randomUUID().toString());
        }

    }
//  发送消息
    @Test
    public void consume() throws Exception
    {
        while (true)
        {
            Thread.sleep(2000);
            MqClient client = new MqClient();
            System.out.println(client.consume("04aebcc91bfd48c596c3df98709f4b41"));
        }
    }
//  多个客户端接收和发送消息
    @Test
    public void producedemo() throws Exception

    {
        String key="";
        MqClient pro = new MqClient();
        ClientThread ct2=new ClientThread(pro,"asdad");
        new Thread(ct2).start();
        while (true)
        {
            Thread.sleep(1000);
            pro.produce("asdad",UUID.randomUUID().toString());
        }
    }
    @Test
    public void consumedemo() throws Exception

    {
        String key="";
        MqClient con = new MqClient();
        ClientThread ct2=new ClientThread(con,"asdad");
        rs.submit(new Thread(ct2));
        while (true)
        {
            Thread.sleep(1000);
            con.consume("asdad");
        }
    }
}
