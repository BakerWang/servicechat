# servicechat 
springboot and netty websockett
* 为了使用https协议这里使用自定义证书，
### 直接使用Java自带的命令keytool来生成，生成命令如下：
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
* 因为https默认使用的8443端口，当端口换后，由于浏览器的证书列表没有自己定义的证书，所以要在浏览器中导入证书。后访问
为了解决http也能正常访问需要，需要如下配置：
### HTTP自动转向HTTPS：，在webconfig中添加如下
```java @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
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
