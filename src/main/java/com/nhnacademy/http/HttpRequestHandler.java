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
import java.util.Objects;

@Slf4j
/* TODO#6 Java에서 Thread는 implements Runnable or extends Thread를 이용해서 Thread를 만들 수 있습니다.
*  implements Runnable을 사용하여 구현 합니다.
*/
public class HttpRequestHandler implements Runnable{
    private Socket client;

    private final static String CRLF="\r\n";

    public HttpRequestHandler(Socket client) {
        //TODO#7 생성자를 초기화 합니다., cleint null or socket close 되었다면 적절히 Exception을 발생시킵니다.
        if(client == null || client.isClosed()){
            throw new IllegalArgumentException();
        }

        this.client = client;
    }


    public void run() {
        //TODO#8 exercise-simple-http-server-step1을 참고 하여 구현 합니다.
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
                    responsebody.append("<html>");
                    responsebody.append("<body>");
                    responsebody.append("<h1>hello java !</h1>");
                    responsebody.append("</body>");
                    responsebody.append("</html>");

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
