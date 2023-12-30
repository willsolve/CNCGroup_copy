import java.util.ArrayList;

import com.neuronrobotics.sdk.addons.kinematics.DHChain;
import com.neuronrobotics.sdk.addons.kinematics.DHLink;
import com.neuronrobotics.sdk.addons.kinematics.DhInverseSolver;
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;
import com.neuronrobotics.sdk.common.Log;					
import Jama.Matrix;

return new DhInverseSolver() {
	
	@Override
	public double[] inverseKinematics(TransformNR target,
			double[] jointSpaceVector,DHChain chain ) {
		//Log.enableDebugPrint();

		//println "to: " +target
		ArrayList<DHLink> links = chain.getLinks();
		
		int linkNum = jointSpaceVector.length;
		double [] inv = new double[linkNum];
	
		inv[2] = target.getX();
		inv[1] = target.getY();
		inv[0] = target.getZ();

		for(int i=3;i<inv.length && i<jointSpaceVector.length ;i++){
			inv[i]=jointSpaceVector[i];
		}
		return inv;
	}
};
