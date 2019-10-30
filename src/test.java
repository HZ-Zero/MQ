//import Pro


public class test {
    public static void main(String[] args) throws Exception{
        String msg="11111";
        while(true)
        {
            Thread.sleep(1500);
        ProduceClient.produce("SERVICE1:",msg);
        System.out.println("SERVICE1收到"+ConsumeClient.consume("SERVICE1:"));

        ProduceClient.produce("SERVICE2:",msg);
        System.out.println("SERVICE2收到"+ConsumeClient.consume("SERVICE2:"));
        }

    }
}
