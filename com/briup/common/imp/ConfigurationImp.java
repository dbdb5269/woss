package com.briup.common.imp;

import com.briup.client.Client;
import com.briup.client.Gather;
import com.briup.common.*;
import com.briup.server.DBStore;
import com.briup.server.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dubo on 16/11/14.
 */
public class ConfigurationImp implements Configuration{
    @Override
    public Log getLogger() {
        return (Log)getInstance("logger");
    }

    @Override
    public Backup getBackup() {
        return (Backup) getInstance("backup");
    }

    @Override
    public Gather getGather() {
        return (Gather)getInstance("gather");
    }

    @Override
    public Client getClient() {
        return (Client)getInstance("client");
    }

    @Override
    public Server getServer() {
        return (Server)getInstance("server");
    }

    @Override
    public DBStore getDBStore() {
        return (DBStore)getInstance("dbStore");
    }


    public Object getInstance(String str){

        Dom dom=new Dom();

        Properties info=new Properties();
        try {
            info.load(new FileInputStream(new File("./src/com/briup/main/woss.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            WossModule w= (WossModule) Class.forName(dom.parse_attribute(str)).newInstance();
            w.init(info);

            if (w instanceof ConfigurationAWare){
                ((ConfigurationAWare) w).setConfiguration(this);
            }
            return w;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
