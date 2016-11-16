package com.briup.common.imp;

import com.briup.common.Configuration;
import com.briup.common.ConfigurationAWare;
import com.briup.common.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dubo on 16/11/14.
 */
public class LogImp implements Log{

    Properties properties=new Properties();
    public LogImp(){

        try {
            properties.load(new FileInputStream("src/log4j.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init(Properties properties) {

    }
    @Override
    public Logger getClientLogger() {
        //1.读取Log4j配置文件
        PropertyConfigurator.configure(properties);
        //2.获取根Logger对象
        Logger logger=Logger.getRootLogger();
        //3.子类
        Logger clientLogger= logger.getLogger("clientLogger");

        return clientLogger;
    }

    @Override
    public Logger getServerLogger() {
        //1.读取Log4j配置文件
        PropertyConfigurator.configure(properties);
        //2.获取根Logger对象
        Logger logger=Logger.getRootLogger();
        //3.子类
        Logger serverLogger= logger.getLogger("serverLogger");

        return serverLogger;
    }


}
