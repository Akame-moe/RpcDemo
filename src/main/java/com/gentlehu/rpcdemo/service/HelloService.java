package com.gentlehu.rpcdemo.service;

import com.gentlehu.rpcdemo.grpc.HelloRequest;
import com.gentlehu.rpcdemo.grpc.HelloResponse;
import com.gentlehu.rpcdemo.grpc.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;


/**
 * Created by gentle-hu on 2018/10/27 2:40.
 * Email:me@gentlehu.com
 */

public class HelloService extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    private static Logger logger = Logger.getLogger(HelloService.class);

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        super.sayHello(request, responseObserver);
        logger.info("server received " + request);
        String m = "reply from server :" + request.getMessage();
        HelloResponse resp = HelloResponse.newBuilder().setMessage(m).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
