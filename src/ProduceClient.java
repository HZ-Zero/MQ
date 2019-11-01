public class ProduceClient {
    public static MqClient client = new MqClient();
    public static void produce(String key,String msg) throws Exception {
//            String key="SERVICE1:";
            client.produce("SEND:"+key+msg);
    }
    public static int heartbeat() throws Exception {
        return client.heartbeat();
    }

}
