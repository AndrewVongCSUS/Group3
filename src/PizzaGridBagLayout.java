import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PizzaGridBagLayout extends JFrame {
    public static void main(String[] args) {
        new PizzaGridBagLayout();
    }

    JTextField name = new JTextField(20), phone = new JTextField(10), address = new JTextField(20);

    JRadioButton small = new JRadioButton("Small"), medium = new JRadioButton("Medium"),
            large = new JRadioButton("Large"), thick = new JRadioButton("Thick"),
            thin = new JRadioButton("Thin");

    JCheckBox pepperoni = new JCheckBox("Pepperoni"), mushrooms = new JCheckBox("Mushrooms"),
            anchovies = new JCheckBox("Anchovies");

    JButton okButton = new JButton("OK"), closeButton = new JButton("Close");

    public PizzaGridBagLayout() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        addItem(panel1, new JLabel("Name:"), 0, 0, 1, 1, GridBagConstraints.EAST);
        addItem(panel1, new JLabel("Phone:"), 0, 1, 1, 1, GridBagConstraints.EAST);
        addItem(panel1, new JLabel("Address:"), 0, 2, 1, 1, GridBagConstraints.EAST);

        addItem(panel1, name, 1, 0, 2, 1, GridBagConstraints.WEST);
        addItem(panel1, phone, 1, 1, 1, 1, GridBagConstraints.WEST);
        addItem(panel1, address, 1, 2, 2, 1, GridBagConstraints.WEST);

        Box sizeBox = Box.createVerticalBox();
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(small);
        sizeGroup.add(medium);
        sizeGroup.add(large);
        sizeBox.add(small);
        sizeBox.add(medium);
        sizeBox.add(large);
        sizeBox.setBorder(BorderFactory.createTitledBorder("Size"));
        addItem(panel1, sizeBox, 0, 3, 1, 1, GridBagConstraints.NORTH);

        Box styleBox = Box.createVerticalBox();

        ButtonGroup styleGroup = new ButtonGroup();
        styleGroup.add(thin);
        styleGroup.add(thick);
        styleBox.add(thin);
        styleBox.add(thick);
        styleBox.setBorder(BorderFactory.

                createTitledBorder("Style"));
        addItem(panel1, styleBox, 1, 3, 1, 1, GridBagConstraints.NORTH);

        Box topBox = Box.createVerticalBox();
        ButtonGroup topGroup = new ButtonGroup();
        topGroup.add(pepperoni);
        topGroup.add(mushrooms);
        topGroup.add(anchovies);
        topBox.add(pepperoni);
        topBox.add(mushrooms);
        topBox.add(anchovies);
        topBox.setBorder(BorderFactory.createTitledBorder("Toppings"));
        addItem(panel1, topBox, 2, 3, 1, 1, GridBagConstraints.NORTH);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(okButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(closeButton);
        addItem(panel1, buttonBox, 2, 4, 1, 1, GridBagConstraints.NORTH);

        this.add(panel1);
        this.pack();
        this.setVisible(true);
    }

    private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 100.0;
        gc.weighty = 100.0;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = align;
        gc.fill = GridBagConstraints.NONE;
        p.add(c, gc);
    }
}