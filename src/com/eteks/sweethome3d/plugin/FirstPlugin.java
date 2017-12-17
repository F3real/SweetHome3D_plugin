package com.eteks.sweethome3d.plugin;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
			getPointsOfRoomItems(r, itemName);
			for (Selectable piece : getHome().getSelectableViewableItems()) {
				if (HomePieceOfFurniture.class.equals(piece.getClass())) {
					if (itemName.equals(((PieceOfFurniture) piece).getName()) && isItemContained(r, piece)) {
						res += 1;
					}
				}
			}
			return res;
		}

		// Get central coordinates of selected items
		private List<Point> getPointsOfRoomItems(Room r, String itemName) {
			List<Point> res = new ArrayList<Point>();
			for (Selectable piece : getHome().getSelectableViewableItems()) {
				if (HomePieceOfFurniture.class.equals(piece.getClass())) {
					if (itemName.equals(((PieceOfFurniture) piece).getName()) && isItemContained(r, piece)) {
						int x = 0;
						int y = 0;
						float[][] points = piece.getPoints();
						for (int i = 0; i < points.length; i++) {
							x += points[i][0];
							y += points[i][1];
						}
						res.add(new Point(x / points.length, y / points.length));
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
