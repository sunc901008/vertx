package openscanner.cc.Verticles;

import com.alibaba.druid.pool.DruidDataSource;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.spi.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created : sunc
 * Date : 16-5-14
 * Description : DruidDataSourceProvider
 */
public class DruidDataSourceProvider implements DataSourceProvider {

    @Override
    public DataSource getDataSource(JsonObject config) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        config(dataSource, config);
        return dataSource;
    }

    @Override
    public void close(DataSource dataSource) throws SQLException {
        ((DruidDataSource)dataSource).close();
    }

    private void config(DruidDataSource dataSource, JsonObject json){
        String driverClassName = json.getString("driverClassName", "");
        if(!driverClassName.isEmpty()){
            dataSource.setDriverClassName(driverClassName);
        }

        String url = json.getString("url", "");
        if(!url.isEmpty()){
            dataSource.setUrl(url);
        }

        String username = json.getString("username", "");
        if(!username.isEmpty()){
            dataSource.setUsername(username);
        }

        String password = json.getString("password", "");
        if(!password.isEmpty()){
            dataSource.setPassword(password);
        }

        String filters = json.getString("filters", "");
        if(!filters.isEmpty()){
            try {
                dataSource.setFilters(filters);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String initialSize = json.getString("initialSize", "");
        if(!initialSize.isEmpty()){
            dataSource.setInitialSize(Integer.parseInt(initialSize));
        }

        String minIdle = json.getString("minIdle", "");
        if(!minIdle.isEmpty()){
            dataSource.setMinIdle(Integer.parseInt(minIdle));
        }

        String maxActive = json.getString("maxActive", "");
        if(!maxActive.isEmpty()){
            dataSource.setMaxActive(Integer.parseInt(maxActive));
        }

        String maxWait = json.getString("maxWait", "");
        if(!maxWait.isEmpty()){
            dataSource.setMaxWait(Integer.parseInt(maxWait));
        }

        String timeBetweenEvictionRunsMillis = json.getString("timeBetweenEvictionRunsMillis", "");
        if(!timeBetweenEvictionRunsMillis.isEmpty()){
            dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(timeBetweenEvictionRunsMillis));
        }

        String minEvictableIdleTimeMillis = json.getString("minEvictableIdleTimeMillis", "");
        if(!minEvictableIdleTimeMillis.isEmpty()){
            dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(minEvictableIdleTimeMillis));
        }

        Boolean poolPreparedStatements = getBoolean(json.getString("poolPreparedStatements", ""));
        if(poolPreparedStatements != null){
            dataSource.setPoolPreparedStatements(poolPreparedStatements);
        }

        String maxPoolPreparedStatementPerConnectionSize = json.getString("maxPoolPreparedStatementPerConnectionSize", "");
        if(!maxPoolPreparedStatementPerConnectionSize.isEmpty()){
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(maxPoolPreparedStatementPerConnectionSize));
        }

        String validationQuery = json.getString("validationQuery", "");
        if(!validationQuery.isEmpty()){
            dataSource.setValidationQuery(validationQuery);
        }

        Boolean testWhileIdle = getBoolean(json.getString("testWhileIdle", ""));
        if(testWhileIdle != null){
            dataSource.setTestWhileIdle(testWhileIdle);
        }

        Boolean testOnBorrow = getBoolean(json.getString("testOnBorrow", ""));
        if(testOnBorrow != null){
            dataSource.setTestOnBorrow(testOnBorrow);
        }

        Boolean testOnReturn = getBoolean(json.getString("testOnReturn", ""));
        if(testOnReturn != null){
            dataSource.setTestOnReturn(testOnReturn);
        }

        Boolean removeAbandoned = getBoolean(json.getString("removeAbandoned", ""));
        if(removeAbandoned != null){
            dataSource.setRemoveAbandoned(removeAbandoned);
        }

        String removeAbandonedTimeout = json.getString("removeAbandonedTimeout", "");
        if(!removeAbandonedTimeout.isEmpty()){
            dataSource.setRemoveAbandonedTimeout(Integer.parseInt(removeAbandonedTimeout));
        }
    }

    private Boolean getBoolean(String str){
        if(str.equals("true")){
            return true;
        }
        if(str.equals("false")){
            return false;
        }
        return null;
    }

}
