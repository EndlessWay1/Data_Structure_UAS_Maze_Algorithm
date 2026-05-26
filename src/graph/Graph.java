package graph;

/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Edge<T> { 
	private T neighbor; //connected vertex
	private int weight; //weight
	
	//Constructor, Time O(1) Space O(1)
	public Edge(T v, int w) {
		this.neighbor = v; 
		this.weight = w;
	}

	public void setNeighbor(T neighbor) {
		this.neighbor = neighbor;
	}
	public T getNeighbor() {
		return neighbor;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}
	
	//Time O(1) Space O(1)
	@Override
	public String toString() {
		return "(" + neighbor + "," + weight + ")";
	}
}

public class Graph<T> { 
    //Map<T, LinkedList<Edge<T>>> adj;
	private Map<T, MyLinearList<Edge<T>>> adj;
	boolean directed;
	private int vertex_length;
	private int edge_length;
	
	public int getVLength(){
		return vertex_length;
	}

	public int getELength(){
		return edge_length;
	}

	//Constructor, Time O(1) Space O(1)
	public Graph (boolean type) { 
        adj = new HashMap<>();
		directed = type; // false: undirected, true: directed
		vertex_length = 0;
		edge_length = 0;
	}

	private boolean containsEdge(T a, T b){
		MyLinearList<Edge<T>> root = adj.get(a);
		if (root == null){
			return false;
		}
		Node<Edge<T>> head = root.head;
		while(head != null){
			T neigh = head.getData().getNeighbor();
			if (Objects.equals(neigh, b)){
				return true;
			}
			head = head.getNext();
		}
		return false;
	}

    //Add edges including adding nodes, Time O(1) Space O(1)
	public void addEdge(T a, T b, int w) {
		if (!adj.containsKey(a)){
			vertex_length +=1;
			adj.put(a, new MyLinearList<>()); //add node
		}
		if (!adj.containsKey(b)){
			vertex_length +=1;
			adj.put(b, new MyLinearList<>()); //add node
		}
		if (!containsEdge(a, b)){
			adj.get(a).pushQ(new Edge<>(b, w));//add(edge1); //add edge
			edge_length +=1;	
		}
		
		if (!directed && !containsEdge(b, a)) { //undirected
			adj.get(b).pushQ(new Edge<>(a, w));//add(edge1); //add edge
			edge_length++;
		}

	}


	public void deleteVertex(T src){
		if (!adj.containsKey(src)) return;

		adj.remove(src);
		vertex_length--;
		for (T key: adj.keySet()){
			deleteEdge(key, src);
		}
	}

	// }
	public void deleteEdge(T a1, T a2){
		if (!adj.containsKey(a1)) return;
		if (!adj.containsKey(a2)) return;

		MyLinearList<Edge<T>> a = adj.get(a1);

		Node<Edge<T>> root = a.head;

		while(root != null){
			Edge<T> neighbor = root.getData();
			if (a2 == neighbor.getNeighbor()){
				a.remove(neighbor);
				edge_length--;
				if (!directed){
					deleteEdge(a2, a1);
				}
				return;
			}
			root = root.getNext();
		}
	}



    //Print graph as hashmap, Time O(V+E), Space O(1)
	public void printGraph() {
		for (T key: adj.keySet()) {
			//System.out.println(key.toString() + " : " + adj.get(key).toString());
            System.out.print(key.toString() + " : ");
			MyLinearList<Edge<T>> thelist = adj.get(key);
			Node<Edge<T>> curr = thelist.head;
			while(curr != null) {
				System.out.print(curr.getData());
				curr = curr.getNext();
			}
			System.out.println();
		}
	}

	//DFS 
	public void DFS(T src) {
		Map<T,Integer> a  = new HashMap<>();
		dfs(a, src);
		System.out.println();
	}

	private void dfs(Map<T,Integer> a, T src){
		if (!adj.containsKey(src)) return;

		if (a.containsKey(src)) return;

		System.out.print(src + " ");

		MyLinearList<Edge<T>> aa = adj.get(src);

		a.put(src, 1);
		Node<Edge<T>> root = aa.head;
		while(root != null){
			dfs(a, root.getData().getNeighbor());
			root = root.getNext();
		}

	}

