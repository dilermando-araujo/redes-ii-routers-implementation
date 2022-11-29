package app;

public class LogHelper {

	public static int log2(int N) {
		int result = (int) Math.ceil(Math.log(N) / Math.log(2));
        return result;
    }
}
