#**电信采集**
##开发环境： IntelliJ IDEA     JDK1.8.0_91     oracle 10g    
###技术：Java    xml   oracle   jdbc   ooad 
###项目描述：
将AAA服务记录的计费日志文件，解析保存到中央服务器的数据库中。
1. 采集模块：解析计费日志文件，转化成Java对象.IO     集合    不完整数据处理  读取字符长度
2. 备份模块：将需要保存的对象信息或备份数据，持久化到文件系统中。Map  count。发生异常备份处理
3. 网络模块：将AAA（客户端）的对象信息发送给中央服务器。
4. 网络编程    线程
5. 入库模块：将中央服务器接收到的对象信息，保存到数据库中。
6. 日志模块：记录程序运行期间发生一切操作。（INFO,EROOR,WARN）
7. 配置模块：管理其他模块的对象创建以及初始化。1.代码灵活2.可扩展增强
