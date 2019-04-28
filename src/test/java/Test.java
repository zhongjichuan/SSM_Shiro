import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test() {
		String url = "/a/b/c";
		String[] urlSplit = url.split("/");
		for(String s : urlSplit){
			System.out.println("==="+s);
		}
	}

}