	//BFS
	public void BFS(T src) { 
		
		// make queue curr node
		MyLinearList<Edge<T>> a = adj.get(src);

		MyLinearList<MyLinearList<Edge<T>>> q = new MyLinearList<>();

		Map<T, Integer> visited = new HashMap<>();
		visited.put(src, 1);
		System.out.print(src + " ");

		q.pushQ(a);

		while(!q.isEmpty()){
			MyLinearList<Edge<T>> curr_node = q.remove();

			Node<Edge<T>> root = curr_node.head;
			
			while(root != null){
				if (!visited.containsKey(root.getData().getNeighbor())){
					T neighbor = root.getData().getNeighbor();
					System.out.print(neighbor + " ");
					visited.put(neighbor, 1);
					q.pushQ(adj.get(neighbor));
				}
				
				root = root.getNext();	
			}

		}

		System.out.println();

	}

	// first n second attempt
	// public void shortestPath(T start){
    //     MyLinearList<T> path = new MyLinearList();
	// 	Heap<Integer, Edge<T>> min_heap = new Heap<>(edge_length + vertex_length, true);

	// 	int sums = 0;
	// 	Map<T, Integer> visited = new HashMap<>();
	// 	Map<T, String> pathMap = new HashMap<>();
	// 	visited.put(start, 0);
	// 	pathMap.put(start, start.toString());

	// 	MyLinearList<Edge<T>> root = adj.get(start);
	// 	Node<Edge<T>> r = root.head;
	// 	while (r != null){
	// 		min_heap.insert(
	// 			(r.getData().getWeight()), 
	// 			r.getData());
	// 		visited.put(r.getData().getNeighbor(), r.getData().getWeight());
	// 		pathMap.put(r.getData().getNeighbor(), start.toString() + "-->" + r.getData().getNeighbor().toString());

	// 		r = r.getNext();
	// 	}
	// 	while (min_heap.size() != 0){
	// 		BTNode<Integer, Edge<T>> res = min_heap.removeFirst();
	// 		Edge<T> new_edge = res.getData();
	// 		Integer weights_edge = res.getKey();
	// 		String curr_path = pathMap.get(res.getData().getNeighbor());
			
	// 		MyLinearList<Edge<T>> curr = adj.get(new_edge.getNeighbor());
	// 		Node<Edge<T>> curr_Iter = curr.head;
	// 		while(curr_Iter != null){
	// 			if (!visited.containsKey(curr_Iter.getData().getNeighbor())){
	// 				min_heap.insert(weights_edge + curr_Iter.getData().getWeight(), curr_Iter.getData());
	// 				visited.put(curr_Iter.getData().getNeighbor(), weights_edge + curr_Iter.getData().getWeight());
	// 				pathMap.put(curr_Iter.getData().getNeighbor(), curr_path + "-->" + curr_Iter.getData().getNeighbor().toString());
	// 			}
	// 			else if (visited.get(curr_Iter.getData().getNeighbor()) > weights_edge + curr_Iter.getData().getWeight() ){
	// 				visited.put(curr_Iter.getData().getNeighbor(), weights_edge + curr_Iter.getData().getWeight());
	// 				pathMap.put(curr_Iter.getData().getNeighbor(), curr_path + "-->" + curr_Iter.getData().getNeighbor().toString());
	// 			}

	// 			curr_Iter = curr_Iter.getNext();
	// 		} 

	// 	}
	// 	for (T key : pathMap.keySet()) {
	// 		System.out.println(start + " --> " + key);
	// 		System.out.println("Cost: " + visited.get(key));
	// 		System.out.println("Path: " + pathMap.get(key));
	// 		System.out.println();
	// 	}

	// 	// return visited.get(end);
    // }

