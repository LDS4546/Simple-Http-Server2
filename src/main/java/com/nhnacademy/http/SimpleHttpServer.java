/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.http;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;

    private final AtomicLong atomicCounter;

    public SimpleHttpServer(){
        this(DEFAULT_PORT);
    }
    public SimpleHttpServer(int port) {
        //TODO#9 port <=0 이면 IllegalArgumentException 발생합니다. 적절한 Error Message를 작성하세요.

        //TODO#10 port와 atomicCounter를 초기화 합니다.
        this.port = 0;
        atomicCounter = null;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port);){

            HttpRequestHandler httpRequestHandlerA = new HttpRequestHandler();
            HttpRequestHandler httpRequestHandlerB = new HttpRequestHandler();

            //TODO#11 threadA를 생성하고 시작 합니다. thread-name : threadA 설정 합니다.
            Thread threadA = null;

            //TODO#12 threadB를 생성하고 시작 합니다. thread-name: threadB 설정 합니다.
            Thread threadB = null;

            while(true){
                Socket client = serverSocket.accept();
                /*TODO#13 count값이 짝수이면 httpRequestHandlerA에 client를 추가 합니다.
                          count값이 홀수라면 httpRequestHandlerB에 clinet를 추가 합니다.
                */

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
