//import Pro

import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.ConnectException;


public class testclass {

    @Test
    public void  test() throws Exception
    {
        String msg="11111";
        while(true)
        {
            Thread.sleep(1500);
            ProduceClient.produce("SERVICE1:",msg);
            System.out.println("SERVICE1�յ�"+ConsumeClient.consume("SERVICE1:"));

            ProduceClient.produce("SERVICE2:",msg);
            System.out.println("SERVICE2�յ�"+ConsumeClient.consume("SERVICE2:"));
        }
    }
    @Test
    public void test2() throws Exception
    {
        MqClient client = new MqClient();
        while(true)
        {
            Thread.sleep(1000);
        System.out.println(client.heartbeat());

        }
    }
    @Test
    public void test3() throws Exception
    {
        ProduceClient.produce("ASDASF:","SSSSSSSSSSSSSSSS");
        ProduceClient.heartbeat();
    }
    @Test
    public void test4() throws Exception
    {
        ProduceClient.produce("ASDASF:","SSSSSSSSSSSSSSSS");
        ProduceClient.heartbeat();
    }
}
