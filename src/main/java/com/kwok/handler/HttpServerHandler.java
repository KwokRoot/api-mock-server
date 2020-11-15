package com.kwok.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Description:
 * Date: 2020/10/31
 * Author: Kwok
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String json = "{\"msg\":\"error\"}";

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            // 1.获取URI
            String uri = req.uri();
            System.out.println(">>> 请求路径：" + uri);

            Properties properties = new Properties();
            properties.load(new FileInputStream("app.properties"));
            String jsonFilePath = properties.getProperty(uri);
            if (jsonFilePath != null){
                File jsonFile = new File(jsonFilePath);
                if(jsonFile.exists()){
                    json = IOUtils.toString(new FileInputStream(jsonFilePath), "UTF-8");
                }else{
                    json = "{\"msg\":\"`" + jsonFilePath + "`is not exists\"}";
                }
            }else{
                json = "{\"msg\":\"`app.properties` is not `" + uri + "`\"}";
            }

            // 2. 获取请求方法
            System.out.println(">>> 请求方法：" + req.method().name());

            // 3.获取请求体
            ByteBuf buf = req.content();
            String content = buf.toString(CharsetUtil.UTF_8);
            System.out.println(">>> 请求体：");
            System.out.println(content);

        }

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(json.getBytes("UTF-8")));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + "; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();

        System.out.println("=================================================");
        System.out.println();

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
