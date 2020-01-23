package javaargs.functional;

public class ArgTest {

	public static void main(String[] args) {
		SerializedArguments sa = new SerializedArguments();
		sa.setAndStoreBooleanArgument('c', false);
		System.out.println(sa.getBooleanArgument('c'));

	}

}
