package com.nyanggle.nyangmail.oauth.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenRes {
    private String id_token;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String refresh_token_expires_in;
}
