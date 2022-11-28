package com.nyanggle.nyangmail.interfaces.convert;

public interface ModelConverter<Input,Output> {
    Output convert(Input source);
}
