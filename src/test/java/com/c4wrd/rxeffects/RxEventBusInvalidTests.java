package com.c4wrd.rxeffects;

import com.c4wrd.rxeffects.data.events.UserLoginEvent;
import com.c4wrd.rxeffects.exceptions.InvalidEffectParameterException;
import io.reactivex.Observable;
import org.junit.Test;
import static org.junit.Assert.*;

public class RxEventBusInvalidTests
{

    @Test
    public void testInvalidParameter() {

        class InvalidEffects {
            @Effect
            public void invalidParameterType(UserLoginEvent stream) {

            }
        }

        RxEventBus bus = new RxEventBus();
        try
        {
            bus.install(new InvalidEffects());
            fail();
        } catch (InvalidEffectParameterException e) {

        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testNonEventParameter() {
        class InvalidEffects {
            @Effect
            public void nonEventParameter(Observable<String> stream) {

            }
        }

        RxEventBus bus = new RxEventBus();
        try
        {
            bus.install(new InvalidEffects());
            fail();
        } catch (InvalidEffectParameterException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEffectMethodHasEmptyParameters() {

        class InvalidEffects {
            @Effect
            public void emptyParameterEffect() {

            }
        }

        RxEventBus bus = new RxEventBus();
        try
        {
            bus.install(new InvalidEffects());
            fail();
        } catch (InvalidEffectParameterException e) {

        } catch (Exception e) {
            fail();
        }
    }

}
