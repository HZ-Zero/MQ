import java.sql.SQLException;

public class ServiceModifyImpl implements ServiceModify {
    @Override
    public void ServiceDelete(String key) throws SQLException {
        Broker.deleteList(key);
//      ���ݿ�ɾ��key
        JDBC.DeleteSer(key);
    }

    @Override
    public void ServiceAdd(String key) throws SQLException{
        Broker.creatList(key);
//      ���ݿ����key
        JDBC.AddSer(key);

    }
}
