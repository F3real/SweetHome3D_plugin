package com.eteks.sweethome3d.plugin;

import javax.swing.JOptionPane;

import com.eteks.sweethome3d.model.HomePieceOfFurniture;
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
			Room r = getSelectedRoom();
			if (null != r) {
				cameraNumber = countNumberOfRoomItems(r, CAMERA_MODEL_NAME);
				// Display the result in a message box (\u00b3 is for 3 in
				// supercript)
				String message = String.format("Number of cameras is %d", cameraNumber);
				JOptionPane.showMessageDialog(null, message);
			}

		}

		private Room getSelectedRoom() {
			if (getHome().getSelectedItems().size() != 1) {
				JOptionPane.showMessageDialog(null, "Select one item");
				return null;
			}
			// Get selected room

			Selectable item = getHome().getSelectedItems().get(0);
			Room res = null;
			// if user selected room just return it
			if (Room.class.equals(item.getClass())) {
				res = (Room) item;
			} else {
				// if user selected item in room, find room in which item
				// belongs
				for (Room r : getHome().getRooms()) {
					if (isItemContained(r, item)) {
						res = r;
					}
				}
			}
			return res;

		}

		private int countNumberOfRoomItems(Room r, String itemName) {
			int res = 0;
			for (Selectable piece : getHome().getSelectableViewableItems()) {
				if (HomePieceOfFurniture.class.equals(piece.getClass())) {
					if (itemName.equals(((PieceOfFurniture) piece).getName()) && isItemContained(r, piece)) {
						res += 1;
					}
				}
			}
			return res;
		}

		boolean isItemContained(Room r, Selectable item) {
			boolean res = false;
			int total = 0;
			for (float[] coord : item.getPoints()) {
				if (r.containsPoint(coord[0], coord[1], 0f)) {
					total += 1;
				}
			}
			if (total >= item.getPoints().length / 2) {
				res = true;
			}
			return res;
		}

		public CounterAction() {
			putPropertyValue(Property.NAME, "Count cameras");
			putPropertyValue(Property.MENU, "Tools");
			// Enables the action by default
			setEnabled(true);
		}

	}

}
