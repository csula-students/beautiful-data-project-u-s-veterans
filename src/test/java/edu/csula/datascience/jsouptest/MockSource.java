package edu.csula.datascience.jsouptest;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class MockSource implements Source<BasketballObject> {
    int index = 0;

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<BasketballObject> next() {
        return Lists.newArrayList(
            new BasketballObject(),
            new BasketballObject("Golden St", 2016, 82,
            103.4, 0.0,
            0.0,0.0,0.0,
            0.0,0.0,0.0,
            0.0,0.0
            0.0,0.0,
            0.0,0.0,
            0.0,0.0,
            0.0,0.0,0.0,
            0.0,0.0,0.0,
            0.0,0.0,
            0.0,0.0,0.0),
            new BasketballObject("Golden St", 2016, 82, 
                103.4, 0.0,
                0.0,0.0,0.0,
                0.0,0.0,0.0,
                0.0,0.0
                0.0,0.0,
                0.0,0.0,
                0.0,0.0,
                0.0,0.0,0.0,
                0.0,0.0,0.0,
                0.0,0.0,
                0.0,0.0,0.0),
            new BasketballObject("Miami", 2016, 82,
                101.4, 0.0,
                0.0,0.0,0.0,
                0.0,0.0,0.0,
                0.0,0.0
                0.0,0.0,
                0.0,0.0,
                0.0,0.0,
                0.0,0.0,0.0,
                0.0,0.0,0.0,
                0.0,0.0,
                0.0,0.0,0.0)
        );
    }
}
