public class ServiceModifyImpl implements ServiceModify {
    @Override
    public void ServiceDelete(String key) {
        Broker.deleteList(key);
//      ���ݿ�ɾ��key
    }

    @Override
    public void ServiceAdd(String key) {
        Broker.creatList(key);
//      ���ݿ����key

    }
}
