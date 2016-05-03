package Helpers;

import Genetic.Individual;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by sanczo on 2016-03-07.
 */
public class TextFileReader {

    private String path;

    private BufferedReader reader;


    public TextFileReader(String path)
    {
        this.path = path;
    }

    public void CreateBufferedReader()
    {
       File filePath = new File(path);

        try {
             reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<String> ReadFileAndGiveListOfRows()
    {
        CreateBufferedReader();
        List<String> listOfRows = new ArrayList<>();
        if(reader != null)
        {
            String s;

            try {
                SkipFirstNLines(4,reader);
                while((s = reader.readLine()).startsWith("n") == false)
                    listOfRows.add(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        EndConnection();

        return listOfRows;
    }

    public List<String> giveListOfColorsNumberForEachVertex()
    {
        CreateBufferedReader();
        List<String> listOfColorNumberForEachVertex = new ArrayList<>();
        if(reader != null)
        {
            String s;

            try {
                while(!(s = reader.readLine()).startsWith("n"))
                    ;
                listOfColorNumberForEachVertex.add(s);
                while ((s = reader.readLine()) != null)
                    listOfColorNumberForEachVertex.add(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        EndConnection();

        return listOfColorNumberForEachVertex;

    }

    public void EndConnection()
    {
        if(reader != null) {
            try {
                reader.close();
                reader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SkipFirstNLines(int linesToSkip, BufferedReader in) throws IOException {
        for(int i =0; i<4;i++)
            in.readLine();
    }

    public int giveColorFromRow(String row){
        Pattern p = Pattern.compile("<|>");
        String onlyNumbers = p.matcher(row).replaceAll("");
        String[] idAndColor = onlyNumbers.split(" ");
        int color = Integer.parseInt(idAndColor[1]);
        return color;
    }

    public Individual readColoring()
    {
        ArrayList<Integer> colors = new ArrayList<>();
        String s;
        try {
            while((s = reader.readLine()) != null) {
                int color = giveColorFromRow(s);
                colors.add(color);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertListOfColorsToColloring(colors);
    }


    public Individual convertListOfColorsToColloring(ArrayList<Integer> colors){
        Individual individual = new Individual(colors.size());
        for (int i =0; i < colors.size() ;i++)
        {
            individual.setGenAtPosition(i, colors.get(i));
        }
        return individual;
    }

}
