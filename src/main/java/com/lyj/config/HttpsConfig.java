package com.lyj.config;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 陆英杰
 * 2018/11/13 20:55
 */

/**
 * 配置https
 */
@Configuration
public class HttpsConfig {


    //获取到普通http访问的端口
    @Value("${http.port}")
    private Integer port;


    /**
     * 采用Undertow作为服务器。
     * Undertow是一个用java编写的、灵活的、高性能的Web服务器，提供基于NIO的阻塞和非阻塞API，特点：
     * 非常轻量级，Undertow核心瓶子在1Mb以下。它在运行时也是轻量级的，有一个简单的嵌入式服务器使用少于4Mb的堆空间。
     * 支持HTTP升级，允许多个协议通过HTTP端口进行多路复用。
     * 提供对Web套接字的全面支持，包括JSR-356支持。
     * 提供对Servlet 3.1的支持，包括对嵌入式servlet的支持。还可以在同一部署中混合Servlet和本机Undertow非阻塞处理程序。
     * 可以嵌入在应用程序中或独立运行，只需几行代码。
     * 通过将处理程序链接在一起来配置Undertow服务器。它可以对各种功能进行配置，方便灵活。
     */
    //使用undertow开启https和http2
    @Bean
    public ServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory undertowFactory = new UndertowServletWebServerFactory();
        undertowFactory.addBuilderCustomizers((Undertow.Builder builder) -> {
            builder.addHttpListener(port, "0.0.0.0");
            // 开启HTTP2
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);
        });
        undertowFactory.addDeploymentInfoCustomizers(deploymentInfo -> {
            // 开启HTTP自动跳转至HTTPS
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection().addUrlPattern("/*"))
                    .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> 443);
        });
        return undertowFactory;
    }

    //使用Tomcat开启https
//    @Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(port);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(443);
//        return connector;
//    }

}
