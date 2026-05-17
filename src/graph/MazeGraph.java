package graph;

import java.util.Random;

public class MazeGraph {

    private int baris; // simpan baris maze
    private int kolom; // simpan kolom maze
    private Cell[][] maze; // simpan maze array 2D
    private Random rand; // generate maze random
    //private Graph<String> graph; // Opsional, buat convert maze ke graph

    public class Cell {
        int baris;
        int kolom;
        boolean visited; // cek apakah cell sudah dikunjungi

        // kita buat 4 walls
        // 0 = atas
        // 1 = kanan
        // 2 = bawah
        // 3 = kiri
        boolean[] walls;
        public Cell(int baris, int kolom) {
            this.baris = baris;
            this.kolom = kolom;
            this.visited = false; // Anggap semua cell belum dikunjungi
            // Anggap semua walls ada
            this.walls =
                new boolean[]{true, true, true, true};
        }
    }

    // Constructor untuk menginisialisasi maze
    public MazeGraph(int baris, int kolom) {
        this.baris = baris;
        this.kolom = kolom;
        rand = new Random(); // random untuk generate maze
        // undirected graph jadi mazenya 2 arah
        // graph = new Graph<>(false);
        // bikin maze array 2D
        maze = new Cell[baris][kolom];
        // isi semua cell di maze
        for(int i = 0; i < baris; i++) {
            for(int j = 0; j < kolom; j++) {
                // buat cell baru di maze
                maze[i][j] = new Cell(i, j);
            }
        }
    }

    // Generate maze dengan DFS backtracking
    public void generateMaze() {
        // mulai dari atas kiri maze
        generateMazeDFS(0, 0);
        System.out.println("Maze Kelinci Percobaan");
    }

    // DFS backtracking
    private void generateMazeDFS(int baris, int kolom) {
        // tandai cell sudah dikunjungi
        maze[baris][kolom].visited = true;
        // 4 arah yakni:
        // atas, kanan, bawah, kiri
        int[] arah = {0, 1, 2, 3};
        // shuffle arah secara random
        for(int i = 0; i < arah.length; i++) {

            // ambil index random
            int randomIndex =
                rand.nextInt(arah.length);

            // swap arah
            int temp = arah[i];
            arah[i] = arah[randomIndex];
            arah[randomIndex] = temp;
        }

        // cek semua arah hasil shuffle
        for(int i = 0; i < arah.length; i++) {
            // ambil arah sekarang
            int dir = arah[i];
            // posisi next cell sementara
            int nextBaris = baris;
            int nextKolom = kolom;
            // atas
            if(dir == 0) {
                nextBaris--;
            }
            // kanan
            else if(dir == 1) {
                nextKolom++;
            }
            // bawah
            else if(dir == 2) {
                nextBaris++;
            }
            // kiri
            else if(dir == 3) {
                nextKolom--;
            }
            // cek apakah next cell valid
            // dan belum dikunjungi
            if( isValid(nextBaris, nextKolom) && !maze[nextBaris][nextKolom].visited) {
                // hapus walls antara
                // current cell dan next cell
                removeWalls( maze[baris][kolom], maze[nextBaris][nextKolom]);
                // DFS rekursif ke next cell
                generateMazeDFS(
                    nextBaris,
                    nextKolom
                );
            }
        }
    }

    // Function hapus walls
    private void removeWalls(
        Cell current,
        Cell next
    ) {

        // selisih kolom
        int x =
            current.kolom - next.kolom;
        // selisih baris
        int y =
            current.baris - next.baris;
        // next ada di kiri
        if(x == 1) {
            // hapus kiri wall current
            current.walls[3] = false;
            // hapus kanan wall next
            next.walls[1] = false;
        }

        // next ada di kanan
        else if(x == -1) {
            // hapus kanan wall current
            current.walls[1] = false;
            // hapus kiri wall next
            next.walls[3] = false;
        }

        // next ada di atas
        else if(y == 1) {
            // hapus atas wall current
            current.walls[0] = false;
            // hapus bawah wall next
            next.walls[2] = false;
        }

        // next ada di bawah
        else if(y == -1) {
            // hapus bawah wall current
            current.walls[2] = false;
            // hapus atas wall next
            next.walls[0] = false;
        }
    }

    // validasi next cell
    private boolean isValid(
        int baris,
        int kolom
    ) {

        // return true jika next cell valid
        // false jika tidak
        return (
            baris >= 0 && baris < this.baris && kolom >= 0 && kolom < this.kolom
        );
    }

    // Print maze
    public void printMaze() {
        for(int i = 0; i < baris; i++) {
            for(int j = 0; j < kolom; j++) {
                System.out.print("[");
                // print semua walls
                for(boolean wall :
                    maze[i][j].walls
                ) {
                    // print 1 jika wall ada
                    if(wall) {
                        System.out.print("1");
                    }
                    // print 0 jika wall tidak ada
                    else {
                        System.out.print("0");
                    }
                }
                System.out.print("] ");
            }
            System.out.println();
        }
    }

    // Convert maze ke graph
    //public void convertToGraph() {
        // // loop semua cell maze
        // for(int i = 0; i < baris; i++) {
        //     for(int j = 0; j < kolom; j++) {
        //         // vertex sekarang
        //         String current =
        //             "(" + i + "," + j + ")";
        //         // cek apakah kanan terbuka
        //         if( !maze[i][j].walls[1] && j + 1 < kolom) {
        //             // vertex neighbor kanan
        //             String neighbor =
        //                 "(" + i + "," + (j + 1) + ")";
        //             // tambahkan edge
        //             graph.addEdge(
        //                 current,
        //                 neighbor,
        //                 1
        //             );
        //         }
        //         // cek apakah bawah terbuka
        //         if( !maze[i][j].walls[2] && i + 1 < baris ) {
        //             // vertex neighbor bawah
        //             String neighbor =
        //                 "(" + (i + 1) + "," + j + ")";
        //             // tambahkan edge
        //             graph.addEdge(
        //                 current,
        //                 neighbor,
        //                 1
        //             );
        //         }
        //     }
        // }
        // System.out.println(
        //     "Maze berhasil di-convert ke graph!"
        // );
    //}

    // getter graph
    // public Graph<String> getGraph() {
    //     return graph;
    // }

    // Main program
    public static void main(String[] args) {
        // buat maze 100 x 100
        MazeGraph maze =
            new MazeGraph(100, 100);
        // generate maze
        maze.generateMaze();
        // print maze
        maze.printMaze();
        // convert maze ke graph
       // maze.convertToGraph();
        // optional print graph
        // maze.getGraph().printGraph();
    }
}