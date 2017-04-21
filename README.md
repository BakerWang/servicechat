# servicechat 
springboot and netty websockett
* 为了使用https协议这里使用自定义证书，
### 直接使用Java自带的命令keytool来生成（当然也可以最下面的生成方式），生成命令如下：
* keytool -genkey -alias tomcat  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650

```xml
1.-storetype 指定密钥仓库类型
2.-keyalg 生证书的算法名称，RSA是一种非对称加密算法
3.-keysize 证书大小
4.-keystore 生成的证书文件的存储路径
5.-validity 证书的有效期
```
* 执行完上面一行命令后，在你的系统的当前用户目录下会生成一个keystore.p12文件.在生成的过程中会有一些需要填写的资料
### 然后将文件拷贝到项目的目录中，在配置文件中配置如下：
```properties
server.ssl.key-store=classpath:key/keystore.p12
server.ssl.key-store-password=123456
#密钥仓库类型
server.ssl.keyStoreType=PKCS12
#别名
server.ssl.keyAlias:tomcat
```

### 直接启动项目，访问：会发现访问的url变成http://www.localhost.com:8080/login.do这种形式。且不可访问。
* 因为https默认使用的8443端口，当端口换后，由于浏览器的证书列表没有自己定义的证书，所以要在浏览器中导入证书。导入证书后，在webconfig添加如下的配置代码，即可将原来的http服务重定向到https上。
后访问为了解决http也能正常访问需要，需要如下配置：
### HTTP自动转向HTTPS：，在webconfig中添加如下
```java @Bean
    /**2017年4月21日
     * @author xzg
     * TODO 配置https
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                Ssl ssl = new Ssl();
                //Server.jks中包含服务器私钥和证书
                ssl.setKeyStore("keystore.p12");//这里访问的是项目的根目录
                ssl.setKeyStorePassword("123456");
                container.setSsl(ssl);
                container.setPort(8443);
            }
        };
    }
    
    /**2017年4月21日
     * @author xzg
     * TODO 配置Http使其自动重定向到Https
     * Embedded默认只有一个Connector，要在提供Https服务的同时支持Http，
     * 需要添加一个Connector。在配置类中添加如下配置：
     */
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
            	//SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(8080);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(8443);
        return connector;
    }
```
### 另外一种生成证书的方式
证书颁发机构

    CA机构私钥

openssl genrsa -out ca.key 2048

    CA证书

openssl req -x509 -new -key ca.key -out ca.crt

注意生成过程中需要输入一些CA机构的信息
服务端

    生成服务端私钥

openssl genrsa -out server.key 2048

    生成服务端证书请求文件

openssl req -new -key server.key -out server.csr

注意生成过程中需要你输入一些服务端信息

    使用CA证书生成服务端证书

openssl x509 -req -sha256 -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -days 3650 -out server.crt

关于sha256，默认使用的是sha1，在新版本的chrome中会被认为是不安全的，因为使用了过时的加密算法。

    打包服务端的资料为pkcs12格式(非必要，只是换一种格式存储上一步生成的证书)

openssl pkcs12 -export -in server.crt -inkey server.key -out server.pkcs12

生成过程中，需要创建访问密码，请记录下来。

    生成服务端的keystore（.jks文件, 非必要，Java程序通常使用该格式的证书）

keytool -importkeystore -srckeystore server.pkcs12 -destkeystore server.jks -srcstoretype pkcs12

生成过程中，需要创建访问密码，请记录下来。

    把ca证书放到keystore中（非必要）

keytool -importcert -keystore server.jks -file ca.crt
* 导入根证书ca.crt到浏览器受信任的根证书颁发机构列表中,剩下的和上面的config配置文件一样即可
