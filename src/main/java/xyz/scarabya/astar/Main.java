/*
 * Copyright 2018 Alessandro Patriarca.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.scarabya.astar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import xyz.scarabya.astar.core.AStar;
import xyz.scarabya.astar.core.Problem;
import xyz.scarabya.astar.domain.Graph;
import xyz.scarabya.astar.domain.Point;
import xyz.scarabya.astar.utils.TestDrawer;

/**
 *
 * @author Alessandro Patriarca
 */
public class Main
{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        Problem prob = new Problem();
        
        System.out.println("Lettura problema");
        
        prob.readFile(new File("C:\\Users\\a.patriarca\\Desktop\\input_challenge\\input_3.txt"));
        
        System.out.println("Creazione triangoli");
        
        prob.createTriangles();
        
        System.out.println("Creazione istanza A*");
        
        AStar as = new AStar(prob.start, prob.end, prob.obstaclesPoint);
        
        System.out.println("Calcolo path");

        as.findPath();
        
        System.out.println("Ricostruzione path");
        
        LinkedList<Point> path = as.getFinalPath();
        
        System.out.println("Stampa path");
        
        try(PrintWriter pw = new PrintWriter(new File("C:\\Users\\a.patriarca\\Desktop\\output_challenge\\output.txt")))
        {
            for(Point point : path)
            {
                pw.println(point.x + ";" + point.y);
            }
            pw.println();
        }
        testDraw(path);
    }
    
    public static void testDraw(LinkedList<Point> path) throws IOException
    {
        TestDrawer test = new TestDrawer();
        
        test.readFile(new File("C:\\Users\\a.patriarca\\Desktop\\input_challenge\\input_3.txt"));
        
        test.createTriangles(10);
        
        for(Point point : path)
        {
            test.matrix.set(point.x, point.y, true);
        }
        
        Graph matrix = test.matrix;
        
        try(PrintWriter pw = new PrintWriter(new File("C:\\Users\\a.patriarca\\Desktop\\output_challenge\\output_visual.txt")))
        {
            for(int y = matrix.minY; y <= matrix.maxY; y++)
            {
                for(int x = matrix.minX; x <= matrix.maxX; x++)
                {
                    pw.print(matrix.get(x, y) ? 'X' : ' ');
                }
                pw.println();
            }
        }
    }
    
}
