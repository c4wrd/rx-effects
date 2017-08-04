package com.c4wrd.rxeffects;

import java.lang.reflect.Method;

public class EffectHandler
{

    private Method method;

    private boolean dispatch = false;

    public EffectHandler(Method method)
    {
        this.method = method;
    }

    public EffectHandler(Method method, boolean dispatch)
    {
        this.method = method;
        this.dispatch = dispatch;
    }

    public Method getMethod()
    {
        return method;
    }

    public boolean isDispatch()
    {
        return dispatch;
    }
}
