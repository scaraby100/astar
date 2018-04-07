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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import xyz.scarabya.astar.domain.Point;

/**
 *
 * @author Alessandro Patriarca
 */
public class AStar
{
    private final PriorityQueue<Point> openQueue;
    private final HashMap<Point, Point> openMap;
    private final LinkedHashSet<Point> closedSet;
    private final HashSet<Point> obstacles;
    
    private final Point start, end;
    
    private boolean pathFound = false;

    public AStar(Point start, Point end, HashSet<Point> obstacles)
    {
        this.obstacles = obstacles;
        this.start = start;
        this.end = end;
        
        this.openQueue = new PriorityQueue<>((Point one, Point two)
                -> (int)(one.fScore - two.fScore));
        this.closedSet = new LinkedHashSet<>();
        this.openMap = new HashMap<>();
    }
    

    public void findPath()
    {
        pathFound = false;
        start.fScore = getHeuristicCostEstimate(start);
        start.gScore = 0;
        start.toInsert = false;
        openQueue.add(start);
        openMap.put(start, start);

        Point current = null;
        int tentativeGScore;

        while (!openQueue.isEmpty() && !(current = openQueue.poll()).equals(end))
        {
            openMap.remove(current);
            closedSet.add(current);

            for (Point neighbor : findNeighbors(current))
            {
                tentativeGScore = current.gScore + 1;
                if (tentativeGScore < neighbor.gScore)
                {
                    if (!neighbor.toInsert)
                    {
                        neighbor.toInsert = true;
                        openQueue.remove(neighbor);
                    }
                    neighbor.cameFrom = current;
                    neighbor.gScore = tentativeGScore;
                    neighbor.fScore = tentativeGScore + getHeuristicCostEstimate(neighbor);
                }

                if (neighbor.toInsert)
                {
                    neighbor.toInsert = false;
                    openQueue.add(neighbor);
                }
            }
        }
        end.cameFrom = current.cameFrom;
    }

    private LinkedList<Point> findNeighbors(Point point)
    {
        LinkedList<Point> neighbors = new LinkedList<>();        

        final int px = point.x, py = point.y;

        Point neighbor = new Point(px, py + 1);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));

        neighbor = new Point(px + 1, py);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));

        neighbor = new Point(px, py - 1);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));

        neighbor = new Point(px - 1, py);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));        

        return neighbors;
    }
    
    public LinkedList<Point> getFinalPath()
    {
        LinkedList<Point> finalPath = new LinkedList<>();
        Point current = end;
        while(current.cameFrom != null)
        {
            finalPath.addFirst(current);
            current = current.cameFrom;
        }
        return finalPath;
    }
    
    private Point getFromMapOrAddNew(Point point)
    {
        final Point retPoint = openMap.get(point);
        if(retPoint == null)
        {
            openMap.put(point, point);
            return point;
        }
        else
            return retPoint;
    }

    public int getDistBetween(Point from, Point to)
    {
        return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
    }

    public double getHeuristicCostEstimate(Point from)
    {
        return Math.sqrt(Math.pow(from.x - end.x, 2)+Math.pow(from.y - end.y,2));
    }
}
