import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static int C, N, Q;
	static final int MAX = 100001;
	static int[] maxt;
	static int[] mint;
	static int h, n, MAXN;
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("./src/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		C = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= C; test_case++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());
			
			h = (int)Math.ceil(Math.log(N) / Math.log(2));
			n = (int)Math.pow(2, h);
			MAXN = (int)Math.pow(2, h + 1);
			
			maxt = new int[MAXN + 1];
			mint = new int[MAXN + 1];
			
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				int value = Integer.parseInt(st.nextToken());
				maxt[i + n] = mint[i + n] = value;
			}
			
			for (int i = n + N; i <= MAXN; i++) {
				mint[i] = 20001;
			}
			
			for (int i = n - 1; i > 0; i--) {
				maxt[i] = Math.max(maxt[i<<1], maxt[i<<1|1]);
				mint[i] = Math.min(mint[i<<1], mint[i<<1|1]);
			}
			
//			for (int i = 1; i <= MAXN; i++)
//				System.out.print(maxt[i] + " ");
//			System.out.println();
//			for (int i = 1; i <= MAXN; i++)
//				System.out.print(mint[i] + " ");
//			System.out.println();
			
			for (int i = 1; i <= Q; i++) {
				int l, r;
				st = new StringTokenizer(br.readLine(), " ");
				l = Integer.parseInt(st.nextToken());
				r = Integer.parseInt(st.nextToken());
				
				bw.write(Math.max(queryMax(l, r), maxt[r+n]) - Math.min(queryMin(l, r), mint[r+n]) + "\n");
			}
		}
		bw.flush();
	}
	
	static int queryMax(int l, int r) {
		int res = 0;
		for (l+=n, r+=n; l<r; l>>=1, r>>=1) {
			if ((l&1) == 1) res = Math.max(res, maxt[l++]);
			if ((r&1) == 1) res = Math.max(res, maxt[--r]);
		}
		return res;
	}
	
	static int queryMin(int l, int r) {
		int res = 20001;
		for (l+=n, r+=n; l<r; l>>=1, r>>=1) {
			if ((l&1) == 1) res = Math.min(res, mint[l++]);
			if ((r&1) == 1) res = Math.min(res, mint[--r]);
		}
		return res;
	}
}