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
package xyz.scarabya.astar.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import xyz.scarabya.astar.domain.Point;

/**
 *
 * @author Alessandro Patriarca
 */
public class Problem
{
    //public int startX, startY;
    //public int endX, endY;
    //public int maxX, maxY, minX, minY;
    public int obstacles;
    public Point start, end;
    public List<int[][]> obstacleCoord = new ArrayList<>();
    public HashSet<Point> obstaclesPoint = new HashSet<>();
    //public Graph matrix;

    public void readFile(File file) throws IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            int startX, startY, endX, endY;

            String[] splitStr = br.readLine().split(" ");
            startX = Integer.parseInt(splitStr[0]);
            startY = Integer.parseInt(splitStr[1]);
            endX = Integer.parseInt(splitStr[2]);
            endY = Integer.parseInt(splitStr[3]);

            start = new Point(startX, startY);
            end = new Point(endX, endY);

            obstacles = Integer.parseInt(br.readLine());

            String line;
            while ((line = br.readLine()) != null)
                obstacleCoord.add(getObstacleCoord(line.split(" ")));
        }

    }

    public void createTriangles()
    {
        //getMaxAndMin();

        for (int[][] oP : obstacleCoord)
        {
            line4C(oP[0][0], oP[0][1], oP[1][0], oP[1][1]);
            line4C(oP[1][0], oP[1][1], oP[2][0], oP[2][1]);
            line4C(oP[2][0], oP[2][1], oP[0][0], oP[0][1]);
        }
        obstacleCoord.clear();
        obstacleCoord = null;

    }
    
    private void line8C(final int x0, final int y0, final int x1, final int y1)
    {
        final int dx = Math.abs(x1 - x0);
        final int dy = Math.abs(y1 - y0);
        final int totalD = dx + dy;

        final int ix = x0 < x1 ? 1 : x0 > x1 ? -1 : 0;
        final int iy = y0 < y1 ? 1 : y0 > y1 ? -1 : 0;

        int e = 0;
        int xD = x0;
        int yD = y0;
        int e1, e2, e1Abs, e2Abs;

        for (int i = 0; i < totalD; i++)
        {
            obstaclesPoint.add(new Point(xD, yD));
            e1 = e + dy;
            e2 = e - dx;
            e1Abs = e1 < 0 ? -e1 : e1;
            e2Abs = e2 < 0 ? -e2 : e2;
            if (e1Abs < e2Abs)
            {
                xD += ix;
                e = e1;
            }
            else
            {
                yD += iy;
                e = e2;
            }
        }
        obstaclesPoint.add(new Point(x1, y1));
    }

    private void line4C(final int x0, final int y0, final int x1, final int y1)
    {
        final int dx = Math.abs(x1 - x0);
        final int dy = Math.abs(y1 - y0);
        final int totalD = dx + dy;

        final int ix = x0 < x1 ? 1 : x0 > x1 ? -1 : 0;
        final int iy = y0 < y1 ? 1 : y0 > y1 ? -1 : 0;

        int e = 0;
        int xD = x0;
        int yD = y0;
        int e1, e2, e1Abs, e2Abs;

        for (int i = 0; i < totalD; i++)
        {
            obstaclesPoint.add(new Point(xD, yD));
            e1 = e + dy;
            e2 = e - dx;
            e1Abs = e1 < 0 ? -e1 : e1;
            e2Abs = e2 < 0 ? -e2 : e2;
            if (e1Abs < e2Abs)
            {
                xD += ix;
                e = e1;
            }
            else
            {
                yD += iy;
                e = e2;
            }
        }
        obstaclesPoint.add(new Point(x1, y1));
    }

    /*
    
     */
    private int[][] getObstacleCoord(String[] stringCoord)
    {
        int[][] coords = new int[3][2];
        coords[0][0] = Integer.parseInt(stringCoord[0]);
        coords[0][1] = Integer.parseInt(stringCoord[1]);
        coords[1][0] = Integer.parseInt(stringCoord[2]);
        coords[1][1] = Integer.parseInt(stringCoord[3]);
        coords[2][0] = Integer.parseInt(stringCoord[4]);
        coords[2][1] = Integer.parseInt(stringCoord[5]);
        return coords;
    }
}
