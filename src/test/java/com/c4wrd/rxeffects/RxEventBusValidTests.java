package com.c4wrd.rxeffects;

import com.c4wrd.rxeffects.data.events.UserLoginEvent;
import io.reactivex.Observable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    @Test
    public void testPayload() {

        class PayloadEvent extends Event {
            @Override
            Object getPayload()
            {
                return "payload example!";
            }
        }

        class exampleAllEffects {

            @Effect
            public void effect(Observable<Event> stream) {
                stream.subscribe(e -> {
                    if ( e instanceof PayloadEvent ) {
                        assertEquals("payload example!", e.getPayload());
                    }
                });
            }

        }

        RxEventBus bus = new RxEventBus();
        bus.install(new exampleAllEffects());
        bus.dispatch(new PayloadEvent());

    }

}
