package pipeline;

import codeEvent.CodeEvent;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created by jnkmhbl on 17/3/6.
 */
public class FrameEncoder extends OneToOneEncoder{
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg)
            throws Exception {
        if(msg == null || (!(msg instanceof CodeEvent))){
            return null ;
        }
        return ((CodeEvent)msg).getBuffer();
    }


}
