package org.bwehrle.hackerrank.bstrotate;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionTest {

    void depthFirstToNode(ArrayList<Integer> nodeList, Solution.Node node) {
        if (node.right != null)
            depthFirstToNode(nodeList, node.right);
        if (node.left != null)
            depthFirstToNode(nodeList, node.left);

        nodeList.add(node.val);
    }

    List<Integer> depthFirstValList(Solution.Node node) {
        ArrayList<Integer> nodeList = new ArrayList<>();
        depthFirstToNode(nodeList, node);
        return nodeList;
    }

    @Test
    public void testRightRightRotate() {
        Solution.Node treeRoot = new Solution.Node(3, 1,
                null,
                new Solution.Node(4, 0, null, null));
        Solution.Node root = Solution.insertNode(treeRoot, 5);
        List<Integer> nodeValArr = depthFirstValList(root);
        Assert.assertEquals(Arrays.asList(5,3,4), nodeValArr);
    }

    @Test
    public void testRightLeftRotate() {
        Solution.Node treeRoot = new Solution.Node(3, 1,
                null,
                new Solution.Node(5, 0, null, null));
        Solution.Node root = Solution.insertNode(treeRoot, 4);
        List<Integer> nodeValArr = depthFirstValList(root);
        Assert.assertEquals(Arrays.asList(5,3,4), nodeValArr);
    }

    @Test
    public void testLeftLeftRotate() {
        Solution.Node treeRoot = new Solution.Node(5, 1,
                new Solution.Node(4, 0, null, null),
                null);
        Solution.Node root = Solution.insertNode(treeRoot, 3);
        List<Integer> nodeValArr = depthFirstValList(root);
        Assert.assertEquals(Arrays.asList(5,3,4), nodeValArr);
    }

    @Test
    public void testLeftRightRotate() {
        Solution.Node treeRoot = new Solution.Node(5, 1,
                new Solution.Node(3, 0, null, null),
                null);
        Solution.Node root = Solution.insertNode(treeRoot, 4);
        List<Integer> nodeValArr = depthFirstValList(root);
        Assert.assertEquals(Arrays.asList(5,3,4), nodeValArr);
    }
}