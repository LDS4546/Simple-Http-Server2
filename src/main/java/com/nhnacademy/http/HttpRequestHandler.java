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
import java.net.Socket;
import java.security.cert.CRL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final Queue<Socket> requestQueue;
    private final int MAX_QUEUE_SIZE=10;

    private static final String CRLF="\r\n";

    public HttpRequestHandler() {
        //TODO#1 requestQueue를 초기화 합니다. Java에서 Queue의 구현체인 LinkedList를 사용 합니다.
        requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(Socket client){

        /* TODO#2 queueSize >= MAX_QUEUE_SIZE 대기 합니다.
            즉 queue에 데이터가 소비될 때 까지 client Socket을 Queue에 등록하는 작업을 대기 합니다.
        */
        if(requestQueue.size() >= MAX_QUEUE_SIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        //TODO#3 requestQueue에 client를 추가 합니다.
        requestQueue.add(client);


        //TODO#4 대기하고 있는 Thread를 깨웁니다.
        notifyAll();

    }

    public synchronized Socket getRequest(){

        //TODO#5 requestQueue가 비어 있다면 대기 합니다.
        if(requestQueue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO#6 대기하고 있는 Thread를 깨우고, requestQueue에서 client를 반환 합니다.
        notify();
        return requestQueue.poll();
    }

    @Override
    public void run() {

        while(true) {
            //TODO#7 getRequest()를 호출하여 client를 requestQueue로 부터 얻습니다., requestQueue가 비어있다면 대기 합니다.
            Socket client = getRequest();

            //TODO#8 다음과 같은 message가 응답되도록 구현 합니다. exercise-step2를 참고하세요
            //<html><body><h1>{threadA}:hello java</h1></body></html>
            //<html><body><h1>{threadB}:hello java</h1></body></html>
            StringBuilder requestBuilder = new StringBuilder();




            try(BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            ){
                log.debug("------HTTP-REQUEST-START()");
                while (true){
                    String line = bf.readLine();
                    requestBuilder.append(line);
                    log.debug("{}", line);

                    if(Objects.isNull(line) || line.isBlank()){
                        break;
                    }
                }
                log.debug("------HTTP-REQUEST-END()");

                StringBuilder responsebody = new StringBuilder();
                responsebody.append(String.format("<html><body><h1>{%s}:hello java</h1></body></html>", Thread.currentThread().getName()));


                StringBuilder responseHeader = new StringBuilder();

                responseHeader.append("HTTP/1.0 200 0K\r\n");
                responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));
                responseHeader.append(String.format("Content-type: text/html; charset=UTF-8%s",System.lineSeparator()));
                responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));
                responseHeader.append(String.format("Content-Length: %d%s", responsebody.length(), System.lineSeparator()));


                bw.write(responseHeader.toString()+System.lineSeparator());
                bw.write(responsebody.toString());

                bw.flush();

                log.debug("header:{}",responseHeader);
                log.debug("body:{}",responsebody);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        }

}
