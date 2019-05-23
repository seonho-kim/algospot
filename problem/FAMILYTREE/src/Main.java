import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int C, N, Q;
	static final int MAX = 100000;
	static int[][] parent;
	static int[] depth;
	static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("./src/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		C = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= C; test_case++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());
			
			parent = new int[MAX][15];
			depth = new int[MAX];
			adj = new ArrayList[MAX];
			
			for (int i = 0; i < N; i++)
				adj[i] = new ArrayList<Integer>();
			
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i < N; i++) {
				int u = Integer.parseInt(st.nextToken());
				adj[u].add(i);
				adj[i].add(u);
			}
			
			for (int i = 0; i < N; i++)
				Arrays.fill(parent[i], -1);
			Arrays.fill(depth, -1);
			depth[0] = 1;
			
			MakeTreeByDFS(0);
			
			for (int j = 0; j < 15; j++)
				for (int i = 1; i < N; i++)
					if (parent[i][j] != -1)
						parent[i][j + 1] = parent[parent[i][j]][j];		
			
			
			int result = 0;
			for (int i = 0; i < Q; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int u, v, v1, v2;
				v1 = u = Integer.parseInt(st.nextToken());
				v2 = v = Integer.parseInt(st.nextToken());
				
				if (depth[u] < depth[v]) {
					int tmp = u;
					u = v;
					v = tmp;
				}
				
				int diff = depth[u] - depth[v];
				
				for (int j = 0; diff > 0; j++) {
					if ((diff & 1) == 1)
						u = parent[u][j];
					diff >>= 1;
				}
				
				if (u != v) {
					for (int j = 14; j >= 0; j--) {
						if ((parent[u][j] != -1) && (parent[u][j] != parent[v][j])) {
							u = parent[u][j];
							v = parent[v][j];
						}
					}
					u = parent[u][0];
				}
				result = depth[v1] + depth[v2] - 2*depth[u];
				sb.append(result + "\n");
			}
		}
		bw.write(sb.toString());
		bw.flush();
	}
	
	static void MakeTreeByDFS(int curr) {
		for (int next : adj[curr]) {
			if (depth[next] == -1) {
				parent[next][0] = curr;
				depth[next] = depth[curr] + 1;
				MakeTreeByDFS(next);
			}
		}
	}
}