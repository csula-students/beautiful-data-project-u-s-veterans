package edu.csula.datascience.jsouptest;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A test case to show how to use Collector and Source
 */
public class CollectorTest {
    private Collector<BasketballObject, BasketballObject> collector;
    private Source<BasetballObject> source;

    @Before
    public void setup() {
        collector = new MockCollector();
        source = new MockSource();
    }

    @Test
    public void mungee() throws Exception {
        List<BasketballObject> list = (List<BasketballObject>) collector.mungee(source.next());

        Assert.assertEquals(list.size(), 2);

        Assert.assertEquals(list.get(1).getTeam(), "Miami");

        }
    }
}