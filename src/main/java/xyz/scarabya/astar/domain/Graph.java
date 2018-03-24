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
package xyz.scarabya.astar.domain;

/**
 *
 * @author Alessandro Patriarca
 */
public class Graph
{
    private final boolean[][] matrix;    
    private final int offsetX, offsetY;
    
    public final int minX, maxX, minY, maxY;
    
    private final int lengthX, lengthY;

    public Graph(int minX, int maxX, int minY, int maxY, int padding)
    {
        offsetX = padding + ((minX < 0) ? - minX : 0);
        offsetY = padding + ((minY < 0) ? - minY : 0);
        
        this.minX = minX - padding;
        this.maxX = maxX + padding;
        this.minY = minY - padding;
        this.maxY = maxY + padding;
        
        lengthX = maxX + offsetX + padding + 1;
        lengthY = maxY + offsetY + padding + 1;
        
        matrix = new boolean[lengthY][lengthX];
    }
    
    public void set(int x, int y, boolean value)
    {
        matrix[y + offsetY][x + offsetX] = value;
    }
    
    public boolean get(int x, int y)
    {
        return matrix[y + offsetY][x + offsetX];
    }    
}
