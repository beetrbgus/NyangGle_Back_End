package com.nyanggle.nyangmail.oauth.convert;

public interface ModelConverter<Input,Output> {
    Output convert(Input source);
}
