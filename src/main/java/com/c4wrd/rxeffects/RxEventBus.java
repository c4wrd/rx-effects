package com.c4wrd.rxeffects;

import com.c4wrd.rxeffects.exceptions.RxEventBusException;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import java.lang.reflect.*;

public class RxEventBus
{

    private Subject<Event> eventStream;

    public RxEventBus() {
        this.eventStream = BehaviorSubject.create();
    }

    public void dispatch(Event e) {
        this.eventStream.onNext(e);
    }

    public void install(Object effectsModule) throws RxEventBusException
    {
        try
        {
            Method[] methods = effectsModule.getClass().getMethods();
            for (Method method : methods)
            {
                Effect effect = method.getAnnotation(Effect.class);
                if (effect != null)
                {
                    EffectContext context = new EffectContext(effect, effectsModule, method);

                    if (effect.dispatch())
                    {
                        registerDispatchedEffectMethod(context, context.getEventClass());
                    }
                    else
                    {
                        registerEffectMethod(context, context.getEventClass());
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new RxEventBusException(String.format("Failed to install module, exception: %s", ex.toString()));
        }
    }

    private void registerEffectMethod(EffectContext context, Class eventParameterClass) throws InvocationTargetException, IllegalAccessException
    {
        Observable<Event> filteredEvents = this.eventStream.filter (e -> e.getClass() == eventParameterClass);
        context.getMethod().invoke(context.getModule(), filteredEvents);
    }

    private void registerDispatchedEffectMethod(EffectContext context, Class eventParameterClass) throws InvocationTargetException, IllegalAccessException
    {
        Observable<Event> filteredEvents = this.eventStream.filter (e -> e.getClass() == eventParameterClass);
        Observable<Event> dispatchedEvents = (Observable<Event>) context.getMethod().invoke(context.getModule(), filteredEvents);
        dispatchedEvents.subscribe(eventStream::onNext);
    }

}
