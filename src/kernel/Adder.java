package kernel;

import main.MainDriver;
import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;

public class Adder {
	private int time;
	private Instr instr;
	private MainDriver main;
	public Adder(MainDriver main){
		this.main = main;
	}
	void run(Instr instr) {
		this.instr = instr;
		if (instr.op == OP.ADD) {
			time = ConstDefinition.OP_TIME[0];
		}
		else if (instr.op == OP.SUB) {
			time = ConstDefinition.OP_TIME[1];
		}
		else {
			System.out.println("操作符有错误！");
		}
	}
	void activate() {
		time--;
		if (time == 0) {
			double ans;
			if (instr.op == OP.ADD) {
				ans = main.fp.get(instr.src1.ordinal()) + main.fp.get(instr.src2.ordinal());
			}
			else {
				ans = main.fp.get(instr.src1.ordinal()) - main.fp.get(instr.src2.ordinal());
			}
			main.fp.set(instr.des.ordinal(), ans);
		}
	}
	
}
