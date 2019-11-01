public class ClientThread implements Runnable{
    MqClient client =new MqClient();
    String key;
    public ClientThread(MqClient client,String key)
    {
        this.client=client;
        this.key=key;
    }
    @Override
    public void run(){
        while (true)
        {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
                System.out.println(client.heartbeat(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
