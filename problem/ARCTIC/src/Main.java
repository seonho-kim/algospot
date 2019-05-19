import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int T, N;									// Test-Case T, ������ ���� N
	static int[] uf = new int[100];						// union-find ���� �迭
	static Edge[] list = new Edge[4950];				// 2������ �̾� ������ ����� �ִ� ����� �� nC2 [100 Combination 2 = 100*99/2]
	static double[][] position = new double[100][2];	// ���� 100���� x, y��ǥ ���� �迭
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("./src/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			
			// x, y��ǥ�� ���� ����
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				double x = Double.parseDouble(st.nextToken());
				double y = Double.parseDouble(st.nextToken());
				
				position[i][0] = x;
				position[i][1] = y;
			}
			
			
			// list�迭�� �� ������ ���� �Ÿ���(����ġ)�� ����,  �Ÿ� : root[a^2 + b^2]
			int idx = 0;
			for (int i = 0; i < N - 1; i++) {
				for (int j = i+1; j < N; j++) {
					list[idx++] = new Edge(i, j, Math.sqrt(
							Math.pow(position[j][0]-position[i][0], 2)
							+ Math.pow(position[j][1]-position[i][1], 2)));
				}
			}
			
			Arrays.sort(list, 0, N*(N-1)/2);				// 0�� index���� N*(N-1)/2 index���� �������� ����
			
			Arrays.fill(uf, -1);							// union-find �迭�� -1������ �ʱ�ȭ
			double result = 0; int cnt = 0;
			for (int i = 0; ; i++) {
				if (union(list[i].u, list[i].v)) {			// union ����� ���ο� �θ� ����Ǹ�
					result = Math.max(result, list[i].w);	// ���� ����ġ ������� ���ο� ����ġ �� �ִ밪���� ������Ʈ
					if (++cnt == N - 1) break;				// MST�����ϱ� ���� Edge ���� N-1�� �Ǹ� ���� 
				}
			}
			bw.write(String.format("%.2f", result) + "\n");	// ǥ������ �Ҽ��� ��°�ڸ����� �ݿø�
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
	
	
	// ���Ľ� ����ġ w�� ��������
	@Override
	public int compareTo(Edge o) {
		return Double.valueOf(w).compareTo(o.w);
	}
	
}