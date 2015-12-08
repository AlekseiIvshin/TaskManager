package com.alekseiivhsin.taskmanager;

import android.app.Application;

import com.alekseiivhsin.taskmanager.ioc.Graph;

/**
 * Created on 07/12/2015.
 */
public class App extends Application {

    private Graph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = Graph.Initializer.init();
    }

    public Graph getObjectGraph(){
        return mObjectGraph;
    }

    public void setObjectGraph(Graph graph){
        mObjectGraph = graph;
    }
}
