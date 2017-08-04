package com.c4wrd.rxeffects.data.effects;

import com.c4wrd.rxeffects.Effect;
import com.c4wrd.rxeffects.data.events.UserLoginEvent;
import io.reactivex.Observable;

public class UserEffects
{

    @Effect
    public void onUserLogin(Observable<UserLoginEvent> events) {
        events.subscribe(event -> {
            System.out.println(event.getUsername());
        });
    }

    /*@Effect(dispatch = true)
    public void onUserLoginMapperInvalid(Observable<UserLoginEvent> events) {
        //return events;
    }*/

}
