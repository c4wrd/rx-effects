package com.c4wrd.rxeffects;

import com.c4wrd.rxeffects.data.events.UserLoginEvent;
import io.reactivex.Observable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RxEventBusValidTests
{

    @Test
    public void testRegisterValidEffects() {

        class ValidEffects {

            private int eventCount = 0;

            @Effect
            public void userLoginEffects(Observable<UserLoginEvent> event) {
                event.subscribe(e -> {
                    eventCount++;
                });
            }

        }

        ValidEffects effects = new ValidEffects();
        RxEventBus bus = new RxEventBus();
        bus.install(effects);
        bus.dispatch(new UserLoginEvent());
        assertEquals(1, effects.eventCount);
        bus.dispatch(new UserLoginEvent());
        assertEquals(2, effects.eventCount);
    }

}
