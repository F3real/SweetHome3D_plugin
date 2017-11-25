package com.eteks.sweethome3d.plugin;
import javax.swing.JOptionPane;

import com.eteks.sweethome3d.model.PieceOfFurniture;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;



public class FirstPlugin extends Plugin {
	private static final String CAMERA_MODEL_NAME = "Security Camera";
	
	
	@Override
	public PluginAction[] getActions() {
		// TODO Auto-generated method stub
		 return new PluginAction [] {new CounterAction()};	 
		 }; 
		 
	
		 public class CounterAction extends PluginAction {

				@Override
				public void execute() {
			        int cameraNumber = 0;
			        // Compute the sum of the volume of the bounding box of 
			        // each movable piece of furniture in home
			        for (PieceOfFurniture piece : getHome().getFurniture()) {
			            if (CAMERA_MODEL_NAME.equals(piece.getName())){
			            	cameraNumber += 1;
			            }
			        }
			        
			        // Display the result in a message box (\u00b3 is for 3 in supercript)
			        String message = String.format(
			            "Number of cameras is %d", cameraNumber);
			        JOptionPane.showMessageDialog(null, message);
			    }

			    public CounterAction() {
			        putPropertyValue(Property.NAME, "Count cameras");
			        putPropertyValue(Property.MENU, "Tools");
			        // Enables the action by default
			        setEnabled(true);
			     }

			}

}
