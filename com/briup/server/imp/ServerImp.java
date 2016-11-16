package com.briup.server.imp;

import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;
import com.briup.common.imp.Dom;
import com.briup.common.imp.LogImp;
import com.briup.main.ServerThread;
import com.briup.model.BIDR;
import com.briup.server.Server;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by dubo on 16/11/9.
 */
public class ServerImp implements Server,ConfigurationAWare{
    private List<BIDR> BIDR_list=new ArrayList<>();
    private ServerSocket serverSocket;
    private Socket socket;
    Integer port=null;
    public List<BIDR> getBIDR_list() {
        return BIDR_list;
    }

    public void setBIDR_list(List<BIDR> BIDR_list) {
        this.BIDR_list = BIDR_list;
    }

    Logger log = null;
    @Override
    public void setConfiguration(Configuration configuration) {
        try {
            port=Integer.parseInt(new Dom().parse("server","port"));
            serverSocket= new ServerSocket(port);
            log=configuration.getLogger().getServerLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init(Properties properties) {
//        try {
//            port=Integer.parseInt(properties.getProperty("port"));
//            serverSocket= new ServerSocket(port);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void revicer() throws Exception {

        while (true){

            socket=serverSocket.accept();
            log.info("start accept");

            new ServerThread(socket).start();

        }



    }
    @Override
    public void shutdown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
