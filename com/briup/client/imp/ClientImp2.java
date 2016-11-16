package com.briup.client.imp;

import java.io.BufferedWriter;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.client.Client;
import com.briup.model.BIDR;
import org.junit.Test;

public class ClientImp2 implements Client{

    @Override
    public void init(Properties properties) {
        // TODO Auto-generated method stub

    }

    @Override

    public void send(Collection<BIDR> collection) throws Exception {
        System.out.println("send function start");
        Collection<BIDR> bidrs = collection;
        Socket socket=new Socket("127.0.0.1",8888);
        System.out.println("start outputstream");
        OutputStream outputStream=socket.getOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(bidrs);
        objectOutputStream.close();
        System.out.println("end outputstream");
        // TODO Auto-generated method stub

    }

}
