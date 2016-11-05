package leap_classes;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

import com.incquerylabs.iot.communication.IAddress;
import com.incquerylabs.iot.communication.exception.PoolNotInitializedException;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Frame;

//import net.badata.protobuf.converter.Converter;

import com.incquerylabs.iot.leapmotion.proto.AbstractProtoFrameStream;
//import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos;

public class Connect extends AbstractProtoFrameStream implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame f;
	
	private boolean test;
	

	private BlockingQueue<Frame> bq = GrabDetector.bq;

	public Connect(IAddress sourceAddress) {
		// super(sourceAddress);
		super(sourceAddress);
		//bq = GrabDetector.bq;
		test=true;
				
		f = null;
//		try {
//			this.start();
//		} catch (PoolNotInitializedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processFrame(Frame frame) {
		// f = Converter.create().toDomain(Frame.class,frame);
		f = frame;
		//System.out.println("PF");
		try {
			
			bq.put(f);
			//System.out.println(bq.size());
			if(test)
			{
				System.out.println(f.getDescriptorForType().getClass());
			
				test=false;
			}
			
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		System.out.println("gond a BQ-val");
			e.printStackTrace();
		}
		
		//System.out.println(f.getId());
		// System.out.println(f);

	}


}