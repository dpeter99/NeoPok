package com.aperlab.neobs.host;

import com.aperlab.neobs.FileLoadingException;
import com.aperlab.neobs.Runner;
import com.aperlab.neobs.WorkspaceNotFoundException;
import com.aperlab.neobs.model.Target;
import com.aperlab.neobs.model.Workspace;
import com.aperlab.neobs.model.api.IProject;
import hu.webarticum.treeprinter.SimpleTreeNode;
import hu.webarticum.treeprinter.TreeNode;
import hu.webarticum.treeprinter.printer.listing.ListingTreePrinter;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "vis")
public class VisualizeCommand implements Callable<Integer> {

    @CommandLine.ParentCommand
    private NeoBSHost mainCmd;

    @Override
    public Integer call() {

        Runner runner = new Runner();

        try {
            runner.openWorkspace(mainCmd.workspaceFolder);
        } catch (WorkspaceNotFoundException | FileLoadingException e) {
            return 1;
        }

        SimpleTreeNode rootNode = new SimpleTreeNode("root");
        rootNode.addChild(print(runner));

        ListingTreePrinter printer = new ListingTreePrinter();
        printer.print(rootNode);

        return 0;
    }

    public static TreeNode print(Runner runner){
        SimpleTreeNode node = new SimpleTreeNode("runner");
        node.addChild(print(runner.getWorkspace()));
        return node;
    }

    private static TreeNode print(Workspace workspace) {
        SimpleTreeNode node = new SimpleTreeNode(workspace.id.toString());
        workspace.getProjects().forEach((neoKey, iProject) -> {
            node.addChild(print(iProject));
        });
        return node;
    }

    private static TreeNode print(IProject<?> project) {
        SimpleTreeNode node = new SimpleTreeNode(project.getId().toString());
        for (Target target : project.getTargets()) {
            node.addChild(print(target));
        }
        return node;
    }

    private static TreeNode print(Target target) {
        SimpleTreeNode node = new SimpleTreeNode(target.id.toString());
        return node;
    }
}
