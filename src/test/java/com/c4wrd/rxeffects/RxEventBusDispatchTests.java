package com.c4wrd.rxeffects;

import com.c4wrd.rxeffects.data.events.UserLoginEvent;
import com.c4wrd.rxeffects.exceptions.InvalidEffectMethodException;
import io.reactivex.Observable;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RxEventBusDispatchTests
{

    @Test
    public void testDispatchVoidReturnType() {

        class InvalidEffects {
            @Effect(dispatch = true)
            public void hiddenMethod(Observable<UserLoginEvent> event) {

            }
        }

        RxEventBus bus = new RxEventBus();
        try
        {
            bus.install(new InvalidEffects());
            fail();
        } catch (InvalidEffectMethodException e) {

        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testDispatchShouldDispatch() {

        class UserLoginGreetingEvent extends Event {

        }

        class DispatchEffectsModule {

            private boolean dispatchedEventReceived = false;

            @Effect(dispatch = true)
            public Observable<UserLoginGreetingEvent> dispatcher(Observable<UserLoginEvent> stream) {
                return stream.map(e -> new UserLoginGreetingEvent());
            }

            @Effect
            public void dispatchHandler(Observable<UserLoginGreetingEvent> stream) {
                stream.subscribe(e -> dispatchedEventReceived = true);
            }

        }

        DispatchEffectsModule module = new DispatchEffectsModule();
        RxEventBus bus = new RxEventBus();
        bus.install(module);
        bus.dispatch(new UserLoginEvent());
        assertTrue(module.dispatchedEventReceived);
    }

}
