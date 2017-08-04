package com.c4wrd.rxeffects.exceptions;

public class InvalidEffectParameterException extends InvalidEffectMethodException
{

    public InvalidEffectParameterException(String id) {
        super(String.format("Invalid effect %s. Effects must have one parameter of type Observable<? extends Event>", id));
    }

}
