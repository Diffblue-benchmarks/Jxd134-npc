package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @author jinxd
 */
@AllArgsConstructor
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)
            throws Exception {
        if (genericClass.isInstance(o)) {
            byte[] data = SerializationUtil.serialize(o);
            if (null != data) {
                byteBuf.writeInt(data.length);
                byteBuf.writeBytes(data);
            }
        }
    }
}
