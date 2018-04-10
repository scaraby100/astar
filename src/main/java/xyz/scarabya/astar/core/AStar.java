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
                    neighbor.fScore = tentativeGScore + neighbor.hScore;
                }

                if (neighbor.toInsert)
                {
                    neighbor.toInsert = false;
                    openQueue.offer(neighbor);
                }
            }
        }

        if (current != null && end.equals(current))
            end.cameFrom = current.cameFrom;
    }

    public HashSet<Point> returnClosedSet()
    {
        return closedSet;
    }

    private LinkedList<Point> findNeighbors(final Point point)
    {
        final LinkedList<Point> neighborsRet = new LinkedList<>();
        final int px = point.x, py = point.y;
        final Point[] neighbors =
        {
            new Point(px, py + 1),
            new Point(px + 1, py),
            new Point(px, py - 1),
            new Point(px - 1, py),
            new Point(px + 1, py + 1),
            new Point(px - 1, py + 1),
            new Point(px + 1, py - 1),
            new Point(px - 1, py - 1)
        };

        Point neighborFromMap;
        int dX, dY;
        for (Point neighbor : neighbors)
            if (!closedSet.contains(neighbor) && !obstacles.contains(neighbor))
            {
                neighborFromMap = openMap.get(neighbor);
                if (neighborFromMap == null)
                {
                    dX = (neighbor.x - endX);
                    dY = (neighbor.y - endY);
                    neighbor.hScore = StrictMath.sqrt(dX * dX + dY * dY);
                    openMap.put(neighbor, neighbor);
                    neighborsRet.add(neighbor);
                }
                else
                    neighborsRet.add(neighborFromMap);
            }

        return neighborsRet;
    }

    public LinkedList<Point> getFinalPath()
    {
        LinkedList<Point> finalPath = new LinkedList<>();
        if (end.cameFrom != null)
        {
            Point current = end;
            while (current != null)
            {
                finalPath.addFirst(current);
                current = current.cameFrom;
            }
        }
        return finalPath;
    }

    private double getDistBetween(final Point from, final Point to)
    {
        return (from.x != to.x && from.y != to.y) ? diagonalDist : 1;
    }

    private double getHeuristicCostEstimate(final Point from)
    {
        final int dX = (from.x - endX), dY = (from.y - endY);
        return StrictMath.sqrt(dX * dX + dY * dY);
    }
}