	// private MyLinearList<T> copy_LL(MyLinearList<T> src){
	// 	Node<T> s = src.head;
	// 	MyLinearList<T> dst = new MyLinearList<>();
	// 	while(s != null){
	// 		dst.pushQ(s.getData());
	// 		s = s.getNext();
	// 	}
	// 	return dst;
	// }


	// public MyLinearList<T> shortestPath1(T start, T end){
	// 	if (!adj.containsKey(start) || !adj.containsKey(end)) return null;
	// 	Heap<Integer, T> min_heap = new Heap<>(vertex_length, true);
	// 	MyLinearList<Edge<T>> s = adj.get(start);
	// 	Map<T, Integer> T_weights = new HashMap<>();
	// 	Map<T, MyLinearList<T>> Paths = new HashMap<>(); 
	// 	T_weights.put(start, 0);

	// 	MyLinearList<T> start_path = new MyLinearList<>(); 
	// 	start_path.pushQ(start);
	// 	Paths.put(start, start_path);

	// 	Node<Edge<T>> r = s.head;
	// 	// adding the first edge
	// 	while(r != null){
	// 		Edge<T> edge = r.getData(); 

	// 		// add to min heap 
	// 		min_heap.add(edge.getWeight(), edge.getNeighbor()); 

	// 		// add weights 
	// 		T_weights.put(edge.getNeighbor(), edge.getWeight());

	// 		// adding path to Linked List and to Map
	// 		MyLinearList<T> copy = copy_LL(start_path);
	// 		copy.pushQ(edge.getNeighbor());
	// 		Paths.put(edge.getNeighbor(), copy);

	// 		r = r.getNext();
	// 	}

	// 	while(min_heap.size() > 0) {
	// 		BTNode<Integer, T> res = min_heap.removeFirst();
	// 		T curr_node = res.getData();
	// 		if (curr_node == end){
	// 			break;
	// 		}

			
	// 		Integer curr_weights = res.getKey();
			
	// 		if (curr_weights > T_weights.get(curr_node)){
    // 			continue;
	// 		}
	// 		// paths
	// 		MyLinearList<T> curr_paths = Paths.get(curr_node);

	// 		MyLinearList<Edge<T>> branch = adj.get(curr_node);
			
	// 		Node<Edge<T>> branch_node = branch.head;
	// 		while (branch_node != null){
	// 			T curr_branch_neighbor = branch_node.getData().getNeighbor();
	// 			int curr_branch_edge_weights = branch_node.getData().getWeight();
				
	// 			if (T_weights.containsKey(curr_branch_neighbor) && T_weights.get(curr_branch_neighbor) > curr_weights + curr_branch_edge_weights){ 
	// 				T_weights.put(curr_branch_neighbor, curr_weights + curr_branch_edge_weights);
	// 				min_heap.add(curr_weights + curr_branch_edge_weights, curr_branch_neighbor);

	// 				// adding path
	// 				MyLinearList<T> copy = copy_LL(curr_paths);
	// 				copy.pushQ(curr_branch_neighbor);
	// 				Paths.put(curr_branch_neighbor, copy);

	// 			}
	// 			else if (!T_weights.containsKey(curr_branch_neighbor)){
	// 				T_weights.put(curr_branch_neighbor, curr_weights + curr_branch_edge_weights);
	// 				min_heap.add(curr_weights + curr_branch_edge_weights, curr_branch_neighbor);
	// 				// adding path
	// 				MyLinearList<T> copy = copy_LL(curr_paths);
	// 				copy.pushQ(curr_branch_neighbor);
	// 				Paths.put(curr_branch_neighbor, copy);
	// 			}
	// 			branch_node = branch_node.getNext();
	// 		}
	// 	}


    //     return Paths.get(end);
    // }



