import java.sql.SQLException;

public class ServiceModifyImpl implements ServiceModify {
    @Override
    public void ServiceDelete(String key) throws SQLException {
        Broker.deleteList(key);
//      数据库删除key
        JDBC.DeleteSer(key);
    }

    @Override
    public void ServiceAdd(String key) throws SQLException{
        Broker.creatList(key);
//      数据库插入key
        JDBC.AddSer(key);

    }
}
