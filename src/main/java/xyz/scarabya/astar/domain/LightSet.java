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
public class LightSet
{
    private final Point[] points = new Point[4];
    public Point[] pointsReplaced;

    private int lastElem;
    private int lastElemReplaced;

    public void clearArrays()
    {
        lastElem = -1;
        lastElemReplaced = -1;
    }

    public boolean isEmpty()
    {
        return lastElem > -1;
    }

    public void add(Point point)
    {
        lastElem++;
        points[lastElem] = point;
    }

    /*
    public void removeIfExists(Point point)
    {
        int i = 0;
        while(i <= lastElem && !point.equals(points[i]))
        {
            i++;
        }
        if(i <= lastElem)
        {
            points[i] = points[lastElem];            
            lastElem --;
        }
    }
     */
    public void createReplaceableArray()
    {
        pointsReplaced = new Point[lastElem + 1];
    }

    public void replaceIfExists(Point point)
    {
        int i = 0;
        while (i <= lastElem && !point.equals(points[i]))
            i++;
        if (i <= lastElem)
        {
            points[i] = points[lastElem];
            lastElem--;

            lastElemReplaced++;
            pointsReplaced[lastElemReplaced] = point;
        }
    }

    public void completeReplaceableArray()
    {
        for (int i = 0; i <= lastElem; i++)
        {
            lastElemReplaced++;
            pointsReplaced[lastElemReplaced] = points[i];
        }
    }
}
