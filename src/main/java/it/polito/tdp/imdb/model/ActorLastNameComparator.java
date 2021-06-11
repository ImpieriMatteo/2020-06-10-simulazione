package it.polito.tdp.imdb.model;

import java.util.Comparator;

public class ActorLastNameComparator implements Comparator<Actor> {

    @Override
    public int compare(Actor firstActor, Actor secondActor) {
       return firstActor.getLastName().compareTo(secondActor.getLastName());
    }

}
