package xcfg4j.console;

import org.apache.ibatis.annotations.Select;

public interface IUser {
    @Select("select * from user where id= #{id}")
    public User getUserByID(int id);
}