	public BTNode<Integer,MyLinearList<T>> dijkstra(T src, T target){
		
		// map distance, such that when its unreacable, we know
		Map<T, Integer> distance = new HashMap<>();

		// writing the path history
		Map<T, T> path = new HashMap<>();
		
		// make min priority queue
		Heap<Integer, T> PQ = new Heap<>(vertex_length, true);
		
		
		distance.put(src, 0);
		PQ.add(0, src);

		while(PQ.size() > 0){
			BTNode<Integer, T> top = PQ.removeFirst();
			Integer curr_dis = top.getKey();
			T curr_vertex = top.getData();

			// if the distance is more than we need, it means there exist 
			// another path that has less cost than the current path 
			if (curr_dis > distance.get(curr_vertex)){
				continue;
			}

			// looping through the edges and verteces
			Node<Edge<T>> root = adj.get(curr_vertex).head;
			while (root != null){
				Edge<T> edge = root.getData();
				T n_vertex = edge.getNeighbor();
				Integer n_weights = edge.getWeight();

				// we have 2 assumsion
				// 1. assume n_vertex is not in map, thus has curr length of inf, 
				// thus we update the weights as its now reachable 
				// 2. assume n_vertex is in map, where n_vertex has finite length and is greater than curr_dis + n_weights 
				// this can be achived effectively by using an or
				Integer new_distance = curr_dis + n_weights;
				if (
					!distance.containsKey(n_vertex) ||
					distance.get(n_vertex) > new_distance
				){
					// update distance
					distance.put(n_vertex, new_distance);
					// update path
					path.put(n_vertex, curr_vertex);
					// insert to PQ
					PQ.insert(new_distance, n_vertex);
				}
				root = root.getNext();
			}
		}

		// backtracking
		MyLinearList<T> return_path = new MyLinearList<>();
		BTNode<Integer, MyLinearList<T>> ret;
		T curr = target; 
		if (path.containsKey(curr)){
			return_path.pushS(target);
			while (path.containsKey(curr)){
				T next = path.get(curr);
				return_path.pushS(next);
				curr = next;
			}
			ret = new BTNode<>(distance.get(target), return_path);
			return ret;
		}
		
		// target is unreachable
		ret = new BTNode<>(Integer.MAX_VALUE, return_path);
		return ret;
	}

	// Membuat path dari target kembali ke source 
	private MyLinearList<T> makePath(Map<T, T> path, T src, T target){
		
		MyLinearList<T> result = new MyLinearList<>(); // list untuk menyimpan path akhir
		// kalau source dan target sama, berarti kita sudah sampai tujuan, return list dengan source
		if ( Objects.equals(src, target)) {
			result.pushQ(src);
			return result;
		}
		// kalau target tidak punya parent, berarti tidak ada path
		if (!path.containsKey(target)) {
			return result;
		}
		// mulai backtraking dari target ke source
		T curr = target;
		result.pushS(curr); // masukan target ke list
		while (path.containsKey(curr)) { // selama curr masih punya parent
			curr = path.get(curr); // ambil parent dari node saat ini
			result.pushS(curr); // masukkan ke depan path
		}
		return result;
	}

	// A* Algorithm ( Heuristic + Dijkstra)
	private int heuristic( T curr, T target, int cols) {
		if (!(curr instanceof Integer) || !(target instanceof Integer)) {
			return 0; // sistemnya kalau bukan integer, kita anggap heuristicnya 0, soalnya vertex maze kita tipenya integer
		} 
		// ubah generic T ke integer
		int currNode = (Integer) curr;
		int targetNode = (Integer) target;
		// manhattan distance
		int currRow = currNode / cols; // ini mencari row dan col current
		int currCol = currNode % cols; // ini mencari row dan col current
		int targetRow = targetNode / cols; // ini mencari row dan col target
		int targetCol = targetNode % cols; // ini mencari row dan col target
		return Math.abs(currRow - targetRow) + Math.abs(currCol - targetCol);
	}

