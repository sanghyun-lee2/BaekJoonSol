import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
문제
송도에 사는 상근이와 친구들은 송도에서 열리는 펜타포트 락 페스티벌에 가려고 한다. 올해는 맥주를 마시면서 걸어가기로 했다. 출발은 상근이네 집에서 하고, 맥주 한 박스를 들고 출발한다. 맥주 한 박스에는 맥주가 20개 들어있다. 목이 마르면 안되기 때문에 50미터에 한 병씩 마시려고 한다. 즉, 50미터를 가려면 그 직전에 맥주 한 병을 마셔야 한다.
상근이의 집에서 페스티벌이 열리는 곳은 매우 먼 거리이다. 따라서, 맥주를 더 구매해야 할 수도 있다. 미리 인터넷으로 조사를 해보니 다행히도 맥주를 파는 편의점이 있다. 편의점에 들렸을 때, 빈 병은 버리고 새 맥주 병을 살 수 있다. 하지만, 박스에 들어있는 맥주는 20병을 넘을 수 없다. 편의점을 나선 직후에도 50미터를 가기 전에 맥주 한 병을 마셔야 한다.
편의점, 상근이네 집, 펜타포트 락 페스티벌의 좌표가 주어진다. 상근이와 친구들이 행복하게 페스티벌에 도착할 수 있는지 구하는 프로그램을 작성하시오.
입력
첫째 줄에 테스트 케이스의 개수 t가 주어진다. (t ≤ 50)
각 테스트 케이스의 첫째 줄에는 맥주를 파는 편의점의 개수 n이 주어진다. (0 ≤ n ≤ 100).
다음 n+2개 줄에는 상근이네 집, 편의점, 펜타포트 락 페스티벌 좌표가 주어진다. 각 좌표는 두 정수 x와 y로 이루어져 있다. (두 값 모두 미터, -32768 ≤ x, y ≤ 32767)
송도는 직사각형 모양으로 생긴 도시이다. 두 좌표 사이의 거리는 x 좌표의 차이 + y 좌표의 차이 이다. (맨해튼 거리)
출력
각 테스트 케이스에 대해서 상근이와 친구들이 행복하게 페스티벌에 갈 수 있으면 "happy", 중간에 맥주가 바닥나서 더 이동할 수 없으면 "sad"를 출력한다. 
*/

public class BOJ_9205_맥주마시면서걸어가기 {
	static int N; // 편의점 갯수
	static int[][] mapDistance; // 거리정보 맵
	
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("input.txt"));
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt(); // 테스트 케이스 수
		
		for(int t = 1; t <= T; t++) {
			N = sc.nextInt(); // 편의점 갯수
			
			mapDistance = new int[N + 2][N + 2]; // 상근이네 집, 락페스티벌, 편의점 거리 맵
			
			int[] PointX = new int[N + 2]; // X 위치
			int[] PointY = new int[N + 2]; // Y 위치
		
			PointX[0] = sc.nextInt(); // 상근이네 X위치 설정
			PointY[0] = sc.nextInt(); // 상근이네 Y위치 설정
			
			for(int i = 1; i < N + 1; i++) {
				PointX[i] = sc.nextInt(); // 편의점 X위치 설정
				PointY[i] = sc.nextInt(); // 편의점 Y위치 설정
			}
			
			PointX[N + 1] = sc.nextInt(); // 락페 X위치 설정
			PointY[N + 1] = sc.nextInt(); // 락페 Y위치 설정
			
			for(int i = 0; i < N + 2; i++) {
				for(int j = 0; j < N + 2; j++) {
					if(i == j) { // 같은 위치는 구할 필요 없음
						continue; 
					}
					// 상근이네 집, 락페스티벌, 편의점 거리 맵 사이의 거리를 구해서 거리 정보 2차원 맵에 추가
					mapDistance[i][j] = Math.abs(PointX[j] - PointX[i]) + Math.abs(PointY[j] - PointY[i]);
					
					if(mapDistance[i][j] <= 1000) { // 거리가 1000m 이하면 비용이 0으로 설정(맥주먹으면서 이동가능..)
						mapDistance[i][j] = 0;
					}
				}
			}
			
			// 플로이드-와샬 알고리즘을 통해 최소비용 경로 구함
			for(int k = 0; k < N + 2; k++ ) { //경유지 k
	            for(int i = 0; i < N + 2; i++) {//출발지  i
	                if(k == i) {
	                    continue;
	                }
	                for(int j = 0; j < N + 2; j++) {//도착지 j
	                    if(j == i || j == k) {
	                        continue;
	                    }
	                    if(mapDistance[i][j] > mapDistance[i][k] + mapDistance[k][j]) {
	                    	mapDistance[i][j] = mapDistance[i][k] + mapDistance[k][j];
	                    }
	                }
	            }
	        }
			
			// 상근이네 집에서 락페 까지 거리비용이 1000이하이면 도착가능
			if(mapDistance[0][N + 1] <= 1000) {
				System.out.println("happy");
			}else {
				System.out.println("sad");
			}
		}
		
		
		sc.close();
	}
}
