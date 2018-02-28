/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxparsertodom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author qiwenguo
 */
public class ParserController implements Initializable {
    
    private Label label;
    @FXML
    private TextArea xmlTextArea;
    @FXML
    private TextArea parsedTextArea;
    @FXML
    private HBox root;
    private File file;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void uploadClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) root.getScene().getWindow(); 
        
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt", "*.xml"));
        file = fileChooser.showOpenDialog(stage); 
        
        if (file != null) {
            BufferedReader br = null;
            
            String document = "";
            String line = "";
            
            try {
                br = new BufferedReader(new FileReader(file));
                
                while ((line = br.readLine()) != null ) {
                    document += line + "\n";
                }
                
                xmlTextArea.setText(document);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    @FXML
    private void parseClick(ActionEvent event) {

        try {
           Node node = xmlLoader.load(file);
           StringBuilder nodeSb = new StringBuilder();
           NodeToString(node, nodeSb, new int[] {0});
           parsedTextArea.setText(nodeSb.toString());
        } catch (Exception ex) {
            Logger.getLogger(ParserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void NodeToString(Node rootNode, StringBuilder sb, int[] level) {
        if (rootNode == null) {
            return;
        }
        sb.append(rootNode.tag);
        if (rootNode.attrs != null) {
            for (Attr attr : rootNode.attrs) {
                sb.append("<AttributeId : ").append(attr.id).append(" AttributeValue : ").append(attr.value).append(" >");
            }
        }
        
        if (rootNode.content != null && !rootNode.content.equals("\n")) {
            sb.append(" : ").append(rootNode.content);
        }
        sb.append("\n");
        
        level[0]++;
        for (Node node : rootNode.contains) {
            for (int i = 0; i < level[0]; i++ ) {
                sb.append("\t");
            }
            NodeToString(node, sb, level);
        }
        level[0]--;
    }
    
}
