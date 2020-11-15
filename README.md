# api-mock-server

### api-mock-server 模拟 Api 接口返回 JSON 数据。

---

###### 启动方式：
1. 命令启动：
    java -jar api-mock-server-1.0-SNAPSHOT.jar
2. Windows 启动：
    单击：ServerStart.bat
3. Bash 启动：
    单击：ServerStart.sh
    注：Mac 需要在当前目录下执行 `chmod 755 ServerStart.sh`命令后，才能单机执行 ServerStart.sh 脚本。


###### 配置：
1. 启动后会在该目录产生 `app.properties` 配置文件(注：存在则不会覆盖)。
2. 以 `请求路径=json文件路径(相对路径或绝对文件路径)` 格式进行配置，例 `/test=test.json`。


###### 请求：
1. 服务正常运行后控制台会打印`访问地址：http://*.*.*.*:8888` 的局域网访问地址。
2. 访问路径：http://*.*.*.*:8888/test

