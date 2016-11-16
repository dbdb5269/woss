package com.briup.common.imp;

import com.briup.common.Backup;
import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;

import java.io.*;
import java.util.Properties;

/**
 * Created by dubo on 16/11/11.
 */
public class BackupImp implements Backup,ConfigurationAWare{
    //private static

    String parent=null;
    @Override
    public void setConfiguration(Configuration configuration) {
        try {
            parent=new Dom().parse("backup","path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init(Properties properties) {

    }


    @Override
    public void backup(String fileName, Object data) throws Exception {

        File file=new File(parent,fileName);
        if (file.exists()){
            file.delete();
        }else {
            file.createNewFile();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(data);
        out.flush();
        out.close();
    }

    @Override
    public Object load(String fileName) throws Exception {


        File file = new File(parent, fileName); //构建文件
        if (!file.exists()) { //如果文件不存在那么就结束
            System.out.println("load方法中根据key值不存在备份文件");
            return null;
        }
        Object object = null;
        if (file.exists()){
            if(file.length()>0){
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                object = inputStream.readObject();
                inputStream.close();


            }
        }

        return object;
    }

    @Override
    public void deleteBackup(String fileName) {

        File file = new File(parent, fileName);
        if (file.exists()){
            file.delete();
        }

    }


}
