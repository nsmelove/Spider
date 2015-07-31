package com.nsm.spider;

import java.sql.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nsm on 2015/7/23.
 */
public class DataBase {
    private static final String url = "jdbc:mysql://localhost:3306/spider" ;
    private static final String username = "root" ;
    private static final String password = "123456" ;
    private static LinkedBlockingQueue<Connection> linkDataQueue = new LinkedBlockingQueue<Connection>(Constant.THREAD_SIZE);
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static Connection getConnection()throws Exception{
        if(linkDataQueue.size() == 0){
            Connection connection= DriverManager.getConnection(url, username, password);
            return connection;
        }else {
            return linkDataQueue.poll();
        }
    }
    private static boolean putConnection(Connection conn){
        return linkDataQueue.offer(conn);
    }
    public static boolean saveLinkData(LinkData linkData){
        try {
            Connection connection = getConnection();
            String sql = "insert into link_data(id,url,title,,text) values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,linkData.getId());
            preparedStatement.setString(2,linkData.getUrl());
            preparedStatement.setString(3,linkData.getTitle());
            preparedStatement.setString(4,linkData.getText());

            int resutCount = preparedStatement.executeUpdate();
            putConnection(connection);
            if(resutCount ==1){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public static boolean checkLink(String url){
        try {
            Connection connection = getConnection();
            String sql = "select count(1) from link_data where url ='"+url+"'";
            Statement statement =connection.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
