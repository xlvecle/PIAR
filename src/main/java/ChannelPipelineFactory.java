import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import pipeline.FrameDecoder;
import pipeline.FrameEncoder;

/**
 * Created by jnkmhbl on 17/3/6.
 */
public class ChannelPipelineFactory  implements org.jboss.netty.channel.ChannelPipelineFactory {
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encoder",new FrameEncoder());
        pipeline.addLast("decoder",new FrameDecoder());
        return pipeline ;
    }
}
