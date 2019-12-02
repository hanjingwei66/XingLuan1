package com.shuojie.nettyService.Handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
@ChannelHandler.Sharable
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<MultipartFile> {
    private static final Logger log = LoggerFactory.getLogger(BinaryWebSocketFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MultipartFile msg) throws InterruptedException {

        MultipartFile file=msg;
        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "F:/mystudy/pic/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            System.out.println("上传成功后的文件路径未：" + filePath + fileName);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (msg instanceof BinaryWebSocketFrame) {
//            ByteBuf content = msg.content();
//            log.info("服务器接收到二进制消息,消息长度:[{}]", msg.content().capacity());
//            MultipartFile file = (MultipartFile) msg.content();
//            String originalFilename = file.getOriginalFilename();
//            System.out.println(originalFilename);
//            byte[] array = new byte[content.readableBytes()];//转为字节数组
//            String path = "F:/test";
//            File dest = new File(path + "/" + ctx.channel().id().asLongText() + msg);
//            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
//                dest.getParentFile().mkdir();
//            }
//            try {
//                OutputStream outputStream = new FileOutputStream(dest);
//                outputStream.write(array);
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ByteBuf byteBuf = Unpooled.directBuffer(msg.content().capacity());
//            byteBuf.writeBytes(msg.content());
//            ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
//        }

    }

    private void writeChunk(ChannelHandlerContext ctx) throws IOException {
//        while (httpDecoder.hasNext()) {
//            InterfaceHttpData data = httpDecoder.next();
//            if (data != null && HttpDataType.FileUpload.equals(data.getHttpDataType())) {
//                final FileUpload fileUpload = (FileUpload) data;
//                final File file = new File(FILE_UPLOAD + fileUpload.getFilename());
//                log.info("upload file: {}", file);
//                try (FileChannel inputChannel = new FileInputStream(fileUpload.getFile()).getChannel();
//                     FileChannel outputChannel = new FileOutputStream(file).getChannel()) {
//                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
//                    ResponseUtil.response(ctx, request, new GeneralResponse(HttpResponseStatus.OK, "SUCCESS", null));
//                }
//            }
//        }
    }
}
