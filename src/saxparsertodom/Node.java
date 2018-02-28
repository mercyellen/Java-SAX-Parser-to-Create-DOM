/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxparsertodom;

import java.util.ArrayList;

/**
 *
 * @author qiwenguo
 */
public class Node {
    public String tag;
    public ArrayList<Node> contains;
    public String content;
    public ArrayList<Attr> attrs;
    
    public Node() {
        tag = new String();
        contains = new ArrayList<>();
        content = new String();
        attrs = new ArrayList<>();
    }
}
