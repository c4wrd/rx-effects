package com.c4wrd.rxeffects;

import com.c4wrd.rxeffects.exceptions.InvalidEffectMethodException;
import com.c4wrd.rxeffects.exceptions.InvalidEffectParameterException;
import com.c4wrd.rxeffects.exceptions.RxEventBusException;
import io.reactivex.Observable;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class EffectContext
{

    Effect effect;

    Object module;

    Class moduleClass;

    Method method;

    Class eventClass;

    public EffectContext(Effect effect, Object module, Method method) throws RxEventBusException
    {
        this.effect = effect;
        this.module = module;
        this.moduleClass = module.getClass();
        this.method = method;
        init();
    }

    public Class getEventClass()
    {
        return eventClass;
    }

    public Method getMethod()
    {
        return method;
    }

    public Object getModule()
    {
        return module;
    }

    public void init() throws RxEventBusException {

        // verify parameter
        if ( method.getParameterCount() != 1 )
        {
            throw new InvalidEffectParameterException(this.toString());
        }

        ParameterizedType eventParameter;
        try
        {
             eventParameter = (ParameterizedType) method.getGenericParameterTypes()[0];
        } catch (ClassCastException ex) {
            throw new InvalidEffectParameterException(this.toString());
        }

        if ( eventParameter.getActualTypeArguments().length != 1 )
        {
            throw new InvalidEffectParameterException(this.toString());
        }

        this.eventClass = (Class) eventParameter.getActualTypeArguments()[0];

        // verify type is Observable<Event>
        if ( !eventParameter.getRawType().getClass().isInstance(Observable.class)
                || !(Event.class.isAssignableFrom(eventClass)) )
        {
            throw new InvalidEffectParameterException(this.toString());
        }

        // verify return type if dispatched effect
        if ( effect.dispatch() )
        {
            // TODO determine if we can check type paramter to verify we're not dispatching the same action and causing a loop
            if ( !Observable.class.isAssignableFrom(method.getReturnType()) )
            {
                throw new InvalidEffectMethodException(String.format("Invalid return type %s for %s. Must return Observable<? extends Event>.", method.getReturnType(), this.toString()));
            }
        }
    }

    @Override
    public String toString()
    {
        return String.format("%s.%s", moduleClass.getName(), method.getName());
    }
}
