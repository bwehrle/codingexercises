package org.bwehrle.hackerrank.lca;

import java.util.*;

class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class Solution {

    public static Node[] traceNodePath(Node root, int value) {
        LinkedList<Node> nodePath = new LinkedList<Node>();

        while (root != null) {
            nodePath.add(root);
            if (value < root.data) {
                root = root.left;
            }
            else if (value > root.data) {
                root = root.right;
            }
            else {
                break;
            }
        }
        if (root != null) {
            return nodePath.toArray(new Node[0]);
        } else {
            return new Node[0];
        }
    }

    public static Node lca(Node root, int v1, int v2) {
        Node commonNode = null;
        Node[] v1NodePath = traceNodePath(root, v1);
        Node[] v2NodePath = traceNodePath(root, v2);
        int maxCommonLength = Math.min(v1NodePath.length, v2NodePath.length);
        for (int i=maxCommonLength -1; i>=0; i-- ) {
            if (v1NodePath[i] == v2NodePath[i]) {
                commonNode = v1NodePath[i];
                break;
            }
        }
        return commonNode;
    }

    public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        int v1 = scan.nextInt();
        int v2 = scan.nextInt();
        scan.close();
        Node ans = lca(root,v1,v2);
        System.out.println(ans.data);
    }
}