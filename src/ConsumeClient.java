public class ConsumeClient {
    public static MqClient client = new MqClient();
    public static String consume(String key) throws Exception {

        String message = client.consume(key);
        return message;
    }
    public static int heartbeat() throws Exception {
        return client.heartbeat();
    }
}
