// Class: ClockListener
// Written by: Eric Huang
// Date: 6/11/18
// Description: runs every 5 mil seconds to time each question
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockListener implements ActionListener{
		InterfaceListener i;
	public ClockListener(InterfaceListener i) {
		this.i = i;
	}
	
	public void actionPerformed(ActionEvent e) {
		i.tick();
	}

}
