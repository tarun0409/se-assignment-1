package javaargs.functional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Constants {
	
	public static enum ArgumentType {
		BOOLEAN,
		INTEGER,
		DOUBLE,
		STRING,
		STRING_ARRAY,
		MAP
	}
	
	public static final Map<String, ArgumentType>  typeStringToArgumentTypeMap = 
		Collections.unmodifiableMap(new HashMap<String,ArgumentType>(){
			private static final long serialVersionUID = 1L;
			{
				put("", ArgumentType.BOOLEAN);
				put("*", ArgumentType.STRING);
				put("#", ArgumentType.INTEGER);
				put("##", ArgumentType.DOUBLE);
				put("[*]", ArgumentType.STRING_ARRAY);
				put("&", ArgumentType.MAP);
			}
	});

}
