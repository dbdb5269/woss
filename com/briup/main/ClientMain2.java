package com.briup.main;


import com.briup.client.imp.ClientImp;
import com.briup.client.imp.GatherImp;

/**
 * @author Chen
 * 客户端启动
 * */


public class ClientMain2 {
    public static void main(String[] args) throws Exception {

        GatherImp gatherImp=new GatherImp();
        ClientImp clientImp=new ClientImp();

        clientImp.send(gatherImp.gather());
    }
}
