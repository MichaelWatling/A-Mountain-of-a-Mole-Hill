#Created by Michael Watling 05/01/2022

"""This finds the top right post of a potential garden, and checks to see if the top left neighbour and top right neighbour are x's. If they are, you are either still 
inside but this is a completed area or you are outside the garden walls.
Returns negative values to show a failure to find a new starting point, ending the cycle. """
def findPost(matrix, h, w): 
    for i in range (h):
            for j in range(w):
                if(matrix[i][j] == "+"):
                    if(i+1 < h and j+1 < w):
                        if(matrix[i][j+1] in "|-"):
                            if(matrix[i-1][j-1] != "x" and matrix[i-1][j+1] != "x"):
                                    if(matrix[i+1][j+1] not in "+|-.x"):
                                        coords = [i+1, j+1]
                                        return(coords)
                        
    coords = [-1, -1]
    return(coords)


"""Flood takes in the coordinates of the first garden space and floods the area looking for moles, tracking where it has been using X's.
This is an 8 way flood to take into account for diagonals.
x = x coordinate, y = y coordinate, h = height or matrix, w = width of matrix""" 
def flood(matrix, x, y, h, w,):
    count = 0
    if(matrix[x][y] == "o"):
        count+=1
    if(matrix[x][y] in "o "):
        matrix[x][y] = "x"
        if x >= 1: count += flood(matrix,x-1,y,h,w)
        if x+1 < h: count += flood(matrix,x+1,y,h,w)
        if y >= 1: count += flood(matrix,x,y-1,h,w)
        if y+1 < w: count += flood(matrix,x,y+1,h,w)
        if x>=1 and y>=1: count += flood(matrix,x-1,y-1,h,w)
        if x>=1 and y+1<w: count += flood(matrix,x-1,y+1,h,w)
        if x+1 < h and y >= 1: count += flood(matrix,x+1,y-1,h,w)
        if x+1 < h and y+1 < w: count += flood(matrix,x+1,y+1,h,w)
        newCoords = findPost(matrix, h,w)
        if(newCoords[0]!= -1): count+=flood(matrix,newCoords[0],newCoords[1],h,w)
    return count
    
if __name__ == "__main__":
    
    width, height = 16, 16

    Matrix = [["/"  for x in range(width)] for y in range(height)]

    #setting up the input to be placed into a Matrix.
    for i in range(height):
        line = list(input())
        for j in range(width):
            Matrix[i][j] = line[j]

    postStart = findPost(Matrix, height, width)
    result = flood(Matrix,postStart[0],postStart[1],height,width)
    print(result)