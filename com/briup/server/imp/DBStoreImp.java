package com.briup.server.imp;

import com.briup.common.Backup;
import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;
import com.briup.common.DBUtils;
import com.briup.common.imp.BackupImp;
import com.briup.common.imp.ConfigurationImp;
import com.briup.common.imp.Dom;
import com.briup.common.imp.LogImp;
import com.briup.model.BIDR;
import com.briup.server.DBStore;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by dubo on 16/11/9.
 */

public class DBStoreImp implements DBStore,ConfigurationAWare{
    private String url;
    private String user;
    private String password;
    private String filename;
    private Backup backup=null;
    Logger log = new LogImp().getServerLogger();
    @Override
    public void setConfiguration(Configuration configuration) {

        try {
            url=new Dom().parse("dbStore","url");

            user=new Dom().parse("dbStore","user");
            password=new Dom().parse("dbStore","password");
            filename=new Dom().parse("dbStore","bakFileName");
            backup=configuration.getBackup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init(Properties properties) {
//        url=properties.getProperty("url");
//        user=properties.getProperty("user");
//        password=properties.getProperty("password");
    }

    @Override
    public synchronized void saveToDB(Collection<BIDR> collection) throws Exception {
        DBUtils dbUtils=new DBUtils();

        Connection connection = dbUtils.getConnection(url, user, password);
        connection.setAutoCommit(false);
        int insertsize=0;
        String table="0";
        String sql=null;
        int ex=0;
        PreparedStatement pstmt=null;

        Object backlist=backup.load(filename);
        if (backlist!=null){
            collection.addAll((Collection<? extends BIDR>) backlist);
            backup.deleteBackup(filename);
        }
        Iterator iterator=collection.iterator();
        try {
            while (iterator.hasNext()){
                BIDR bidr=(BIDR)iterator.next();
                if (!table.equals(String.valueOf(bidr.getLoginDate().getDate()))){
                    if(!table.equals("0")){  //pstmt!=null
                        pstmt.executeBatch();
                        pstmt.clearBatch();
                        pstmt.close();
                    }
                    table=String.valueOf(bidr.getLoginDate().getDate());
                    sql="insert into "+"t_detail_"+table+" values(?,?,?,?,?,?)";
                    pstmt=connection.prepareStatement(sql);
                    ex++;
                }
                pstmt.setString(1,bidr.getAaaLoginName());
                pstmt.setString(2,bidr.getLoginIp());
                pstmt.setTimestamp(3,new java.sql.Timestamp(bidr.getLoginDate().getTime()));
                pstmt.setTimestamp(4,new java.sql.Timestamp(bidr.getLogoutDate().getTime()));
                pstmt.setString(5,bidr.getNasIp());
                pstmt.setLong(6,bidr.getTimeDuration());
                pstmt.addBatch();
                insertsize++;
                if (insertsize%1000==0){
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }
            }
            pstmt.executeBatch();
            //commit
            connection.commit();
            log.info(insertsize);
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            backup.backup(filename,collection);
        }finally {
            pstmt.close();
            connection.close();
        }
    }



}
