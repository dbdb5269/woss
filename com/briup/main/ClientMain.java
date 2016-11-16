package com.briup.main;


import com.briup.client.Client;
import com.briup.client.Gather;
import com.briup.client.imp.ClientImp;
import com.briup.client.imp.GatherImp;
import com.briup.common.Configuration;
import com.briup.common.imp.ConfigurationImp;

/**
 * @author Chen
 * 客户端启动
 * */


public class ClientMain {
	public static void main(String[] args) throws Exception {
        Configuration configuration=new ConfigurationImp();
        Gather gatherImp=configuration.getGather();
        Client clientImp=configuration.getClient();
        clientImp.send(gatherImp.gather());
	}
}
