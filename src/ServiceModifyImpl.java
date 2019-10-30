public class ServiceModifyImpl implements ServiceModify {
    @Override
    public void ServiceDelete(String key) {
        Broker.deleteList(key);
//      数据库删除key
    }

    @Override
    public void ServiceAdd(String key) {
        Broker.creatList(key);
//      数据库插入key

    }
}
