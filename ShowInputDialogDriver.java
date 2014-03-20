/*******************************************************************************
 *	Name:      		Mr. Shirck
 *	Assignment:		a2_08
 *	Files:			ShowInputDialogDriver.java, MessageDialogDriver.java
 ******************************************************************************/

 import javax.swing.JOptionPane;

public class ShowInputDialogDriver
{
	public static void main(String [] args)
	{
		// Declare variables and objects
		int intNum;		// integer
		double dblNum;	// double
		String  msg;	// string

		msg = JOptionPane.showInputDialog(null, "Enter an integer:");
		intNum = Integer.parseInt( msg );

		JOptionPane.showMessageDialog(null, msg + " + 2 = " + (intNum + 2) );



		msg = JOptionPane.showInputDialog(null, "Enter a double:");
		dblNum = Double.parseDouble( msg );

		JOptionPane.showMessageDialog(null, dblNum + " + 2.2 = " + (dblNum + 2.2) );


		System.exit(0);
	}
}