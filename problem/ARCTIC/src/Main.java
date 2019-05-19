import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int T, N;									// Test-Case T, 기지의 개수 N
	static int[] uf = new int[100];						// union-find 저장 배열
	static Edge[] list = new Edge[4950];				// 2기지를 이어 간선을 만드는 최대 경우의 수 nC2 [100 Combination 2 = 100*99/2]
	static double[][] position = new double[100][2];	// 기지 100개의 x, y좌표 저장 배열
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("./src/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			
			// x, y좌표의 값을 저장
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				double x = Double.parseDouble(st.nextToken());
				double y = Double.parseDouble(st.nextToken());
				
				position[i][0] = x;
				position[i][1] = y;
			}
			
			
			// list배열에 두 기지를 이은 거리값(가중치)를 저장,  거리 : root[a^2 + b^2]
			int idx = 0;
			for (int i = 0; i < N - 1; i++) {
				for (int j = i+1; j < N; j++) {
					list[idx++] = new Edge(i, j, Math.sqrt(
							Math.pow(position[j][0]-position[i][0], 2)
							+ Math.pow(position[j][1]-position[i][1], 2)));
				}
			}
			
			Arrays.sort(list, 0, N*(N-1)/2);				// 0번 index부터 N*(N-1)/2 index까지 오름차순 정렬
			
			Arrays.fill(uf, -1);							// union-find 배열에 -1값으로 초기화
			double result = 0; int cnt = 0;
			for (int i = 0; ; i++) {
				if (union(list[i].u, list[i].v)) {			// union 연산시 새로운 부모 연결되면
					result = Math.max(result, list[i].w);	// 기존 가중치 결과값과 새로운 가중치 중 최대값으로 업데이트
					if (++cnt == N - 1) break;				// MST만족하기 위해 Edge 수가 N-1이 되면 종료 
				}
			}
			bw.write(String.format("%.2f", result) + "\n");	// 표시형식 소숫점 셋째자리에서 반올림
		}
		bw.flush();
	}
	static boolean union(int a, int b) {
		a = find(a);
		b = find(b);
		if (a == b) return false;
		uf[b] = a;
		return true;
	}
	static int find(int a) {
		if (uf[a] < 0) return a;
		return uf[a] = find(uf[a]);
	}
}
class Edge implements Comparable<Edge> {
	int u, v;
	double w;
	Edge() {
		this.u = -1;
		this.v = -1;
		this.w = 0;
	}
	Edge(int u, int v, double w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	
	// 정렬시 가중치 w로 오름차순
	@Override
	public int compareTo(Edge o) {
		return Double.valueOf(w).compareTo(o.w);
	}
	
}