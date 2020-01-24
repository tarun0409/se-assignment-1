package javaargs.functional;

public class ArgTest {

	public static void main(String[] args) {
		SerializedArguments sa = new SerializedArguments("f,s*,n#,a##,p[*]", 
				"-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3");
//		SerializedArguments sa = new SerializedArguments();
//		sa.setAndStoreBooleanArgument('c', false);
//		System.out.println(sa.getBooleanArgument('c'));

	}

}
