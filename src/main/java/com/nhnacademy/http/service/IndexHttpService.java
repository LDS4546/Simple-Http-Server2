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

package com.nhnacademy.http.service;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class IndexHttpService implements HttpService{
    /*TODO#2 /index.html을  처리하는 HttpService 입니다.
        - doGet()method를 구현 합니다.
    */

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        //Body-설정
        String responseBody = null;
        
        //Header-설정
        String responseHeader = null;


        //PrintWriter 응답
        try(PrintWriter bufferedWriter = null){


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
