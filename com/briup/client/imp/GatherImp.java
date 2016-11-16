package com.briup.client.imp;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.briup.client.Gather;
import com.briup.common.Backup;
import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;
import com.briup.common.imp.BackupImp;
import com.briup.common.imp.ConfigurationImp;
import com.briup.common.imp.Dom;
import com.briup.common.imp.LogImp;
import com.briup.model.BIDR;
import org.apache.log4j.Logger;
import org.junit.Test;

public class GatherImp implements Gather,ConfigurationAWare{
    String filename=null;
    String filesize=null;
    String filelist=null;
    private Backup backup=null;
    Logger log = null;
	@Override
    public void init(Properties properties) {

		// TODO Auto-generated method stub
		
	}
    @Override
    public void setConfiguration(Configuration configuration) {
        try {
            filename=new Dom().parse("gather","path");
            filelist=new Dom().parse("gather","gatherFileName");
            filesize=new Dom().parse("gather","countFileName");
            backup=configuration.getBackup();
            log=configuration.getLogger().getClientLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	@Override
	public Collection<BIDR> gather() throws Exception {

		// TODO Auto-generated method stub

        List<BIDR> BIDRList=new ArrayList<BIDR>();
        FileInputStream fileInputStream=new FileInputStream(new File(filename));
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
        Object count = backup.load(filesize);
        Object backlist=backup.load(filelist);
        if (backlist!=null){
            BIDRList.addAll((List<BIDR>) backlist);
            backup.deleteBackup(filelist);
        }
        int readlinesize=0;

        Map<String,BIDR> BIDRMap=new HashMap<>();
        try {
            if (count!=null){
                readlinesize=(Integer)count;
                bufferedReader.skip(readlinesize);
                backup.deleteBackup(filesize);
            }
            log.info("skip "+readlinesize);
            Map<String, BIDR> loadMap = (Map<String, BIDR>) backup.load("BIRD_map");
            if (loadMap != null) {
                log.info("备份文件大小：" + loadMap.size());
                BIDRMap.putAll(loadMap);
                backup.deleteBackup("BIRD_map");
            }
            while (bufferedReader.ready()){
                String bidrs=bufferedReader.readLine();
                readlinesize=readlinesize+bidrs.length()+2;
                String[] strings = bidrs.split("[|]");

                if(strings[2].equals("7")){
                    BIDR bidr=new BIDR();
                    bidr.setAaaLoginName(strings[0]);
                    bidr.setNasIp(strings[1]);
                    Integer parseInt = Integer.parseInt(strings[3]);
                    Date date=new Date(parseInt*1000L);
                    bidr.setLoginDate(date);
                    bidr.setLoginIp(strings[4]);
                    BIDRMap.put(strings[4],bidr);

                }else if(strings[2].equals("8")){
                    BIDR bidr=BIDRMap.get(strings[4]);
                    Date dayForLogOut=(new Date(Integer.parseInt(strings[3])*1000L));
                    int  logOutDate= dayForLogOut.getDate();//max one day
                    int logInDate=bidr.getLoginDate().getDate();
                    //同一天

                    if (logInDate==logOutDate){
                        bidr.setLogoutDate(dayForLogOut);
                        bidr.setTimeDuration((bidr.getLogoutDate().getTime()-bidr.getLoginDate().getTime()));
                        BIDRList.add(bidr);
                        BIDRMap.remove(strings[4]);

                    }
                    else {
                        //不在同一天
                        //22:00-0:00-7:00
                        dayForLogOut.setHours(0);
                        dayForLogOut.setMinutes(0);
                        dayForLogOut.setSeconds(0);
                        //0:00
                        bidr.setLogoutDate(dayForLogOut);
                        bidr.setTimeDuration((bidr.getLogoutDate().getTime()-bidr.getLoginDate().getTime()));
                        BIDRList.add(bidr);
                        BIDRMap.remove(strings[4]);
                        //7:00
                        BIDR newBIDR=new BIDR();
                        newBIDR.setAaaLoginName(bidr.getAaaLoginName());
                        newBIDR.setLoginDate(dayForLogOut);
                        newBIDR.setLoginIp(strings[4]);
                        newBIDR.setLogoutDate(new Date(Integer.parseInt(strings[3])*1000L));
                        newBIDR.setTimeDuration(newBIDR.getLogoutDate().getTime()-newBIDR.getLoginDate().getTime());
                        newBIDR.setNasIp(strings[1]);
                        BIDRList.add(newBIDR);
                    }
                }

            }
            log.info("gather end");
            log.info("list size  "+BIDRList.size());
            log.info("map size  "+BIDRMap.size());
            backup.backup(filesize,readlinesize);
            backup.backup("BIRD_map",BIDRMap);
        }catch (Exception e){
            e.printStackTrace();
            backup.backup(filelist,BIDRList);
        }finally {
            bufferedReader.close();
        }



        if (BIDRList.size()!=0){
            return BIDRList;
        }
        return null;


	}



}
