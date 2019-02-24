import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LonelyIsland
{
    public static void Solution(LinkedList<Integer>[] islandsList,  boolean[] visited, int id, List<Integer> lonelyIsl)
    { 
        visited[id] = true;
        if (islandsList[id].size() == 1)
        {
            System.out.print(id+1);
            System.out.print(" ");
        }

        Iterator<Integer> i = islandsList[id].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n-1])
            {
                Solution(islandsList, visited, n-1, lonelyIsl);
            }
        }
    }
    
    public static void printPath(LinkedList<Integer>[] islandsList,  boolean[] visited, int id, LinkedList<Integer> availPaths)
    { 
        visited[id] = true;
        availPaths.add(id+1);
        if (islandsList[id].size() == 1)
        {
            availPaths.remove(new Integer(id+1));
        }

        Iterator<Integer> i = islandsList[id].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n-1])
            {
                printPath(islandsList, visited, n-1, availPaths);
                availPaths.remove(new Integer(n));
            }
            else if (visited[n-1] && islandsList[n-1].size() == 1)
            {
                availPaths.add(n);
                System.out.print(availPaths.toString());
                availPaths.remove(new Integer(n));
            }
        }
    }

    public static void main(String[] args)
    {
        Scanner reader = new Scanner(System.in);
        System.out.println("Input file name with .txt : ");
        String filename = reader.next();
        reader.close();
        File file = new File(filename);

        try
        {
            Scanner sc = new Scanner(file);
            String[] init = sc.nextLine().split("\\s+");
            int numIsl = Integer.parseInt(init[0]);
            int startIsl = Integer.parseInt(init[2]);
            LinkedList<Integer>[] islList = new LinkedList[numIsl];
            for (int i = 0; i < numIsl; i++)
            {
                islList[i] = new LinkedList<Integer>();
                islList[i].add(i+1);
            }

            while (sc.hasNextLine())
            {
                String[] j = sc.nextLine().split("\\s+");
                int prevIsl = Integer.parseInt(j[0]);
                int nextIsl = Integer.parseInt(j[1]);
                islList[prevIsl - 1].addLast(nextIsl);
            }
            
            long startTime = System.nanoTime();
            boolean[] visitedIsl = new boolean[numIsl];
            LinkedList<Integer> allPaths = new LinkedList<Integer>();
            List<Integer> lonelyIsls = new ArrayList();
            System.out.println("Lonely islands:");
            Solution(islList, visitedIsl, startIsl-1, lonelyIsls);
            long endTime   = System.nanoTime();
            visitedIsl = new boolean[numIsl];
            System.out.println();
            
            long totalTime = (endTime - startTime);
            System.out.println("Possible paths:");
            printPath(islList, visitedIsl, startIsl-1, allPaths);
            System.out.println();
            System.out.println("Runtime: " + totalTime + " ns");

            sc.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}