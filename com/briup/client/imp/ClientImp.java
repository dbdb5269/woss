package com.briup.client.imp;


import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;
import com.briup.client.Client;
import com.briup.common.Backup;
import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;
import com.briup.common.imp.BackupImp;
import com.briup.common.imp.ConfigurationImp;
import com.briup.common.imp.Dom;
import com.briup.common.imp.LogImp;
import com.briup.model.BIDR;
import org.apache.log4j.Logger;


public class ClientImp implements Client,ConfigurationAWare{

    private Backup backupImp=null;
    private Client client=null;
    String fileName = "";
    String IP="";
    Integer port=null;
    Logger log = null;
    @Override

    public void setConfiguration(Configuration configuration) {
        backupImp=configuration.getBackup();
        try {
            IP =new Dom().parse("client","ip");
            fileName=new Dom().parse("client","bakFileName");
            port=Integer.parseInt(new Dom().parse("client","port"));
            log=configuration.getLogger().getClientLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	@Override
	public void init(Properties properties) {

	}

	@Override
	public void send(Collection<BIDR> collection) throws Exception {

        try {
            Collection<BIDR> collection1= (Collection<BIDR>) backupImp.load(fileName);
            if (collection1!=null){
                collection.addAll(collection1);
                backupImp.deleteBackup(fileName);
            }
            log.info("send start");
            Collection<BIDR> bidrs = collection;
            Socket socket=new Socket(IP,port);
            log.info("start outputstream");
            OutputStream outputStream=socket.getOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(bidrs);
            objectOutputStream.close();
            log.info("end outputstream");
        }catch (Exception e){
            e.printStackTrace();

            backupImp.backup(fileName,collection);
        }


		
	}


}
