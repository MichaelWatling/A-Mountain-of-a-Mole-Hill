import java.util.Scanner;
/*Created by Michael Watling 05/01/2022*/

public class MoleHill {
    public static void main(String args[]) {
        int width = 16;
        int height = 16;

        char[][] Matrix = new char[height][width];

        Scanner in = new Scanner(System.in);
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
            for (int j = 0; j < width; j++) {
                Matrix[i][j] = line.charAt(j);
            }
        }
        int[] postStart = findPost(Matrix, height, width);
        int result = flood(Matrix, postStart[0], postStart[1], height, width);

        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(result);
    }
    /*This finds the top right post of a potential garden, and checks to see if the top left neighbour and top right neighbour are x's. If they are, you are either still
inside but this is a completed area or you are outside the garden walls.
Returns negative values to show a failure to find a new starting point, ending the cycle.*/
    public static int[] findPost(char[][] matrix, int h, int w){
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(matrix[i][j] == '+'){
                    if(i+1 < h && j+1 < w){
                        if(matrix[i][j+1]=='|' ||  matrix[i][j+1]=='-'){
                            int minusI = i -1;
                            if(minusI < 0 ){
                                minusI = 0;
                            }
                            int minusJ = j -1;
                            if(minusJ < 0){
                                minusJ =0;
                            }
                            if(matrix[minusI][minusJ] != 'x' && matrix[minusI][j+1] != 'x'){
                                String checkString = "+|-.x";
                                if(!checkString.contains(""+matrix[i+1][j+1])){
                                    int[] coords = new int[]{i+1, j+1};
                                    return coords;
                                }
                            }
                        }
                    }
                }
            }
        }
        int[] coords = new int[]{-1, -1};
        return coords;
    }
/*Flood takes in the coordinates of the first garden space and floods the area looking for moles, tracking where it has been using X's.
This is an 8 way flood to take into account for diagonals.
x = x coordinate, y = y coordinate, h = height or matrix, w = width of matrix*/
    public static int flood(char[][] matrix, int x, int y, int h, int w){
        int count = 0;
        if(matrix[x][y] == 'o'){
            count+=1;
        }
        String checkString = "o ";
        if(checkString.contains(""+matrix[x][y])){
            matrix[x][y] = 'x';
            if(x >= 1){ count += flood(matrix,x-1,y,h,w);}
            if(x+1 < h){ count += flood(matrix,x+1,y,h,w);}
            if(y >= 1){ count += flood(matrix,x,y-1,h,w);}
            if(y+1 < w){count += flood(matrix,x,y+1,h,w);}
            if(x>=1 && y>=1){ count += flood(matrix,x-1,y-1,h,w);}
            if(x>=1 && y+1<w){ count += flood(matrix,x-1,y+1,h,w);}
            if(x+1 < h && y >= 1){ count += flood(matrix,x+1,y-1,h,w);}
            if(x+1 < h && y+1 < w){ count += flood(matrix,x+1,y+1,h,w);}
            int [] newCoords = findPost(matrix, h,w);
            if(newCoords[0]!= -1){ count+=flood(matrix,newCoords[0],newCoords[1],h,w);}
        }
        return count;
    }
}
