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
import xyz.scarabya.astar.domain.Graph;
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
        testDraw();
    }
    
    public static void testDraw() throws IOException
    {
        TestDrawer test = new TestDrawer();
        
        test.readFile(new File("C:\\Users\\a.patriarca\\Desktop\\input_challenge\\test.txt"));
        
        test.createTriangles(10);
        
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
