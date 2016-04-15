package Helpers;

import Genetic.GeneticAlgorithmResult;
import Genetic.Individual;
import Genetic.Result;

import java.io.*;

/**
 * Created by sanczo on 2016-03-15.
 */
public class TextFileWriter  {

    private String fileName;

    private PrintWriter writer;


    public TextFileWriter(String fileName) {
        this.fileName = fileName;
        CreatePrintWriter();
    }

    public void CreatePrintWriter()
    {
        File filePath = new File(fileName);
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FlushData()
    {
        writer.flush();
    }

    public void EndConnection()
    {
            writer.close();
            writer = null;

    }

    public void writeHeader()
    {
        writer.println("Populacja,nalepszy,sredni,najgorszy");
    }
    //polimorfizm ad hoc, przeciazenie

    public void write(ResearchData researchData)
    {
        writer.println(researchData.getGeneration()+","+researchData.getBestResult()+","+researchData.getAverageResult()+","+researchData.getWorstResult());
    }

    public void write(GeneticAlgorithmResult result)
    {
        writer.print("Podejscie " + result.getTake()+"   ");
        writer.print("Rozwiazanie  "+result.getResult()+" po "+ result.getIterations() + " iteracjach   ");
        writer.println("w czasie " +
                result.getDurationTime().getMinutes() + " min  " +
                result.getDurationTime().getSeconds() + " sek " +
                result.getDurationTime().getMilliseconds() + " milsek   " +
                "calkowita liczba milisekund " + result.getDurationTime().getMilliseconds());
    }

    public void write(Result result) {
        writer.println(result);
    }

    public void logTime(MyTime diff) {
        writer.println("Execute time " + diff.getMinutes()+" min  "+diff.getSeconds()+" sek "+diff.getMilliseconds()+" milsek" );
    }

    public void logSuccessAndFailure(int success, int failure, long time) {
        writer.println("Poszukiwanie rozwiazanie zakonczylo siê " + success + " sukcesami i " + failure + " porazkami");
        MyTime myTime = new MyTime(time);
        writer.println("W srednim czasie: " + myTime.getSeconds() + " s   " + myTime.getMilliseconds() + " milisekundy    total time " + myTime.getMilliseconds() + " milisekundy");
    }

    public void writeColoring(int vertexId, int color)
    {
        writer.println("<" + vertexId + " " + color + ">");
    }


    public void print(int[] colors, int individualPosition) {

        writer.print("Osbnik nr " + individualPosition+"    [");
        for (int c : colors)
        {
            writer.print(" "+c+",");
        }
        writer.print("]\n");
        writer.println();
    }
}