	// Dijkstra ( Output berupa cost, path, visited node, time )
	public BTNode<Integer, MyLinearList<T>> dijkstraReport(T src, T target ) {
		// catat waktu mulai
		long startTime = System.nanoTime();
		// Simpan jarak terpendek dari src ke setiap vertex
		Map<T, Integer> distance = new HashMap<>();
		// Simpan parent untuk membentuk path
		Map<T, T> path = new HashMap<>();
		// Simpan vertex yang sudah diproses
		Map<T, Integer> visited = new HashMap<>();
		// counter jumlah vertex yang sudah dikunjungi
		int visitedCount = 0;
		// Min Priority Queue ( capacity dibuat lebih besar karena node bisa masuk PQ lebih dari sekali )
		Heap<Integer, T> PQ = new Heap<>(edge_length + vertex_length + 10, true);
		// Jarak source ke dirinya sendiri adalah 0
		distance.put(src, 0);
		// Masukan source ke PQ
		PQ.insert(0, src);
		// Selama PQ belum kosong
		while (PQ.size() > 0) {
			BTNode<Integer, T> top = PQ.removeFirst(); // Ambil vertex dengan jarak terkecil
			Integer curr_dis = top.getKey(); // jarak current dari source
			T curr_vertex = top.getData(); // vertex sekarang
			// Jika vertex sudah diproses maka dilewati
			if (visited.containsKey(curr_vertex)) {
				continue;
			}
			// Tandai vertex sebagai sudah diproses
			visited.put(curr_vertex, 1);
			// Tandai jumlah visited node
			visitedCount++;
			// kalau sudah sampai target, berhenti
			if (Objects.equals(curr_vertex, target)) {
				break;
			}
			// ambil adjacency list dari vertex sekarang
			MyLinearList<Edge<T>> list = adj.get(curr_vertex);
			if ( list == null){ // kalau tidak punya tetangga, lanjut
				continue;
			}
			Node<Edge<T>> root = list.head; // iterasi tetangga
			while ( root != null ) {
				Edge<T> edge = root.getData(); // ambil edge
				T n_vertex = edge.getNeighbor(); // vertex tetangga
				Integer n_weights = edge.getWeight(); // bobot edge
				Integer new_distance = curr_dis + n_weights; // jarak baru = jarak current + bobot edge
				// jika tetangga belum punya distance atau jarak yang lebih pendek
				if ( !distance.containsKey(n_vertex) || distance.get(n_vertex) > new_distance ) {
					distance.put(n_vertex, new_distance); // update distance
					path.put(n_vertex, curr_vertex); // update path
					PQ.insert(new_distance, n_vertex); // masukan ke PQ 
				}
				// lanjut edge berikutnya
				root = root.getNext();
			}
		}
		// buat path akhir
		MyLinearList<T>return_path = makePath(path, src, target);
		// ambil cost akhir
		Integer cost;
		// kalau target ditemukan
		if ( distance.containsKey(target)) {
			cost = distance.get(target);
		}
		// kalau ga ketemu
		else {
			cost = Integer.MAX_VALUE;
		}
		// catat waktu selesai
		long endTime = System.nanoTime();
		// hitung durasi
		double duration = (endTime - startTime) / 1000000.0; // dalam milisekon
		// print hasil
		System.out.println("Dijkstra");
		System.out.println("Cost : " + cost);
		System.out.println("Visited Nodes : " + visitedCount);
		System.out.println("Time : " + duration + " ms");
		System.out.println("Path :");
		return_path.cetakList();
		return new BTNode<>(cost, return_path);
	}
	
