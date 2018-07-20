package Obj_Principal;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Dialogos extends JDialog {
    private JPanel myPanel          = null;
    private JCButton yesButton      = null;
    private JCButton noButton       = null;
//    private JCButton cancelarButton = null;

    public Dialogos(JFrame frame, boolean modal, String myMessage) {
    super(frame, modal);
    myPanel = new JPanel();
    getContentPane().add(myPanel);
    myPanel.add(new JLabel(myMessage));
    yesButton = new JCButton("Si", myMessage, myMessage);
    myPanel.add(yesButton);
    noButton = new JCButton("No" , myMessage, myMessage);
    myPanel.add(noButton);
    pack();
    //setLocationRelativeTo(frame);
    setLocation(200, 200); // <--
    setVisible(true);
    }
}



