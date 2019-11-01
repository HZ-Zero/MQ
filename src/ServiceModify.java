import java.sql.SQLException;

public interface ServiceModify {
    public void ServiceDelete(String key) throws SQLException;
    public void ServiceAdd(String key) throws SQLException;

}
