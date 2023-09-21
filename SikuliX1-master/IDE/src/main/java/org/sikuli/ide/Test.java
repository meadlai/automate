package org.sikuli.ide;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test {
	 public static void main(String[] args) { 
	        JFrame f = new JFrame("Test");
	 
	        JButton b1 = new JButton("Button1");
	        JButton b2 = new JButton("Button2");
	        JButton b3 = new JButton("Button3");
	        JButton b4 = new JButton("Button4");
	        JButton b5 = new JButton("Button5");
	        JButton b6 = new JButton("Button6");
	        JButton b7 = new JButton("Button7");
	 
	        GridBagConstraints c = new GridBagConstraints();
	        GridBagLayout g = new GridBagLayout();
	 
	        f.setLayout(g);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	 
	        f.add(b1);   //由于属性都有默认值，前面的按默认值来加入！
	        f.add(b2);
	        f.add(b3);

	       c.gridy = 1;
	        c.gridwidth = 3 ;   //修改了gridwidth值
	        c.fill = GridBagConstraints.BOTH; //所以这里要做相应的修改才能按gridwidth的值来显示
	        g.setConstraints(b4,c);
	        f.add(b4);
	 
	        c = new GridBagConstraints();
	        c.gridy = 2;
	        g.setConstraints(b5,c);
	        f.add(b5);
	 
	        c = new GridBagConstraints();
	        c.gridy = 3;
	        g.setConstraints(b6,c);
	        f.add(b6);
	 
	 
	        c = new GridBagConstraints();
	        c.gridx = 1;
	        c.gridy = 2;
	        c.gridwidth = 2;
	        c.gridheight = 2;
	        c.fill =GridBagConstraints.BOTH;   //同上面的注释
	        g.setConstraints(b7,c);
	        f.add(b7);
	 
	        f.pack();
	        f.setVisible(true);
	    }
}
