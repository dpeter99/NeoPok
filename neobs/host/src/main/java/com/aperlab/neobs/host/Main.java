package com.aperlab.neobs.host;

import com.aperlab.neobs.FileLoadingException;
import com.aperlab.neobs.Runner;
import com.aperlab.neobs.WorkspaceNotFoundException;
import com.aperlab.neobs.model.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws WorkspaceNotFoundException, FileLoadingException {
        System.out.println("NEO BS Build System V0.0.0");

        if(args.length <= 0){
            System.out.println("Usage: neobs [OPTIONS] [TARGET]");
            return;
        }

        String targetId = args[0];

        Runner runner = new Runner();

        runner.openWorkspace(new File("."));

        Target target = runner.findTarget(targetId);

        if(target != null) {
            try {
                target.run();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Can't find target");
        }

        //runner.PrintStructure();
    }

}
