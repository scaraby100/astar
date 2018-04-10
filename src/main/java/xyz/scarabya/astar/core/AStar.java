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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.scarabya.astar.domain.Point;

/**
 *
 * @author Alessandro Patriarca
 */
public class AStar
{
    private final PriorityQueue<Point> openQueue;
    private final HashMap<Point, Point> openMap;
    private final HashSet<Point> closedSet;
    private final HashSet<Point> obstacles;
    
    private final Point start, end;
    
    private final double diagonalDist = StrictMath.sqrt(2);
    private final int endX, endY;

    public AStar(Point start, Point end, HashSet<Point> obstacles)
    {
        this.obstacles = obstacles;
        this.start = start;
        this.end = end;
        this.endX = end.x;
        this.endY = end.y;
        this.openQueue = new PriorityQueue<>((Point one, Point two)
                -> one.fScore >= two.fScore ? 1 : -1);
        this.closedSet = new HashSet<>();
        this.openMap = new HashMap<>();
    }
    

    public void findPath()
    {
        start.fScore = getHeuristicCostEstimate(start);
        start.gScore = 0;
        start.toInsert = false;
        openQueue.offer(start);
        openMap.put(start, start);

        Point current = null;
        double tentativeGScore;
        
        try(PrintWriter pw = new PrintWriter(new File ("C:\\Users\\a.patriarca\\Desktop\\output_challenge\\stats_4_euclidean_1.3.txt")))
        {
            System.out.println(obstacles.size());
            while (!openQueue.isEmpty() && !(current = openQueue.poll()).equals(end))
            {
                pw.println(openQueue.size() + ";" + closedSet.size());
                openMap.remove(current);
                closedSet.add(current);

                for (Point neighbor : findNeighbors(current))
                {
                    tentativeGScore = current.gScore + getDistBetween(current, neighbor);
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
                        openQueue.offer(neighbor);
                    }
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if(current != null && end.equals(current))
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

        
        
        neighbor = new Point(px + 1, py + 1);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));
        
        neighbor = new Point(px - 1, py + 1);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));
        
        neighbor = new Point(px + 1, py - 1);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));
        
        neighbor = new Point(px -1, py - 1);
        if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            neighbors.add(getFromMapOrAddNew(neighbor));

        return neighbors;
    }
    
    public LinkedList<Point> getFinalPath()
    {
        LinkedList<Point> finalPath = new LinkedList<>();
        if(end.cameFrom != null)
        {
            Point current = end;
            while(current != null)
            {
                finalPath.addFirst(current);
                current = current.cameFrom;
            }
        }
        return finalPath;
    }
    
    private Point getFromMapOrAddNew(final Point point)
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

    private double getDistBetween(final Point from, final Point to)
    {
        return (from.x != to.x && from.y != to.y) ? 1.3 : 1;
    }

    private double getHeuristicCostEstimate(final Point from)
    {
        final int dX = (from.x - endX), dY = (from.y - endY);
        return StrictMath.sqrt(dX * dX + dY * dY);
        //return (dX > 0 ? dX : -dX) + (dY > 0 ? dY : -dY);
    }
}
