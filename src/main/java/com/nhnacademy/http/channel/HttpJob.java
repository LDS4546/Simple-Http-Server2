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

package com.nhnacademy.http.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CRL;
import java.util.Objects;

@Slf4j
public class HttpJob implements Executable {
    private final Socket client;
    private static final String CRLF="\r\n";

    public HttpJob(Socket client) {
        if(Objects.isNull(client)){
            throw new IllegalArgumentException("client Socket is null");
        }
        this.client = client;
    }

    public Socket getClient() {
        return client;
    }

    @Override
    public void execute(){

        //TODO#23 HttpJob는 execute() method를 구현 합니다. step2~3 참고하여 구현합니다.
        //<html><body><h1>thread-0:hello java</h1></body>
        //<html><body><h1>thread-1:hello java</h1></body>
        //<html><body><h1>thread-2:hello java</h1></body>
        //....

        while(true) {


            try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            ) {
                StringBuilder sb = new StringBuilder();
                String line;
                while(true){
                    line = br.readLine();
                    sb.append(line);
                    log.debug("{}", line);

                    if(line == null || line.isBlank()){
                        break;
                    }
                }




                StringBuilder responsebody = new StringBuilder();
                responsebody.append(String.format("<html><body><h1>%s:hello java</h1></body>", Thread.currentThread().getName()));


                StringBuilder responseHeader = new StringBuilder();

                responseHeader.append("HTTP/1.0 200 0K\r\n");
                responseHeader.append(String.format("Server: HTTP server/0.1%s", System.lineSeparator()));
                responseHeader.append(String.format("Content-type: text/html; charset=UTF-8%s", System.lineSeparator()));
                responseHeader.append(String.format("Connection: Closed%s", System.lineSeparator()));
                responseHeader.append(String.format("Content-Length: %d%s", responsebody.length(), System.lineSeparator()));

                bw.write(responseHeader.toString() + System.lineSeparator());
                bw.write(responsebody.toString());
                bw.flush();

                log.debug("header:{}", responseHeader);
                log.debug("body:{}", responsebody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
