package com.gentlehu.rpcdemo.test;

import com.gentlehu.rpcdemo.grpc.HelloRequest;
import com.gentlehu.rpcdemo.grpc.HelloResponse;
import com.gentlehu.rpcdemo.grpc.HelloWorldServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by gentle-hu on 2018/10/29 15:59.
 * Email:me@gentlehu.com
 */

public class ClientDemo {

    private final ManagedChannel channel;
    private final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub blockingStub;

    public ClientDemo(String host,int port) {
        channel = ManagedChannelBuilder.forAddress(host,port).usePlaintext().build();
        blockingStub = HelloWorldServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public HelloResponse sayHello(String message){
        HelloRequest request = HelloRequest.newBuilder().setMessage(message).build();
        HelloResponse response = blockingStub.sayHello(request);
        System.out.println("================================");
        System.out.println("response from server:"+response.getMessage());
        System.out.println("================================");
        return response;
    }

    public static void main(String[] args) throws InterruptedException {
        ClientDemo client = new ClientDemo("127.0.0.1", 8081);
        HelloResponse response = client.sayHello("hello,I'm akame -- from client");
        System.out.println(response.getMessage());
        client.shutdown();
    }
}
