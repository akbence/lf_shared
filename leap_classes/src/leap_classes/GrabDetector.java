package leap_classes;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.flink.streaming.api.TimeCharacteristic;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import com.incquerylabs.iot.communication.PublisherPool;
import com.incquerylabs.iot.communication.SubscriberPool;
import com.incquerylabs.iot.communication.YellowPages;
import com.incquerylabs.iot.communication.exception.PoolNotInitializedException;
import com.incquerylabs.iot.communication.zmq.ZMQFactory;
//import com.incquerylabs.iot.leapmotion.YellowPages;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Frame;
import com.twitter.chill.protobuf.ProtobufSerializer;

//import akka.remote.serialization.ProtobufSerializer;

//import akka.remote.serialization.ProtobufSerializer;

public class GrabDetector {

	static BlockingQueue<Frame> bq;
	

	public static void main(String[] args) throws Exception {
		//Thread.sleep(5000);

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		env.getConfig().registerTypeWithKryoSerializer(Frame.class, ProtobufSerializer.class);
		

		SubscriberPool.initializePool(new ZMQFactory());
		PublisherPool.initializePool(new ZMQFactory());

		bq = new ArrayBlockingQueue<Frame>(1000);

		// get input data

		// new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(new
		// Connect(YellowPages.getFrameStreamAddress()));

		// FlinkFrameStream stream = new
		// FlinkFrameStream(YellowPages.getFrameStreamAddress());
		// stream.start();

		Connect connectToLeapMotion = new Connect(YellowPages.INSTANCE.getFrameStreamAddress());
		FlinkFrameStream fstream = new FlinkFrameStream();
		connectToLeapMotion.start();
		
		
		
		DataStream<Frame> stream = env.addSource(fstream);
		
		
		
//
//		stream.addSink(new SinkFunction<Frame>() {
//
//			@Override
//			public void invoke(Frame value) throws Exception {
//				// TODO Auto-generated method stub
//				System.out.println("gotcha");
//
//			}
//		});

		stream.print();

		/*
		 * DataStream<Integer> t= env.addSource(new StockSource()); //t.print();
		 * t.addSink(new SinkFunction<Integer>() {
		 * 
		 * @Override public void invoke(Integer value) throws Exception { //
		 * TODO Auto-generated method stub System.out.println(value);
		 * 
		 * } });
		 * 
		 */
		//env.execute();

	}

	/**
	 * Implements the string tokenizer that splits sentences into words as a
	 * user-defined FlatMapFunction. The function takes a line (String) and
	 * splits it into multiple pairs in the form of "(word,1)" (Tuple2<String,
	 * Integer>).
	 */

}
