//package com.sparta.padoing.dto;
//
//import java.util.Map;
//
//public class GoogleResponse implements OAuth2Response{
//
//    private final Map<String, Object> attribute;
//
//    public GoogleResponse(Map<String, Object> attribute) {
//
//        this.attribute = attribute;
//    }
//
//    @Override
//    public String getProvider() {
//
//        return "google";
//    }
//
//    @Override
//    public String getProviderId() {
//
//        return attribute.get("sub").toString();
//    }
//
//    @Override
//    public String getEmail() {
//
//        return attribute.get("email").toString();
//    }
//
//    @Override
//    public String getName() {
//
//        return attribute.get("name").toString();
//    }
//}

//package com.sparta.padoing.dto;
//
//import java.util.Map;
//
//public class GoogleResponse implements OAuth2Response {
//
//    private final Map<String, Object> attribute;
//
//    public GoogleResponse(Map<String, Object> attribute) {
//        this.attribute = attribute;
//    }
//
//    @Override
//    public String getProvider() {
//        return "google";
//    }
//
//    @Override
//    public String getProviderId() {
//        return attribute.get("sub").toString();
//    }
//
//    @Override
//    public String getEmail() {
//        return attribute.get("email").toString();
//    }
//
//    @Override
//    public String getName() {
//        return attribute.get("name").toString();
//    }
//
//    public String getGivenName() {
//        return attribute.get("given_name").toString();
//    }
//
//    public String getRole() {
//        // Google 응답에 role 정보가 없는 경우를 고려하여 기본 값을 설정
//        return attribute.containsKey("role") ? attribute.get("role").toString() : "USER";
//    }
//}

package com.sparta.padoing.dto.response;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}