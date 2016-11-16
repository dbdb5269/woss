package com.briup.main;

import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;
import com.briup.common.imp.ConfigurationImp;
import com.briup.common.imp.LogImp;
import com.briup.model.BIDR;
import com.briup.server.DBStore;
import com.briup.server.imp.DBStoreImp;
import com.briup.server.imp.ServerImp;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * @author Chen
 * 线程类,处理一些多线程问题
 * */
public class ServerThread extends Thread  {
	private Socket socket;
	public ServerThread(Socket socket){
		this.socket=socket;
	}
	DBStore dbStoreImp=new ConfigurationImp().getDBStore();
	Logger log = new LogImp().getServerLogger();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		InputStream inputStream = null;
		try {
			try {

				inputStream = socket.getInputStream();
				ObjectInputStream objectInput=new ObjectInputStream(inputStream);

				try {
					List<BIDR> BIDR_list=(List<BIDR>) objectInput.readObject();


					try {
						if (BIDR_list!=null){

							dbStoreImp.saveToDB(BIDR_list);
						}
						else {
							log.info("Bidr_list is null");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e){
			e.printStackTrace();

		}






	}


}
