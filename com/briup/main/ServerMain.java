package com.briup.main;


import com.briup.common.imp.ConfigurationImp;
import com.briup.server.Server;
import com.briup.server.imp.DBStoreImp;
import com.briup.server.imp.ServerImp;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Chen
 * 服务器启动
 * */
public class ServerMain {
	public static void main(String[] args) throws Exception {
        Server serverImp=new ConfigurationImp().getServer();
        try {
            //服务器初始化
            serverImp.revicer();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
