package com.base.db;
 
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.base.utils.Constants;
 
/**
 * 数据库管理
 * @author Winter Lau
 * @date 2010-2-2 下午10:18:50
 */
public class DBManager {
    private final static Log log = LogFactory.getLog(DBManager.class);
    private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
    private static DruidDataSource dds = null;
    private static boolean DEBUG = false;
     
    static {
        initDataSource(null);
    }
 
    /**
     * 初始化连接池
     * @param props
     * @param show_sql
     */
    private final static void initDataSource(Properties dbProperties) {
        try {
            if(dbProperties == null){
                dbProperties = new Properties();
                dbProperties.load(DBManager.class.getResourceAsStream("/appConfig.properties"));
            }
            Properties dbProps = new Properties();
            for(Object key : dbProperties.keySet()) {
                String skey = (String)key;
                if(skey.startsWith("jdbc.")){
                    String name = skey.substring(5);
                    dbProps.put(name, dbProperties.getProperty(skey));
                }
            }
            
            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(dbProps);
            log.info("Using DataSource : " + dds.getClass().getName());
            //BeanUtils.populate(dds, dbProps);
 
            Connection conn = getConnection();
            DatabaseMetaData mdm = conn.getMetaData();
            log.info("Connected to " + mdm.getDatabaseProductName() + " " + mdm.getDatabaseProductVersion());
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new DBException(e);
        }
    }
     
    /**
     * 断开连接池
     */
    public final static void closeDataSource(){
        try {
            //dds.close();
        	dds.getClass().getMethod("close").invoke(dds);
        } catch (NoSuchMethodException e){ 
        } catch (Exception e) {
            log.error("Unabled to destroy DataSource!!! ", e);
        }
    }
 
    public final static Connection getConnection() throws SQLException {
        Connection conn = conns.get();
        if(conn ==null || conn.isClosed()){
            conn = dds.getConnection();
            conn.setAutoCommit(false);
            conns.set(conn);
        }
        return (Constants.devMode && !Proxy.isProxyClass(conn.getClass())) ? new _DebugConnection(conn).getConnection():conn;
    }
     
    /**
     * 提交事务
     */
    public final static void commit() {
        Connection conn = conns.get();
        try {
            if(conn != null && !conn.isClosed()){
            	log.debug("");
                conn.commit();
            }
        } catch (SQLException e) {
            log.error("Unabled to commit connection!!! ", e);
        }
        conns.set(null);
    }
    
    /**
     * 回滚事务
     */
    public final static void rollback() {
        Connection conn = conns.get();
        try {
            if(conn != null && !conn.isClosed()){
                conn.rollback();
            }
        } catch (SQLException e) {
            log.error("Unabled to rollback connection!!! ", e);
        }
        conns.set(null);
    }
    
    /**
     * 关闭连接
     */
    public final static void closeConnection() {
        Connection conn = conns.get();
        try {
            if(conn != null && !conn.isClosed()){
                conn.close();
                conns.remove();
            }
        } catch (SQLException e) {
            log.error("Unabled to close connection!!! ", e);
        }
        conns.set(null);
    }
 
    /**
     * 用于跟踪执行的SQL语句
     * @author Winter Lau
     */
    static class _DebugConnection implements InvocationHandler {
         
        private final static Log log = LogFactory.getLog(_DebugConnection.class);
         
        private Connection conn = null;
 
        public _DebugConnection(Connection conn) {
            this.conn = conn;
        }
 
        /**
         * Returns the conn.
         * @return Connection
         */
        public Connection getConnection() {
            return (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), 
                             conn.getClass().getInterfaces(), this);
        }
         
        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            try {
                String method = m.getName();
                if("prepareStatement".equals(method) || "createStatement".equals(method))
                    log.info("[SQL] >>> " + args[0]);              
                return m.invoke(conn, args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
 
    }
     
}