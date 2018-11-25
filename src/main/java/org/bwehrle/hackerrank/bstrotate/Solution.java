package org.bwehrle.hackerrank.bstrotate;

public class Solution {

    static class Node {
        public Node(int val, int ht, Node left, Node right) {
            this.val = val;
            this.ht = ht;
            this.left = left;
            this.right = right;
        }

        int val;    //Value
        int ht;        //Height
        Node left;    //Left child
        Node right;    //Right child

    }
    static Node createNode(int val) {
        Node node = new Node(val, 0, null, null);
        node.val = val;
        return node;
    }

    static int calcNewHeight(Node node) {
        int lh = node.left == null ? -1 : node.left.ht;
        int rh = node.right == null ? -1 : node.right.ht;
        return Math.max(lh + 1, rh + 1);
    }

    static int getBalanceFactor(Node node) {
        int lh = node.left == null ? 0 : node.left.ht + 1;
        int rh = node.right == null ? 0 : node.right.ht + 1;
        return lh - rh;
    }

    static Node rebalanceLeft(Node node) {
        Node l1 = node.left;
        int bf = getBalanceFactor(l1);
        if (bf >= 1) {
            // left left
            Node l1RTemp = l1.right;
            l1.right = node;
            node.left = l1RTemp;
            node.ht = calcNewHeight(node);
            l1.ht = calcNewHeight(l1);
            return l1;  // no new top node
        } else if (bf <= -1) {
            // left right
            Node l1r1 = l1.right;
            Node l1r1LTemp = l1r1.left;
            node.left = l1r1;
            l1r1.left = l1;
            l1.right = l1r1LTemp;
            l1.ht = calcNewHeight(l1);
            l1r1.ht = calcNewHeight(l1r1);
            return node;
        } else {
            return node;
        }
    }
    // rebalance receives pointer to subtree parent and direction
    static Node rebalanceRight(Node node) {
        Node r1 = node.right;
        int bf = getBalanceFactor(r1);
        if (bf >= 1) {
            // right left, 1st swap r1 and r1l1
            Node r1l = r1.left;
            Node r1lRightTemp = r1.right;
            node.right = r1l;
            r1l.right = r1;
            r1.left = r1lRightTemp;
            r1.ht = calcNewHeight(r1);
            r1l.ht = calcNewHeight(r1l);
            node.ht = calcNewHeight(node);
            return node;  // no new top node
        } else if (bf <= -1) {
            // right right
            Node r1ltemp = r1.left;
            r1.left = node;
            node.right = r1ltemp;
            node.ht = calcNewHeight(node);
            r1.ht = calcNewHeight(r1);
            return r1;
        } else {
            return node;
        }
    }

    public static Node insertNode(Node node, int val) {
        if (val > node.val) {
            if (node.right != null) {
                node.right = insertNode(node.right, val);
            } else {
                node.right = createNode(val);
            }
            node.ht = calcNewHeight(node);
        } else {
            if (node.left != null) {
                node.left = insertNode(node.left, val);
            } else {
                node.left = createNode(val);
            }
            node.ht = calcNewHeight(node);
        }

        int bf;
        do {
            bf = getBalanceFactor(node);
            if (bf < -1) {
                node = rebalanceRight(node);
            } else if (bf > 1) {
                node = rebalanceLeft(node);
            }
        } while (Math.abs(bf) > 1);
        return node;
    }

}