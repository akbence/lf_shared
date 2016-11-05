package leap_classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import org.apache.flink.streaming.api.checkpoint.Checkpointed;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import com.incquerylabs.iot.communication.IAddress;
import com.incquerylabs.iot.communication.PublisherPool;
import com.incquerylabs.iot.communication.SubscriberPool;
import com.incquerylabs.iot.communication.zmq.ZMQFactory;
//import com.incquerylabs.iot.leapmotion.YellowPages;
//import com.incquerylabs.iot.leapmotion.processor.AbstractProtoFrameStream;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Frame;
//import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Frame;
import com.incquerylabs.iot.leapmotion.*;

//import net.badata.protobuf.converter.Converter;


public class FlinkFrameStream extends RichSourceFunction<Frame> implements Serializable, Checkpointed<Long>{
	
	public boolean isRunning;
	public long count;
	public BlockingQueue<Frame> bq=GrabDetector.bq;

	
	public FlinkFrameStream() {
		
		
		isRunning= true;
		count=0L;
		
		
	}

	

	@Override
	public void run(org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext<Frame> ctx)
			throws Exception {
		
		while (isRunning) {
			
		
			synchronized (ctx.getCheckpointLock()) {
				Frame f = null;//bq.take();
				System.out.println("Taken");
				
				
				ctx.collect(f);
				Thread.sleep(500);
				System.out.println(count);
				count++;
			}
		}
	}

	@Override
	public void cancel() {
		isRunning=false;
	}
	
	@Override
	public Long snapshotState(long checkpointId, long checkpointTimestamp) throws Exception {
		// TODO Auto-generated method stub
		return count;
	}
	
	@Override
	public void restoreState(Long state) throws Exception {
		// TODO Auto-generated method stub
		this.count=state;
	}




	
	
}

