package xcfg4j.console;

import org.apache.ibatis.session.SqlSession;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ConnectionStrings css = ConnectionStrings.getInstance(ConnectionStrings.class);
    	if(css!=null) {
    		for(ConnectionString cs : css.getConns()) {
    			System.out.println(cs.getConnectionStr());
    		}
    	}
    	Log4jHelper.LOGGER.error("start openSession");
    	SqlSession session = MyBatisHelper.getSession().openSession();
    	try {
    		 IUser iuser = session.getMapper(IUser.class);
             User user = iuser.getUserByID(1);
			if(user!=null){
				String userInfo = user.getName();
				System.out.println(userInfo);
			}
		} finally {
			session.close();
		}
    	Log4jHelper.LOGGER.error("session close");
    }
    
    
}
