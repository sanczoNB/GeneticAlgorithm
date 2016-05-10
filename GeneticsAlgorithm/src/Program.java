import Controller.Controller;
import Exceptions.FlatPopulationException;
import Genetic.*;
import Helpers.ProgramDependencies;
import Helpers.ProxyHandlerToMeasureTime;
import Helpers.TextFileWriter;
import MyGraph.GraphMaker;
import MyGraph.IGraphService;
import MyGraph.VertexOrder;
import View.Parametrs.Parameters;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Program {




    private ProxyHandlerToMeasureTime timeKepper;



    public Program(String fileName)
    {



    }

    private IGraphService createDynamicProxy(IGraphService graphService) {
        timeKepper = new ProxyHandlerToMeasureTime(graphService);
        IGraphService graphSrevice =
                (IGraphService) Proxy.newProxyInstance(
                        IGraphService.class.getClassLoader(),
                        new Class[]{IGraphService.class},
                        timeKepper);
        return graphSrevice;
    }





}
