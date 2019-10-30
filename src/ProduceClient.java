public class ProduceClient {

    public static void produce(String key,String msg) throws Exception {
        MqClient client = new MqClient();
//        while(true)
//        {
//            Thread.sleep(1000);
//            String key="SERVICE1:";
            client.produce("SEND:"+key+msg);
//        }
    }

}
