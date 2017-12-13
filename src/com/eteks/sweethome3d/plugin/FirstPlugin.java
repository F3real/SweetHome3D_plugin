package com.eteks.sweethome3d.plugin;

import javax.swing.JOptionPane;

import com.eteks.sweethome3d.model.PieceOfFurniture;
import com.eteks.sweethome3d.model.Room;
import com.eteks.sweethome3d.model.Selectable;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;

public class FirstPlugin extends Plugin {
	private static final String CAMERA_MODEL_NAME = "Security Camera";

	@Override
	public PluginAction[] getActions() {
		// TODO Auto-generated method stub
		return new PluginAction[] { new CounterAction() };
	};

	public class CounterAction extends PluginAction {

		@Override
		public void execute() {
			int cameraNumber = 0;

			for (PieceOfFurniture piece : getHome().getFurniture()) {
				if (CAMERA_MODEL_NAME.equals(piece.getName())) {
					cameraNumber += 1;
				}
			}

			// Display the result in a message box (\u00b3 is for 3 in
			// supercript)
			String message = String.format("Number of cameras is %d", cameraNumber);
			JOptionPane.showMessageDialog(null, message);

			// Get selected room
			for (Selectable piece : getHome().getSelectedItems()) {
				System.out.println(piece.getClass().toString());
				System.out.println("Number of rooms: " + getHome().getRooms().size());
				
				//if user selected room just return it
				if (Room.class.equals(piece.getClass())) {
					System.out.println("1:  Room found!");
				} else {
					//if user selected item in room, find room in which item belongs
					for (Room r : getHome().getRooms()) {
						System.out.println("Checking room");
						if (r.containsPoint(piece.getPoints()[0][0], piece.getPoints()[0][1], 1f)) {
							System.out.println("2:   Room found");
						}
					}
				}

			}

		}

		public CounterAction() {
			putPropertyValue(Property.NAME, "Count cameras");
			putPropertyValue(Property.MENU, "Tools");
			// Enables the action by default
			setEnabled(true);
		}

	}

}
