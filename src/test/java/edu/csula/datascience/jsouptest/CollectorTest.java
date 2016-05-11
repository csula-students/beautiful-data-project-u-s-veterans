package edu.csula.datascience.jsouptest;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A test case to show how to use Collector and Source
 */
public class CollectorTest {
	private Collector<BasketballObject, BasketballObject> collector;
	private Source<BasketballObject> source;

	@Before
	public void setup() {
		collector = new MockCollector();
		source = new MockSource();
	}

	@Test
	public void mungee() throws Exception {
		List<BasketballObject> list = (List<BasketballObject>) collector
				.mungee(source.next());

		Assert.assertEquals(list.size(), 2);

		Assert.assertEquals(list.get(1).getTeam(), "Miami");

	}
}