	// A* ( Output sama dengan dijkstra)
	public BTNode<Integer, MyLinearList<T>> aStarReport (T src, T target, int cols) {
		// catat waktu mulai
		long startTime = System.nanoTime();
		// gScore menyimpan jarak asli dari src ke vertex
		Map<T, Integer> gScore = new HashMap<>();
		// Simpan parent untuk membentuk path
		Map<T, T> path = new HashMap<>();
		// Simpan vertex yang sudah diproses
		Map<T, Integer> visited = new HashMap<>();
		// counter jumlah vertex yang sudah dikunjungi
		int visitedCount = 0;
		// Min Priority Queue ( capacity dibuat lebih besar karena node bisa masuk PQ lebih dari sekali )
		Heap<Integer, T> PQ = new Heap<>(edge_length + vertex_length + 10, true);
		// Jarak source ke dirinya sendiri adalah 0
		gScore.put(src, 0);
		// fScore source = gScore + heuristic
		int startF = heuristic(src, target, cols);
		// Masukan source ke PQ dengan priority 
		PQ.insert(startF, src);
		while (PQ.size() > 0) {
			BTNode<Integer, T> top = PQ.removeFirst(); // Ambil vertex dengan fScore terkecil
			T curr_vertex = top.getData(); // vertex sekarang
			if (visited.containsKey(curr_vertex)) { // skip kalau sudah diproses
				continue;
			}
			visited.put(curr_vertex, 1);// tandai sudah diproses
			visitedCount++; // tambah jumlah visited node
			// kalau sudah sampai target, berhenti
			if (Objects.equals(curr_vertex, target)) {
				break;
			}
			// ambil adjacency list vertex sekarang
			MyLinearList<Edge<T>> list = adj.get(curr_vertex);
			// lanjut kalau tidak punya tetangga
			if ( list == null){
				continue;
			}
			// ambil gScore current
			int curr_g = gScore.get(curr_vertex);
			// iterasi tetangga
			Node<Edge<T>> root = list.head;
			while ( root != null ) {
				Edge<T> edge = root.getData(); // ambil edge
				T n_vertex = edge.getNeighbor(); // vertex tetangga
				int n_weights = edge.getWeight(); // bobot edge
				int new_g = curr_g + n_weights; // gScore baru = gScore current + bobot edge
				// jika tetangga belum punya gScore atau jarak yang lebih kecil
				if ( !gScore.containsKey(n_vertex) || gScore.get(n_vertex) > new_g ) {
					gScore.put(n_vertex, new_g); // update gScore
					path.put(n_vertex, curr_vertex); // simpan parent
					int h = heuristic(n_vertex, target, cols); // hitung heuristic tetangga ke target
					int f = new_g + h; // fScore = gScore + heuristic
					PQ.insert(f, n_vertex);
				}
				// lanjut edge berikutnya
				root = root.getNext();
			}
		}

		// buat path akhir
		MyLinearList<T>return_path = makePath(path, src, target);
		// ambil cost akhir
		Integer cost;
		// kalau target ditemukan
		if ( gScore.containsKey(target)) {
			cost = gScore.get(target);
		}
		// kalau ga ketemu
		else {
			cost = Integer.MAX_VALUE;
		}
		// catat waktu selesai
		long endTime = System.nanoTime();
		// hitung durasi
		double duration = (endTime - startTime) / 1000000.0; // dalam milisekon
		// print hasil
		System.out.println("A*");
		System.out.println("Cost : " + cost);
		System.out.println("Visited Nodes : " + visitedCount);
		System.out.println("Time : " + duration + " ms");
		System.out.println("Path :");
		return_path.cetakList();
		return new BTNode<>(cost, return_path);
	}
	
	public pair<Graph<T>, Integer> MSTPrim(T src){
		
		Graph<T> return_type = new Graph<>(this.directed);

		Map<T, Integer> visited = new HashMap<>();
		

		// make min priority queue
		Heap<Integer, pair<T, T>> PQ = new Heap<>(vertex_length, true);		
		
		int cost = 0;
		Node<Edge<T>> h = adj.get(src).head;
		while (h != null){
			PQ.insert(h.getData().getWeight(), new pair<>(src, h.getData().getNeighbor()));
			h = h.getNext();
		}
		while(PQ.size() > 0){
			BTNode<Integer, pair<T,T>> top = PQ.removeFirst();
			Integer curr_dis = top.getKey();
			T curr_vertex = top.getData().second;
			T prev_vertex = top.getData().first;

			return_type.addEdge(prev_vertex, curr_vertex, curr_dis);
			if (return_type.containsCycle()){
				return_type.deleteEdge(prev_vertex, curr_vertex);
				continue;
			}
			else {
				visited.put(curr_vertex, 1);
				visited.put(prev_vertex, 1);
				cost += curr_dis;
			}
			

			// looping through the edges and verteces
			Node<Edge<T>> root = adj.get(curr_vertex).head;
			while (root != null){
				Edge<T> edge = root.getData();
				T n_vertex = edge.getNeighbor();
				Integer n_weights = edge.getWeight();

				if (visited.containsKey(curr_vertex) && !visited.containsKey(n_vertex)){
					PQ.insert(n_weights, new pair<>(curr_vertex, n_vertex));
				}

				root = root.getNext();
			}
		}
		return new pair<>(return_type, cost);
	}

