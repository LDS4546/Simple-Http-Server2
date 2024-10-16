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

package com.nhnacademy.http.request;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpRequestImpl implements HttpRequest {
    /* TODO#2 HttpRequest를 구현 합니다.
    *  test/java/com/nhnacademy/http/request/HttpRequestImplTest TestCode를 실행하고 검증 합니다.
    */

    private final Socket client;
    private final Map<String, Object> headermap = new HashMap<>();
    private final Map<String, Object> attributeMap = new HashMap<>();
    private final static String KEY_HTTP_METHOD = "HTTP-METHOD";
    private final static String KEY_QUERY_PARAM_MAP = "HTTP-QUERY-PARAM-MAP";
    private final static String KEY_REQUEST_PATH="HTTP-REQUEST-PATH";
    private final static String HEADER_DELIMER=": ";


    public HttpRequestImpl(Socket client) {

        this.client = client;

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = bf.readLine();
            log.debug(line);
            String[] str = line.split(" ");


            headermap.put(KEY_HTTP_METHOD, str[0]);

            Map<String, String> param_map = new HashMap<>();
            if(str[1].contains("?")){
                String[] s = str[1].split("\\?");
                headermap.put(KEY_REQUEST_PATH, s[0]);

                String[] params = s[1].split("&");
                for(String string : params){
                    String[] params_s = string.split("=");
                    param_map.put(params_s[0], params_s[1]);
                }
                headermap.put(KEY_QUERY_PARAM_MAP, param_map);
            }else{
                headermap.put(KEY_REQUEST_PATH, str[1]);
            }


            while(true){

                line = bf.readLine();
                log.debug(line);
                if( line == null || line.isBlank()){
                    break;
                }

                String[] str2 = line.split(HEADER_DELIMER, 2);
                String key = str2[0];
                String value = str2[1];

                headermap.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;

    }

    @Override
    public String getMethod() {
        return String.valueOf(headermap.get(KEY_HTTP_METHOD));
    }

    @Override
    public String getParameter(String name) {
        return String.valueOf(getParameterMap().get(name));
    }

    @Override
    public Map<String, String> getParameterMap() {
        return (Map<String, String>) headermap.get(KEY_QUERY_PARAM_MAP);
    }

    @Override
    public String getHeader(String name) {
        return String.valueOf(headermap.get(name));
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributeMap.put(name,o);
    }

    @Override
    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }

    @Override
    public String getRequestURI() {
        return String.valueOf(headermap.get(KEY_REQUEST_PATH));
    }
}
