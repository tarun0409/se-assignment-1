package javaargs.functional;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class MapArgumentTest {

	@Test
	public void insertAndGetMapObject() {
		MapArgument mapArgument = new MapArgument();
		HashMap<String,String> map = new HashMap<String,String>();
		mapArgument.setValue(map);
		assertSame(map, mapArgument.getValue());
	}

}
