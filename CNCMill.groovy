import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
import com.neuronrobotics.bowlerstudio.vitamins.*;
import eu.mihosoft.vrl.v3d.parametrics.*;
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
// Load an STL file from a git repo
// Loading a local file also works here

CSGDatabase.clear()//set up the database to force only the default values in	
return new ICadGenerator(){

	double toMM(double feet){
		return 12.0*feet*24.5;
	}
	LengthParameter thickness 		= new LengthParameter("Big Material Thickness",3.15,[19,3])
	LengthParameter rollerDiameter	= new LengthParameter("Roller Diameter Thickness",40,[80,3])
	
	//http://www.aliexpress.com/item/4pcs-lot-61920-6920-2RS-bearing-sealed-100x140x20-mm-61920-2RS-ball-bearing-100mm-diameter-6920RS/795172529.html?spm=2114.40010308.4.7.5uywHF
	ArrayList<String> options = Vitamins.listVitaminSizes("stepperMotor");
	StringParameter stepperType = new StringParameter("stepperMotor Default",
											"GenericNEMA17",
											options)
	StringParameter bearingType = new StringParameter("ballBearing Default",
											"608zz",
											options)									
	CSG stepper = Vitamins.get( "stepperMotor",bearingType.getStrValue())
										
	CSG bearing = Vitamins.get( "ballBearing",bearingType.getStrValue())
				.roty(90)
	def params = [thickness,rollerDiameter,stepperType]
	
	@Override 
	public ArrayList<CSG> generateCad(DHParameterKinematics d, int linkIndex) {
		ArrayList<DHLink> dhLinks = d.getChain().getLinks()
		ArrayList<CSG> allCad=new ArrayList<>()
		
		DHLink dh = dhLinks.get(linkIndex)
		// Hardware to engineering units configuration
		LinkConfiguration conf = d.getLinkConfiguration(linkIndex);
		// Engineering units to kinematics link (limits and hardware type abstraction)
		AbstractLink abstractLink = d.getAbstractLink(linkIndex);// Transform used by the UI to render the location of the object
		// Transform used by the UI to render the location of the object
		Affine manipulator = dh.getListener();

				
		allCad.add(stepper.clone())

		for(CSG c:allCad){
			for(Parameter p:params){
				c.setParameter(p)
			}
			c.setManipulator(manipulator)
			c.setRegenerate({ generateCad(d, linkIndex).get(0)})	
		}
		
		return allCad;
	}
	@Override 
	public ArrayList<CSG> generateBody(MobileBase b ) {
		ArrayList<CSG> allCad=new ArrayList<>();
		

		return allCad;
	}
};