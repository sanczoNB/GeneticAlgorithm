package Helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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
        List<String> listOfRows = new ArrayList<>();
        if(reader != null)
        {
            String s;

            try {
                SkipFirstNLines(4,reader);
                while((s = reader.readLine()) != null && !s.startsWith("n"))
                    listOfRows.add(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listOfRows;
    }

    public void EndConnection()
    {
        try {
            reader.close();
            reader = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SkipFirstNLines(int linesToSkip, BufferedReader in) throws IOException {
        for(int i =0; i<4;i++)
            in.readLine();
    }

}
