public class ConsumeClient {

    public static String consume(String key) throws Exception {
        MqClient client = new MqClient();
//        String message = client.consume();
        String message = client.consume(key);
//        while(message  != null) {
////        String message = client.consume();
////            String key="SERVICE1:";
//            System.out.println("��ȡ����ϢΪ��" + message);
//            message = client.consume(key);
//        }
        return message;
//        }

    }
}
