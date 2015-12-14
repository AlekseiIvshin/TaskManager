package com.alekseiivhsin.taskmanager;

import android.app.Application;

import com.alekseiivhsin.taskmanager.ioc.Graph;

/**
 * Created on 07/12/2015.
 */
public class App extends Application {

    private static App mAppInstance;

    private Graph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppInstance = this;
        mObjectGraph = Graph.Initializer.init();
    }

    public Graph getObjectGraph(){
        return mObjectGraph;
    }

    public void setObjectGraph(Graph graph){
        mObjectGraph = graph;
    }

    public static Graph getObjectGraphInstance(){
        return mAppInstance.getObjectGraph();
    }

    public static App getInstance(){
        return mAppInstance;
    }
}
