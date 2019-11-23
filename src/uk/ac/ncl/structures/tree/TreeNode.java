package uk.ac.ncl.structures.tree;

import java.util.ArrayList;

public class TreeNode {
    int val,level;
    ArrayList<Integer> children;

    TreeNode(int val, int level) {
        this.val = val;
        this.level = level;
        children = new ArrayList<Integer>();
    }

    public void addChild(int val) {
        System.out.println("{TREESTRUCTURE} " + this.val + " has added as " + val + " as child.");
        children.add(val);
    }

    public int getLevel() {
        return this.level;
    }

    public ArrayList<Integer> getChildren() {
        return this.children;
    }

    public String toString() {
        String output = "";

        if (level == 0) return output+=this.val;
        for (int i = 0; i <= level - 1; i++) {
            if (i == level-1)
                output += "L";
            else
                output += "        ";
        }
        for (int i = this.level - 1; i < level; i++) {
            if (i < 0) break;
            output += "_ ";
        }
        output += "_ _>";
        return output += "("+this.val+")";
    }
}