	public boolean containsCycle(){
		Map<T, Integer> visited = new HashMap<>();
		for (T k : adj.keySet()){
			Node<Edge<T>> head = adj.get(k).head;
			while (head != null){
				if (!visited.containsKey(k)){
					if (_dfs(visited, k, null)){
						return true;
					}
				}
				head = head.getNext();
			} 
		}

		return false;
	}

	private boolean _dfs(Map<T, Integer> visited, T curr, T parent){
		visited.put(curr, 1);
		Node<Edge<T>> k = adj.get(curr).head;

		while(k != null){
			T neigh = k.getData().getNeighbor();
			if (!visited.containsKey(neigh)){
				if (_dfs(visited, neigh, curr)){
					return true;
				}
			}
			// Objects.equals(neigh, b)
			else if (!Objects.equals(neigh, parent)){
				return true;
			}
			k = k.getNext();
		}

		return false;
	}
	

	public pair<Graph<T>, Integer> MSTKruskal(T src){
		Heap<Integer, pair<T, T>> PQ = new Heap<>(edge_length, false);
		
		// add all of the edge to PQ
		for (T k : adj.keySet()){
			MyLinearList<Edge<T>> curr = adj.get(k);
			if (curr != null){
				Node<Edge<T>> head = curr.head;
				while (head != null){
					Edge<T> edge = head.getData();
					T n_vertex = edge.getNeighbor();
					Integer n_weights = edge.getWeight();
					PQ.add(n_weights, new pair<>(k, n_vertex));
					head = head.getNext();
				}
			}
		}
		// sort PQ o(nlogn)
		PQ.sort();
		MyArrayList<BTNode<Integer, pair<T,T>>> n = PQ.getArray();
		// n.cetakList();
		
		int cost = 0;
		Graph<T> ret_graph = new Graph<>(this.directed);
		
		// iterate through the array o(n)
		for (int i = 0; i < n.size(); i++){
			BTNode<Integer, pair<T,T>> k = n.get(i);

			T neighbor = k.getData().second;
			T curr = k.getData().first;
			Integer weights = k.getKey();
			
			// add edge
			ret_graph.addEdge(curr, neighbor, weights);
			// check if there exist a cycle
			// o(n) to check
			if (ret_graph.containsCycle()){
				ret_graph.deleteEdge(curr, neighbor);
			}

		}

		// o(n^2) thus the loop


		// another bfs o(n)
		MyLinearList<T> q = new MyLinearList<>();
		Map<T, Integer> visit = new HashMap<>();
		q.pushQ(src);
		visit.put(src, 1);
		while(!q.isEmpty()){
			T curr = q.remove();
			Node<Edge<T>> h = ret_graph.adj.get(curr).head;
			while (h != null){
				Edge<T> e = h.getData();
				if (!visit.containsKey(e.getNeighbor())){
					cost += e.getWeight();
					visit.put(e.getNeighbor(), 1);
					q.pushQ(e.getNeighbor());
				}
				h = h.getNext();
			}
		}
		
		// the total complexity is o(n^2) :(
		return new pair<>(ret_graph, cost);
	}	

}


class pair<T,V> {
	public T first;
	public V second;
	public pair(T pair1, V pair2){
		this.first = pair1;
		this.second = pair2;
	}

	@Override
	public String toString(){
		return "(" + first.toString() + ", " + second.toString() + ")";
	}

	// @Override
	public boolean equals(pair<T,V> a){
		return a.first.equals(this.first) && a.second.equals(this.second);
	}
}