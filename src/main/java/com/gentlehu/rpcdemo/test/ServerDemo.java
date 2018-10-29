package com.gentlehu.rpcdemo.test;

import com.gentlehu.rpcdemo.grpc.HelloRequest;
import com.gentlehu.rpcdemo.grpc.HelloResponse;
import com.gentlehu.rpcdemo.grpc.HelloWorldServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * Created by gentle-hu on 2018/10/29 16:11.
 * Email:me@gentlehu.com
 */
public class ServerDemo {

    private Server server;

    public static void main(String[] args) {
        new ServerDemo().start();
    }

    private void start() {

        try {
            System.out.println("server start at port:8081");
            server = ServerBuilder.forPort(8081).addService(new HelloServerImpl()).build().start();
            server.awaitTermination();
        } catch (IOException e) {
            e.printStackTrace();
            server.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private class HelloServerImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            System.out.println("=======================");
            System.out.println("server:call sayHello");
            System.out.println("server receive:"+request.getMessage());
            HelloResponse response = HelloResponse.newBuilder().setMessage("hello,I'm server,developed by akame.").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            System.out.println("=======================");
            if(request.getMessage().equals("exit")){
                server.shutdownNow();
            }
        }

    }
}
