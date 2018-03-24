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
public class Point
{
    public final int x, y;    
    private final int hash;
    
    public int gScore, fScore;    
    public Point cameFrom;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
        hash = 5887 + (29 * x) + y;
    }

    @Override
    public int hashCode()
    {
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (getClass() == obj.getClass())
        {
            final Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }
}
